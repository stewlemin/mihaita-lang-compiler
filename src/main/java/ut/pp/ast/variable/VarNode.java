package ut.pp.ast.variable;

import ut.pp.ast.ExprNode;

/**
 * Base class for nodes that refer to a named variable.
 * Both plain variables and array accesses extend this class.
 */
public abstract class VarNode extends ExprNode {
    /**
     * The name of the variable being referenced.
     */
    public final String name;

    /**
     * Creates a variable reference node with the given name.
     *
     * @param name the name of the variable
     */
    protected VarNode(String name) {
        this.name = name;
    }
}
