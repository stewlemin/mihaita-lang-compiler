package ut.pp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import ut.pp.ast.ProgramNode;
import ut.pp.compiler.checker.Checker;
import ut.pp.compiler.codegen.CodeGenerator;
import ut.pp.compiler.codegen.HaskellOutput;
import ut.pp.compiler.parser.ParserRunner;

/**
 * Entry point of the compiler.
 * Reads a source file, runs the parser, checker, and code generator,
 * then writes the result to a Haskell output file.
 */
public class Main {
    /**
     * Runs the compiler on the given source file.
     *
     * @param args command-line arguments; first argument must be the path to the source file
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: mvn exec:java -Dexec.args=\"<path-to-source-file.msm>\"");
            System.exit(1);
        }

        Path filePath = Path.of(args[0]);
        String input;
        try {
            input = Files.readString(filePath);
        } catch (IOException e) {
            System.err.println("Couldn't read file" + filePath);
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }

        ProgramNode root = ParserRunner.parseString(input);
        Checker checker = new Checker();
        checker.check(root);

        CodeGenerator generator = new CodeGenerator();
        List<List<String>> programs = generator.generate(root);


        HaskellOutput output = new HaskellOutput();
        Path outputPath = Path.of("output/Generated.hs");
        try {
            Files.createDirectories(outputPath.getParent());
            output.writeToFile(programs, outputPath);
            System.out.println("Wrote to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
