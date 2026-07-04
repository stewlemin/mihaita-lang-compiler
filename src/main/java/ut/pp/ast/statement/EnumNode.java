package ut.pp.ast.statement;

import java.util.List;
import ut.pp.ast.StatementNode;

/**
 * Represents an enumerated type declaration.
 * For example: {@code enum Color { RED, GREEN, BLUE };}.
 */
public class EnumNode extends StatementNode {
    /**
     * The name of the enum type.
     */
    public final String name;
    /**
     * The ordered list of value names defined in this enum.
     */
    public final List<String> enumValues;

    /**
     * Creates an enum declaration node.
     *
     * @param name   the name of the enum type
     * @param values the list of value names in declaration order
     */
    public EnumNode(String name, List<String> values) {
        this.name = name;
        this.enumValues = values;
    }
}
