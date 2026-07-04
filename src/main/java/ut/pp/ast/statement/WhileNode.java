package ut.pp.ast.statement;

import ut.pp.ast.ExprNode;
import ut.pp.ast.StatementNode;

/**
 * Represents a while loop statement.
 * For example: {@code while (x < 10) { x = x + 1; }}.
 */
public class WhileNode extends StatementNode {
    /**
     * The loop condition expression.
     */
    public ExprNode expression;
    /**
     * The block of statements to repeat while the condition is true.
     */
    public BlockNode block;

    /**
     * Creates a while loop node.
     *
     * @param expr  the loop condition expression
     * @param block the body to execute on each iteration
     */
    public WhileNode(ExprNode expr, BlockNode block) {
        this.expression = expr;
        this.block = block;
    }
}
