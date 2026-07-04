package ut.pp.compiler.checker;

import java.util.List;

/**
 * Thrown when the checker finds one or more errors in the program.
 * All error messages are collected and available via {@link #getErrors()}.
 */
public class CheckerException extends RuntimeException {
    private final List<String> errors;

    /**
     * Creates a checker exception with the given list of error messages.
     *
     * @param errors the list of error messages found during checking
     */
    public CheckerException(List<String> errors) {
        super(String.join(System.lineSeparator(), errors));
        this.errors = errors;
    }

    /**
     * Returns all error messages that caused this exception.
     *
     * @return the list of error messages
     */
    public List<String> getErrors() {
        return errors;
    }
}
