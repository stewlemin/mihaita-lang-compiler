package ut.pp.compiler.codegen;

import ut.pp.ast.type.TypeNode;


/**
 * Describes where a declared variable lives in memory.
 * Stores the first address, how many memory cells it occupies,
 * and whether it is in local or shared memory.
 */
public class MemoryLocation {

    private final String name;
    private final TypeNode type;
    private final int firstAddress;
    private final int cellCount;
    private final boolean shared;

    /**
     * Creates a memory location for the given variable.
     *
     * @param name         the variable name (must not be null)
     * @param type         the variable type (must not be null)
     * @param firstAddress the first memory address allocated for this variable
     * @param isShared     true if the variable lives in shared (global) memory
     */
    public MemoryLocation(String name, TypeNode type, int firstAddress, boolean isShared) {

        if (name == null) {
            throw new CodeGeneratorException("Variable name cannot be null.");
        }
        if (type == null) {
            throw new CodeGeneratorException("Variable type cannot be null.");
        }

        this.name = name;
        this.type = type;
        this.shared = isShared;
        this.firstAddress = firstAddress;

        if (type.isArray()) {
            if (type.arrayLength == null || type.arrayLength <= 0) {
                throw new CodeGeneratorException("Invalid array size for variable '" + name + "'.");
            }
            this.cellCount = type.arrayLength;
        } else {
            this.cellCount = 1;
        }
    }

    /**
     * Returns the variable name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of this variable.
     *
     * @return the type node
     */
    public TypeNode getType() {
        return type;
    }

    /**
     * Returns the number of memory cells this variable occupies.
     * Arrays use one cell per element; scalar variables use one cell.
     *
     * @return the cell count
     */
    public int getCellCount() {
        return cellCount;
    }

    /**
     * Returns the first (lowest) memory address allocated for this variable.
     *
     * @return the first address
     */
    public int getFirstAddress() {
        return firstAddress;
    }

    /**
     * Returns {@code true} if this variable is stored in the array form.
     *
     * @return true if this is an array variable
     */
    public boolean isArray() {
        return type.isArray();
    }

    /**
     * Returns {@code true} if this variable is stored in shared (global) memory.
     *
     * @return true if shared
     */
    public boolean isShared() {
        return shared;
    }

    /**
     * Returns the memory address of the element at the given index.
     * For scalar variables, {@code i} must be 0.
     *
     * @param i the element index
     * @return the address of that element
     */
    public int addressOfElement(int i) {
        if (!isArray()) {
            if (i != 0) {
                throw new CodeGeneratorException("Variable '" + name + "' is not an array.");
            }
            return firstAddress;
        }

        if (i < 0 || i >= cellCount) {
            throw new CodeGeneratorException(
                    "Array index " + i + " out of bounds for '" + name + "'.");
        }

        return firstAddress + i;
    }

    @Override
    public String toString() {
        return name + " -> address " + firstAddress + ", size " + cellCount;
    }
}
