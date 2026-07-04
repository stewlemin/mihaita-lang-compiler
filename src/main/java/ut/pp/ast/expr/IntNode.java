package ut.pp.ast.expr;

import ut.pp.ast.ExprNode;

/**
 * Represents an integer literal expression, e.g. {@code 42}.
 */
public class IntNode extends ExprNode {
    /**
     * The integer value of this literal.
     */
    public final int value;

    /**
     * Creates an integer literal node.
     *
     * @param value the integer value
     */
    public IntNode(int value) {
        this.value = value;
    }
}
