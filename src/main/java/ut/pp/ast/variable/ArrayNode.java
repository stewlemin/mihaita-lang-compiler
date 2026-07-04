package ut.pp.ast.variable;

import ut.pp.ast.ExprNode;

/**
 * Represents an array element access expression, e.g. {@code a[2]}.
 */
public class ArrayNode extends VarNode {
    /**
     * The index expression used to access the array element.
     */
    public final ExprNode index;

    /**
     * Creates an array element access node.
     *
     * @param arrayName the name of the array variable
     * @param index     the index expression
     */
    public ArrayNode(String arrayName, ExprNode index) {
        super(arrayName);
        this.index = index;
    }
}
