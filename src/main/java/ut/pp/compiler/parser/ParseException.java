package ut.pp.compiler.parser;

/**
 * Thrown when the lexer or parser encounters a syntax error in the source program.
 */
public class ParseException extends RuntimeException {
    /**
     * Creates a parse exception with the given error message.
     *
     * @param message a description of the syntax error
     */
    public ParseException(String message) {
        super(message);
    }
}
