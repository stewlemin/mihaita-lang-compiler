package ut.pp.tests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import ut.pp.ast.ProgramNode;
import ut.pp.compiler.checker.Checker;
import ut.pp.compiler.codegen.CodeGenerator;
import ut.pp.compiler.codegen.HaskellOutput;
import ut.pp.compiler.parser.ParserRunner;

import static org.junit.jupiter.api.Assertions.*;

public class TestRuntime {

    //Maximum time for normal programs.
    private static final Duration TIMEOUT = Duration.ofSeconds(25);

    //Short timeout used to detect infinite loops.
    private static final Duration LOOP_TIMEOUT = Duration.ofSeconds(5);

    //Stores the result of running a program.
    private record RunResult(boolean finished, int exitCode, String output) {
    }

    //Compiles a test program and runs it.
    private RunResult run(String file, Duration timeout) throws IOException, InterruptedException {

        Path inputPath = Path.of("src", "test", "resources", "semantic").resolve(file);
        String source = Files.readString(inputPath);

        ProgramNode root = ParserRunner.parseString(source);
        new Checker().check(root);

        CodeGenerator generator = new CodeGenerator();
        List<List<String>> programs = generator.generate(root);

        Path outDir = Path.of("target", "runtime-tests");
        Files.createDirectories(outDir);

        String safeName = file.replace("/", "_").replace(".msm", ".hs");
        Path generatedFile = outDir.resolve(safeName);

        new HaskellOutput().writeToFile(programs, generatedFile);

        return exec(generatedFile, timeout);
    }

    //Runs the generated Haskell file with Sprockell.
    private RunResult exec(Path file, Duration timeout) throws IOException, InterruptedException {

        Path sprockellDir = Path.of("sprockell").toAbsolutePath().normalize();
        Path generatedFile = file.toAbsolutePath().normalize();

        String relativeFile = sprockellDir.relativize(generatedFile).toString();

        ProcessBuilder pb = new ProcessBuilder("stack", "runghc", relativeFile);

        pb.directory(sprockellDir.toFile());
        pb.redirectErrorStream(true);

        Process process = pb.start();

        boolean finished = process.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);

        if (!finished) {
            process.destroyForcibly();
            process.waitFor();

            return new RunResult(false, -1, "");
        }

        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        return new RunResult(true, process.exitValue(), output);
    }

    //Gets the numbers printed by Sprockell.
    private List<Integer> numbers(String output) {
        List<Integer> numbers = new ArrayList<>();

        Pattern pattern = Pattern.compile("says\\s+(-?\\d+)");
        Matcher matcher = pattern.matcher(output);

        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group(1)));
        }

        return numbers;
    }

    //Checks the leap-year program output.
    @Test
    public void leapYears() throws Exception {
        RunResult result = run("correct/leap_years.msm", TIMEOUT);

        assertTrue(result.finished, "Program did not terminate. Output:\n" + result.output);
        assertEquals(0, result.exitCode, "SPROCKELL failed. Output:\n" + result.output);

        assertEquals(List.of(29, 1, 28, 1, 29, 1, 28, 1, 29, 1, 29, 1, 28, 1),
                     numbers(result.output));
    }

    //Checks whether the prime-number program gives the correct results.
    @Test
    public void primeNumbers() throws Exception {
        RunResult result = run("correct/prime_number.msm", TIMEOUT);

        assertTrue(result.finished, "Program did not terminate. Output:\n" + result.output);
        assertEquals(0, result.exitCode, "SPROCKELL failed. Output:\n" + result.output);

        assertEquals(List.of(0, 1, 1, 0), numbers(result.output));
    }

    //Checks the final balance after four concurrent banking threads.
    @Test
    public void banking() throws Exception {
        RunResult result = run("correct/banking_system.msm", TIMEOUT);

        assertTrue(result.finished, "Program did not terminate. Output:\n" + result.output);
        assertEquals(0, result.exitCode, "SPROCKELL failed. Output:\n" + result.output);

        assertEquals(List.of(1160), numbers(result.output));
    }

    //Checks that nested forks produce the correct final total.
    @Test
    public void nestedForks() throws Exception {
        RunResult result = run("correct/nested_forks.msm", TIMEOUT);

        assertTrue(result.finished, "Program did not terminate. Output:\n" + result.output);
        assertEquals(0, result.exitCode, "SPROCKELL failed. Output:\n" + result.output);

        assertEquals(List.of(131), numbers(result.output));
    }

    //Checks the runtime error code for division by zero.
    @Test
    public void divisionByZero() throws Exception {
        RunResult result = run("incorrect/division_by_zero.msm", TIMEOUT);

        assertTrue(result.finished, "Program did not terminate. Output:\n" + result.output);

        assertEquals(List.of(-99999999), numbers(result.output));
    }

    //Checks that an infinite loop does not terminate.
    @Test
    public void infiniteLoop() throws Exception {
        RunResult result = run("incorrect/infinite_loop.msm", LOOP_TIMEOUT);

        assertFalse(result.finished,
                    "Infinite-loop program unexpectedly terminated. Output:\n" + result.output);
    }

    //Checks the runtime error code for an invalid array index.
    @Test
    public void arrayOutOfBounds() throws Exception {
        RunResult result = run("incorrect/array_out_of_bounds.msm", TIMEOUT);

        assertTrue(result.finished, "Program did not terminate. Output:\n" + result.output);
        assertEquals(0, result.exitCode, "SPROCKELL failed. Output:\n" + result.output);

        assertEquals(List.of(-88888888), numbers(result.output));
    }
}