package ut.pp.compiler.codegen;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import ut.pp.ast.type.TypeNode;

/**
 * Keeps track of where every variable is stored in memory during code generation.
 * Local variables are stored per scope; shared variables go into a separate global area.
 */
public class MemoryManager {
    private final Stack<Map<String, MemoryLocation>> scopes = new Stack<>();
    private final Map<String, MemoryLocation> sharedMemory = new HashMap<>();
    private int nextFreeAddress = 0;
    private int nextSharedAddress = 0;

    /**
     * Pushes a new empty local scope onto the scope stack.
     */
    public void newScope() {
        scopes.push(new HashMap<>());

    }

    /**
     * Creates a memory manager and immediately opens the global scope.
     */
    public MemoryManager() {
        newScope();
    }

    /**
     * Pops the innermost local scope from the scope stack.
     * Throws if you try to exit the global scope.
     */
    public void leaveScope() {
        if (scopes.size() <= 1) {
            throw new CodeGeneratorException("Cannot exit global memory scope.");
        }
        scopes.pop();
    }

    /**
     * Allocates a memory location for the given variable in the correct memory area.
     *
     * @param name     the variable name
     * @param type     the variable type
     * @param isShared true to place the variable in shared (global) memory
     * @return the allocated memory location
     */
    public MemoryLocation declare(String name, TypeNode type, boolean isShared) {
        if (isShared) {
            MemoryLocation location = new MemoryLocation(name, type, nextSharedAddress, true);
            sharedMemory.put(name, location);
            nextSharedAddress += location.getCellCount();
            return location;
        } else {
            Map<String, MemoryLocation> scope = scopes.peek();
            if (scope == null) {
                throw new CodeGeneratorException("No active memory scope.");
            }
            MemoryLocation location = new MemoryLocation(name, type, nextFreeAddress, false);
            scope.put(name, location);
            nextFreeAddress += location.getCellCount();
            return location;
        }
    }

    /**
     * Reserves a single address in shared memory for internal use (e.g. for thread state flags).
     *
     * @return the reserved shared memory address
     */
    public int allocateSharedAddress() {
        int address = nextSharedAddress;
        nextSharedAddress++;
        return address;
    }

    /**
     * Looks up the memory location for the given variable name.
     * Searches local scopes from innermost outward, then shared memory.
     *
     * @param name the variable name
     * @return the memory location
     * @throws CodeGeneratorException if the variable is not declared
     */
    public MemoryLocation search(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, MemoryLocation> scope = scopes.get(i);
            MemoryLocation location = scope.get(name);
            if (location != null) {
                return location;
            }
        }
        MemoryLocation shared = sharedMemory.get(name);
        if (shared != null) {
            return shared;
        }
        throw new CodeGeneratorException("Unknown variable: " + name);

    }

    /**
     * Returns the total number of local memory cells allocated so far.
     *
     * @return the number of cells used in local memory
     */
    public int getCellsUsed() {
        return nextFreeAddress;
    }

    /**
     * Returns {@code true} if the given name is declared in the current (innermost) local scope.
     *
     * @param name the variable name
     * @return true if declared in the current scope
     */
    public boolean isInCurrentScope(String name) {
        Map<String, MemoryLocation> scope = scopes.peek();
        return scope.containsKey(name);
    }
}
