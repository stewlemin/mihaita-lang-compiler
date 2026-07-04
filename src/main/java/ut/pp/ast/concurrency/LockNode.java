package ut.pp.ast.concurrency;

import ut.pp.ast.StatementNode;

/**
 * Represents a {@code lock} declaration statement.
 * Declares a named lock that can later be acquired or released.
 */
public class LockNode extends StatementNode {
    /**
     * The name of the lock being declared.
     */
    public final String identifier;

    /**
     * Creates a lock declaration node with the given name.
     *
     * @param name the name of the lock
     */
    public LockNode(String name) {
        this.identifier = name;
    }

}
