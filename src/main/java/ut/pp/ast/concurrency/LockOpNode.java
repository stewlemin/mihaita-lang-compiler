package ut.pp.ast.concurrency;

import ut.pp.ast.StatementNode;

/**
 * Represents an acquire or release operation on a named lock.
 * For example: {@code acquire(bank)} or {@code release(bank)}.
 */
public class LockOpNode extends StatementNode {
    /**
     * The name of the lock to operate on.
     */
    public final String identifier;
    /**
     * Whether to acquire or release the lock.
     */
    public final LockOp operation;

    /**
     * Creates a lock operation node.
     *
     * @param name the name of the lock
     * @param op   the operation to perform (acquire or release)
     */
    public LockOpNode(String name, LockOp op) {
        this.identifier = name;
        this.operation = op;
    }
}
