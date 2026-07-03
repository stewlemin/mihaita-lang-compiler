package ut.pp;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;

import ut.pp.compiler.checker.Checker;
import ut.pp.compiler.codegen.CodeGenerator;
import ut.pp.compiler.codegen.HaskellOutput;
import ut.pp.compiler.parser.ASTBuilder;
import ut.pp.compiler.parser.ParserRunner;
import ut.pp.parser.MyLangLexer;
import ut.pp.parser.MyLangParser;
import ut.pp.ast.ProgramNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if(args.length < 1){
            System.err.println("Usage: mvn exec:java -Dexec.args=\"<path-to-source-file.msm>\"");
            System.exit(1);
        }

        Path filePath = Path.of(args[0]);
        String input;
        try{
            input = Files.readString(filePath);
        }
        catch (IOException e){
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
