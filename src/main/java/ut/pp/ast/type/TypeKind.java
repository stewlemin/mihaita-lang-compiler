package ut.pp.ast.type;

/**
 * The basic kinds of types supported by the language.
 */
public enum TypeKind {
    /**
     * A 32-bit integer type.
     */
    INT,
    /**
     * A boolean type ({@code TRUE} or {@code FALSE}).
     */
    BOOL,
    /**
     * A user-defined enumerated type.
     */
    ENUM
}
