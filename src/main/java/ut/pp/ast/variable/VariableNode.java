package ut.pp.ast.variable;

/**
 * Represents a plain variable reference expression, e.g. {@code x}.
 */
public class VariableNode extends VarNode {

    /**
     * Creates a variable reference node.
     *
     * @param name the name of the variable
     */
    public VariableNode(String name) {
        super(name);
    }
}
