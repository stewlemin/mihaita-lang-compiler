package ut.pp.ast.expr;

import ut.pp.ast.ExprNode;

/**
 * Represents a unary expression with one operator and one operand.
 * Currently only the logical NOT operator ({@code !}) is supported.
 */
public class SingleExprNode extends ExprNode {
    /**
     * The operator symbol, e.g. {@code "!"}.
     */
    public final String operator;
    /**
     * The operand expression.
     */
    public final ExprNode expression;

    /**
     * Creates a unary expression node.
     *
     * @param op   the operator symbol
     * @param expr the operand expression
     */
    public SingleExprNode(String op, ExprNode expr) {
        this.operator = op;
        this.expression = expr;
    }
}
