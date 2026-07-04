package ut.pp.ast.type;

import ut.pp.ast.ASTNode;

/**
 * Represents a type in the source language.
 * A type can be a plain scalar (e.g. {@code int}), an array (e.g. {@code int[3]}),
 * or a named enum type (e.g. {@code Color}).
 */
public class TypeNode extends ASTNode {
    /**
     * The base kind of this type (INT, BOOL, or ENUM).
     */
    public final TypeKind kind;
    /**
     * The length of the array, or {@code null} if this is not an array type.
     */
    public final Integer arrayLength; // we define null as not an array
    /**
     * The enum type name, or {@code null} if this is not an enum type.
     */
    public final String typeName;

    /**
     * Creates a plain scalar type (int or bool).
     *
     * @param kind the base type kind
     */
    public TypeNode(TypeKind kind) {
        this.kind = kind;
        this.arrayLength = null;
        this.typeName = null;
    }

    /**
     * Creates a named type (e.g. an enum).
     *
     * @param kind the base type kind
     * @param name the name of the type
     */
    public TypeNode(TypeKind kind, String name) {
        this.kind = kind;
        this.typeName = name;
        this.arrayLength = null;
    }

    /**
     * Creates an array type with a fixed length.
     *
     * @param kind        the element type kind
     * @param arrayLength the number of elements in the array
     */
    public TypeNode(TypeKind kind, Integer arrayLength) {
        this.kind = kind;
        this.arrayLength = arrayLength;
        this.typeName = null;
    }

    /**
     * Creates a named array type (e.g. an array of a specific enum).
     *
     * @param kind        the element type kind
     * @param name        the name of the element type
     * @param arrayLength the number of elements in the array
     */
    public TypeNode(TypeKind kind, String name, Integer arrayLength) {
        this.arrayLength = arrayLength;
        this.typeName = name;
        this.kind = kind;
    }

    /**
     * Returns {@code true} if this type is an array.
     *
     * @return true if arrayLength is set
     */
    public boolean isArray() {
        return arrayLength != null;
    }

    /**
     * Returns {@code true} if this type is an enum.
     *
     * @return true if the kind is ENUM
     */
    public boolean isEnum() {
        return kind == TypeKind.ENUM;
    }
}
