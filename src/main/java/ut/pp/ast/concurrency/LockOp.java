package ut.pp.ast.concurrency;

/**
 * The two possible operations on a lock: acquire or release.
 */
public enum LockOp {
    /**
     * Tries to acquire the lock, blocking until it succeeds.
     */
    ACQUIRE,
    /**
     * Releases the lock so another thread can acquire it.
     */
    RELEASE
}
