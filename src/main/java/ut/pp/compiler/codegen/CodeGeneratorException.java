package ut.pp.compiler.codegen;

/**
 * Thrown when the code generator encounters an unsupported or invalid AST node.
 */
public final class CodeGeneratorException extends RuntimeException {
    /**
     * Creates a code generator exception with the given message.
     *
     * @param message a description of the problem
     */
    public CodeGeneratorException(String message) {
        super(message);
    }
}
