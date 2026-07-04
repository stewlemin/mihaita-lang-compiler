package ut.pp.compiler.parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * An ANTLR error listener that turns any syntax error into a {@link ParseException}.
 * Attach this to the lexer and parser to get early, clean error reporting.
 */
public class ExceptionErrorListener extends BaseErrorListener {
    /**
     * The singleton instance to attach to the lexer and parser.
     */
    public static final ExceptionErrorListener listener = new ExceptionErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                            int charPositionInLine, String msg, RecognitionException e) {
        throw new ParseException(
                "syntax error at line " + line + " and col " + charPositionInLine + " - " + msg);
    }

}
