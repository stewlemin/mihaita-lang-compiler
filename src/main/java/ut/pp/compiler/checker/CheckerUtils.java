package ut.pp.compiler.checker;

import java.util.Objects;
import ut.pp.ast.type.TypeKind;
import ut.pp.ast.type.TypeNode;

/**
 * Utility methods used by the {@link Checker} for type comparisons and type creation.
 * This class only has static methods and cannot be instantiated.
 */
public final class CheckerUtils {
    private CheckerUtils() {
    }

    /**
     * Returns {@code true} if the two types are compatible (same kind, array length, and enum name).
     *
     * @param first  the first type
     * @param second the second type
     * @return true if both types match
     */
    public static boolean sameType(TypeNode first, TypeNode second) {
        if (first == null || second == null) {
            return false;
        }

        if (first.kind != second.kind) {
            return false;
        }

        if (first.isArray() != second.isArray()) {
            return false;
        }

        if (first.kind == TypeKind.ENUM) {
            if (!Objects.equals(first.typeName, second.typeName)) {
                return false;
            }
        }

        if (first.isArray()) {
            return Objects.equals(first.arrayLength, second.arrayLength);
        }


        return true;
    }

    /**
     * Returns {@code true} if the given type is a plain integer (not an array).
     *
     * @param type the type to check
     * @return true if the type is int
     */
    public static boolean isInt(TypeNode type) {
        return type != null && !type.isArray() && type.kind == TypeKind.INT;
    }

    /**
     * Returns {@code true} if the given type is a plain boolean (not an array).
     *
     * @param type the type to check
     * @return true if the type is bool
     */
    public static boolean isBool(TypeNode type) {
        return type != null && !type.isArray() && type.kind == TypeKind.BOOL;
    }

    /**
     * Creates a fresh plain integer type node.
     *
     * @return a new int type node
     */
    public static TypeNode intType() {
        return new TypeNode(TypeKind.INT);
    }

    /**
     * Creates a fresh plain boolean type node.
     *
     * @return a new bool type node
     */
    public static TypeNode boolType() {
        return new TypeNode(TypeKind.BOOL);
    }

    /**
     * Returns the element type of an array type.
     * For example, for {@code int[3]} this returns {@code int}.
     *
     * @param arrayType the array type
     * @return the element type, or {@code null} if not an array
     */
    public static TypeNode elementType(TypeNode arrayType) {
        if (arrayType == null || !arrayType.isArray()) {
            return null;
        }
        if (arrayType.kind == TypeKind.ENUM) {
            return new TypeNode(TypeKind.ENUM, arrayType.typeName);
        }

        return new TypeNode(arrayType.kind);
    }

    /**
     * Converts a type node to a human-readable string, e.g. {@code "int[3]"} or {@code "Color"}.
     *
     * @param type the type to convert
     * @return a readable string representation of the type
     */
    public static String toString(TypeNode type) {
        if (type == null) {
            return "<unknown>";
        }
        String base;
        if (type.kind == TypeKind.INT) {
            base = "int";
        } else if (type.kind == TypeKind.BOOL) {
            base = "bool";
        } else if (type.kind == TypeKind.ENUM) {
            base = type.typeName;
        } else {
            base = "<unknown>";
        }
        if (type.isArray()) {
            return base + "[" + type.arrayLength + "]";
        }
        return base;
    }

}
