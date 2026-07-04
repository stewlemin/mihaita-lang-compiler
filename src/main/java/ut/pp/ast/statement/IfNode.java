package ut.pp.ast.statement;

import ut.pp.ast.ExprNode;
import ut.pp.ast.StatementNode;

/**
 * Represents an if-statement, optionally with an else block.
 * For example: {@code if (x > 0) { print x; } else { print 0; }}.
 */
public class IfNode extends StatementNode {
    /**
     * The boolean condition expression.
     */
    public final ExprNode condition;
    /**
     * The block to execute when the condition is true.
     */
    public final BlockNode thenBlock;
    /**
     * The block to execute when the condition is false, or {@code null} if there is no else.
     */
    public final BlockNode elseBlock;

    /**
     * Creates an if-node with no else block.
     *
     * @param cond      the condition expression
     * @param thenBlock the block executed when the condition is true
     */
    public IfNode(ExprNode cond, BlockNode thenBlock) {
        this.condition = cond;
        this.thenBlock = thenBlock;
        this.elseBlock = null;
    }

    /**
     * Creates an if-else node.
     *
     * @param cond      the condition expression
     * @param thenBlock the block executed when the condition is true
     * @param elseBlock the block executed when the condition is false
     */
    public IfNode(ExprNode cond, BlockNode thenBlock, BlockNode elseBlock) {
        this.condition = cond;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }
}
