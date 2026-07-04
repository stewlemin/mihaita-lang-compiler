package ut.pp.ast.statement;

import ut.pp.ast.ExprNode;
import ut.pp.ast.StatementNode;
import ut.pp.ast.variable.VarNode;

/**
 * Represents an assignment statement, e.g. {@code x = 5} or {@code a[0] = 99}.
 */
public class AssignmentNode extends StatementNode {
    /**
     * The variable (or array element) being assigned to.
     */
    public final VarNode target;
    /**
     * The expression whose value is assigned.
     */
    public final ExprNode value;

    /**
     * Creates an assignment node.
     *
     * @param target the variable or array element to assign to
     * @param value  the expression to evaluate and store
     */
    public AssignmentNode(VarNode target, ExprNode value) {
        this.target = target;
        this.value = value;
    }
}
