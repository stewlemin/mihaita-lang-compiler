package ut.pp.compiler.checker;

import ut.pp.ast.type.TypeNode;

/**
 * Represents a declared symbol (variable or lock) in the symbol table.
 * Stores the name, type, initialization status, and whether it is a lock.
 */
public class Symbol {
    private final String name;
    private final TypeNode type;
    private boolean initialized = false;
    private final boolean isLock;

    /**
     * Creates a symbol with the given properties.
     *
     * @param name          the name of the symbol
     * @param type          the type of the symbol ({@code null} for locks)
     * @param isInitialized whether the symbol already has a value
     * @param isLock        whether this symbol is a lock rather than a variable
     */
    public Symbol(String name, TypeNode type, boolean isInitialized, boolean isLock) {
        this.name = name;
        this.type = type;
        this.initialized = isInitialized;
        this.isLock = isLock;
    }

    /**
     * Marks this symbol as initialized.
     */
    public void initialize() {
        initialized = true;
    }

    /**
     * Returns {@code true} if this symbol has been initialized.
     *
     * @return true if the symbol has a value
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Returns the type of this symbol.
     *
     * @return the type node, or {@code null} for locks
     */
    public TypeNode getType() {
        return type;
    }

    /**
     * Returns {@code true} if this symbol is a lock.
     *
     * @return true if this is a lock symbol
     */
    public boolean isLock() {
        return isLock;
    }

}
