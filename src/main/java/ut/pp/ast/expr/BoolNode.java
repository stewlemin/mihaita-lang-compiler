package ut.pp.ast.expr;

import ut.pp.ast.ExprNode;

/**
 * Represents a boolean literal expression: {@code TRUE} or {@code FALSE}.
 */
public class BoolNode extends ExprNode {
    /**
     * The boolean value of this literal.
     */
    public final boolean value;

    /**
     * Creates a boolean literal node.
     *
     * @param value the boolean value
     */
    public BoolNode(boolean value) {
        this.value = value;
    }


}
