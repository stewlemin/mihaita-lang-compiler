package ut.pp.ast.statement;

import ut.pp.ast.ExprNode;
import ut.pp.ast.StatementNode;
import ut.pp.ast.type.TypeNode;

/**
 * Represents a variable declaration, optionally with an initializer expression.
 * For example: {@code int x = 5;} or {@code shared bool flag;}.
 */
public class DeclarationNode extends StatementNode {
    /**
     * The declared type of the variable.
     */
    public final TypeNode type;
    /**
     * The name of the variable being declared.
     */
    public final String identifier;
    /**
     * The initial value expression, or {@code null} if none was given.
     */
    public final ExprNode value;
    /**
     * Whether the variable is declared as {@code shared} (lives in global memory).
     */
    public final boolean isShared;

    /**
     * Creates a declaration node with no initializer.
     *
     * @param type       the type of the variable
     * @param identifier the name of the variable
     * @param isShared   whether the variable is shared across threads
     */
    public DeclarationNode(TypeNode type, String identifier, boolean isShared) {
        this.identifier = identifier;
        this.type = type;
        this.value = null;
        this.isShared = isShared;
    }

    /**
     * Creates a declaration node with an initializer expression.
     *
     * @param type       the type of the variable
     * @param identifier the name of the variable
     * @param isShared   whether the variable is shared across threads
     * @param expression the initial value expression
     */
    public DeclarationNode(TypeNode type, String identifier, boolean isShared,
                           ExprNode expression) {
        this.type = type;
        this.identifier = identifier;
        this.value = expression;
        this.isShared = isShared;
    }
}
