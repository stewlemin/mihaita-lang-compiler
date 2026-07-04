package ut.pp.ast.statement;

import java.util.List;
import ut.pp.ast.StatementNode;

/**
 * Represents a block of statements enclosed in curly braces, e.g. {@code { int x = 1; print x; }}.
 * Blocks introduce a new scope.
 */
public class BlockNode extends StatementNode {
    /**
     * The ordered list of statements inside this block.
     */
    public final List<StatementNode> statements;

    /**
     * Creates a block node with the given statements.
     *
     * @param statements the statements inside the block
     */
    public BlockNode(List<StatementNode> statements) {
        this.statements = statements;
    }
}
