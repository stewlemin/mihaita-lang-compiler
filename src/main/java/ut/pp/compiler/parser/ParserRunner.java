package ut.pp.compiler.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import ut.pp.ast.ProgramNode;
import ut.pp.parser.MyLangLexer;
import ut.pp.parser.MyLangParser;

/**
 * Provides helper methods to run the ANTLR lexer and parser on source code
 * and build a {@link ProgramNode} AST from the result.
 */
public class ParserRunner {

    /**
     * Parses the given source code string and returns the AST root.
     *
     * @param input the source code to parse
     * @return the root program AST node
     * @throws ut.pp.compiler.parser.ParseException if the input contains a syntax error
     */
    public static ProgramNode parseString(String input) {
        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(input));
        myLangLexer.removeErrorListeners();
        myLangLexer.addErrorListener(ExceptionErrorListener.listener);

        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ExceptionErrorListener.listener);

        ParseTree tree = parser.program();
        ASTBuilder builder = new ASTBuilder();
        return (ProgramNode) builder.visit(tree);
    }

    /**
     * Reads the source file at the given path and parses it.
     *
     * @param path the path to the source file
     * @return the root program AST node
     * @throws IOException                          if the file cannot be read
     * @throws ut.pp.compiler.parser.ParseException if the source contains a syntax error
     */
    public static ProgramNode parseFile(Path path) throws IOException {
        String input = Files.readString(path);
        return parseString(input);
    }
}
