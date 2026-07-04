package ut.pp.compiler.checker;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ut.pp.ast.type.TypeNode;


/**
 * Tracks declared variables, locks, and enum types across nested scopes.
 * Scopes are pushed on entry to a block and popped on exit.
 */
public class SymbolTable {

    private final List<Map<String, Symbol>> scopesList = new ArrayList<>();
    private final Map<String, Map<String, Integer>> enums = new HashMap<>();

    /**
     * Declares a new enum type with its ordered values.
     * Returns {@code false} if the enum name or any of its values conflict with existing names.
     *
     * @param name   the name of the enum type
     * @param values the ordered list of value names
     * @return true if the enum was declared successfully
     */
    public boolean declareEnum(String name, List<String> values) {
        if (enums.containsKey(name) || isEnumVal(name)) {
            return false;
        }

        Map<String, Integer> current = new HashMap<>();

        for (int i = 0; i < values.size(); i++) {
            String val = values.get(i);
            if (enums.containsKey(val) || isEnumVal(val) || current.containsKey(val)) {
                return false;
            }
            current.put(val, i);
        }

        enums.put(name, current);
        return true;
    }

    /**
     * Returns {@code true} if the given name is a declared enum type.
     *
     * @param name the name to check
     * @return true if it is a known enum type
     */
    public boolean isEnumType(String name) {
        return enums.containsKey(name);
    }

    /**
     * Returns {@code true} if the given name is a value of any declared enum.
     *
     * @param valName the name to check
     * @return true if it is an enum value
     */
    public boolean isEnumVal(String valName) {
        return enumTypeOfVal(valName) != null;
    }

    /**
     * Returns the enum type name that the given value belongs to, or {@code null} if not found.
     *
     * @param valName the enum value name
     * @return the enum type name, or null
     */
    public String enumTypeOfVal(String valName) {
        for (String enName : enums.keySet()) {
            Map<String, Integer> values = enums.get(enName);
            if (values.containsKey(valName)) {
                return enName;
            }
        }
        return null;
    }

    /**
     * Pushes a new empty scope onto the scope stack.
     */
    public void enterScope() {
        scopesList.add(new HashMap<String, Symbol>());
    }

    /**
     * Pops the innermost scope from the scope stack.
     */
    public void exitScope() {
        scopesList.remove(scopesList.size() - 1);
    }

    /**
     * Declares a symbol in the current (innermost) scope.
     * Does nothing if the name is already declared in the current scope.
     *
     * @param name        the symbol name
     * @param type        the symbol type
     * @param initialized whether the symbol starts initialised
     * @param isLock      whether this symbol is a lock
     */
    public void declare(String name, TypeNode type, boolean initialized, boolean isLock) {
        if (!isDeclaredInCurrScope(name)) {
            Symbol variable = new Symbol(name, type, initialized, isLock);
            scopesList.get(scopesList.size() - 1).put(name, variable);
        }
    }

    /**
     * Declares a lock in the current scope with no type.
     *
     * @param name the name of the lock
     */
    public void declareLock(String name) {
        declare(name, null, true, true);
    }

    /**
     * Returns {@code true} if the given name is already declared in the current scope.
     *
     * @param name the name to check
     * @return true if the name is declared in the current scope
     */
    public boolean isDeclaredInCurrScope(String name) {
        return scopesList.get(scopesList.size() - 1).containsKey(name);
    }

    /**
     * Looks up a symbol by name, searching from the innermost scope outward.
     *
     * @param name the symbol name to look up
     * @return the found symbol, or {@code null} if not declared in any scope
     */
    public Symbol lookup(String name) {
        for (int i = scopesList.size() - 1; i >= 0; i--) {
            if (scopesList.get(i).containsKey(name)) {
                return scopesList.get(i).get(name);
            }
        }
        return null;
    }

    /**
     * Marks a symbol as initialized. Does nothing if the symbol is not found.
     *
     * @param name the name of the symbol to mark as initialized
     */
    public void markInitiliazed(String name) {
        Symbol s = lookup(name);
        if (s != null) {
            s.initialize();
        }
    }
}
