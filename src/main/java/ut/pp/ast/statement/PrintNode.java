package ut.pp.ast.statement;

import ut.pp.ast.ExprNode;
import ut.pp.ast.StatementNode;

/**
 * Represents a print statement that outputs an expression's value.
 * For example: {@code print x;} or {@code print 42;}.
 */
public class PrintNode extends StatementNode {
    /**
     * The expression whose value is printed.
     */
    public final ExprNode expression;

    /**
     * Creates a print node.
     *
     * @param expression the expression to print
     */
    public PrintNode(ExprNode expression) {
        this.expression = expression;
    }
}
