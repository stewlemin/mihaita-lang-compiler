package ut.pp.ast.expr;

import ut.pp.ast.ExprNode;

/**
 * Represents a binary expression with two operands and an operator.
 * For example: {@code 1 + 2}, {@code x > 3}, or {@code a && b}.
 */
public class DoubleExprNode extends ExprNode {
    /**
     * The left-hand side expression.
     */
    public final ExprNode expr1;
    /**
     * The operator symbol, e.g. {@code "+"}, {@code "=="}, {@code "&&"}.
     */
    public final String operator;
    /**
     * The right-hand side expression.
     */
    public final ExprNode expr2;

    /**
     * Creates a binary expression node.
     *
     * @param expr1    the left operand
     * @param Operator the operator symbol
     * @param expr2    the right operand
     */
    public DoubleExprNode(ExprNode expr1, String Operator, ExprNode expr2) {
        this.expr1 = expr1;
        this.operator = Operator;
        this.expr2 = expr2;
    }
}
