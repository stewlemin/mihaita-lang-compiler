package ut.pp.ast.expr;

import java.util.List;
import ut.pp.ast.ExprNode;

/**
 * Represents an array literal expression, e.g. {@code [1, 2, 3]}.
 */
public class ArrayLiteralNode extends ExprNode {
    /**
     * The list of element expressions in the array literal.
     */
    public final List<ExprNode> elements;

    /**
     * Creates an array literal node with the given elements.
     *
     * @param elements the expressions that make up the array
     */
    public ArrayLiteralNode(List<ExprNode> elements) {
        this.elements = elements;
    }
}