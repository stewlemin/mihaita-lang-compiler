package ut.pp.ast;

import java.util.List;

/**
 * Represents the root of the program's AST.
 * Holds the list of top-level statements that make up the program.
 */
public class ProgramNode extends ASTNode {
    /**
     * The list of top-level statements in the program.
     */
    public final List<StatementNode> statements;

    /**
     * Creates a new program node with the given statements.
     *
     * @param statements the top-level statements of the program
     */
    public ProgramNode(List<StatementNode> statements) {
        this.statements = statements;
    }
}
