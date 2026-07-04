package ut.pp.ast.concurrency;

import ut.pp.ast.StatementNode;
import ut.pp.ast.statement.BlockNode;

/**
 * Represents a {@code fork} statement that starts a new concurrent thread.
 * The thread runs the given block independently of the parent thread.
 */
public class ForkNode extends StatementNode {
    /**
     * The block of statements the forked thread will execute.
     */
    public final BlockNode body;

    /**
     * Creates a fork node with the given block as its thread body.
     *
     * @param body the block of code to run in the new thread
     */
    public ForkNode(BlockNode body) {
        this.body = body;
    }
}
