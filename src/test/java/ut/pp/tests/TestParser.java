package ut.pp.tests;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import ut.pp.ast.ProgramNode;
import ut.pp.ast.concurrency.*;
import ut.pp.ast.expr.ArrayLiteralNode;
import ut.pp.ast.expr.DoubleExprNode;
import ut.pp.ast.expr.SingleExprNode;
import ut.pp.ast.statement.*;
import ut.pp.ast.type.TypeKind;
import ut.pp.ast.variable.ArrayNode;
import ut.pp.compiler.parser.ParseException;
import ut.pp.compiler.parser.ParserRunner;

import static org.junit.jupiter.api.Assertions.*;


public class TestParser {
    private ProgramNode buildAst(String input) {
        return ParserRunner.parseString(input);
    }


    //Checks that a simple addition is parsed with the + operator.
    @Test
    public void parseSimpleAddition() {
        ProgramNode root = buildAst("int x = 1 + 2;");
        DeclarationNode decl = (DeclarationNode) root.statements.get(0);
        DoubleExprNode add = (DoubleExprNode) decl.value;
        assertEquals("+", add.operator);
    }

    //Checks that multiplication binds tighter than addition.
    @Test
    public void multBindsTightherThanAdd() {
        ProgramNode root = buildAst("x = 2 + 3 * 4;");
        AssignmentNode stmt = (AssignmentNode) root.statements.get(0);
        DoubleExprNode add = (DoubleExprNode) stmt.value;
        DoubleExprNode mult = (DoubleExprNode) add.expr2;
        assertEquals("+", add.operator);
        assertEquals("*", mult.operator);
    }

    //Checks that parentheses override the default binding rules.
    @Test
    public void paransOverrideBindingRules() {
        ProgramNode root = buildAst("x = (2 + 3) * 4;");
        AssignmentNode stmt = (AssignmentNode) root.statements.get(0);
        DoubleExprNode mult = (DoubleExprNode) stmt.value;
        assertEquals("*", mult.operator);
        DoubleExprNode add = (DoubleExprNode) mult.expr1;
        assertEquals("+", add.operator);
    }

    //Checks that subtraction is left-associative.
    @Test
    public void leftAssociativeSameOrderOperations() {
        ProgramNode root = buildAst("x = 10 - 3 - 2;");
        AssignmentNode stmt = (AssignmentNode) root.statements.get(0);
        DoubleExprNode first = (DoubleExprNode) stmt.value;
        assertEquals("-", first.operator);
        DoubleExprNode second = (DoubleExprNode) first.expr1;
        assertEquals("-", second.operator);
    }

    //Checks that comparison operators bind looser than arithmetic.
    @Test
    public void comparisonAfterArithmetic() {
        ProgramNode root = buildAst("x = 1 + 2 > 3;");
        AssignmentNode stmt = (AssignmentNode) root.statements.get(0);
        DoubleExprNode cmp = (DoubleExprNode) stmt.value;
        assertEquals(">", cmp.operator);
        DoubleExprNode add = (DoubleExprNode) cmp.expr1;
        assertEquals("+", add.operator);
    }


    //Checks that && binds tighter than ||.
    @Test
    public void andBindsTighterThanOr() {
        ProgramNode root = buildAst("b = TRUE && FALSE || TRUE;");
        AssignmentNode stmt = (AssignmentNode) root.statements.get(0);
        DoubleExprNode or = (DoubleExprNode) stmt.value;
        assertEquals("||", or.operator);
        assertEquals("&&", ((DoubleExprNode) or.expr1).operator);
    }


    //Checks that == binds tighter than &&.
    @Test
    public void eqBindsTightherThanAnd() {
        ProgramNode root = buildAst("b = 1 == 2 && TRUE;");
        AssignmentNode stmt = (AssignmentNode) root.statements.get(0);
        DoubleExprNode and = (DoubleExprNode) stmt.value;
        assertEquals("&&", and.operator);
        assertEquals("==", ((DoubleExprNode) and.expr1).operator);
    }


    //Checks that comparison binds tighter than equality.
    @Test
    public void compBindsTighterThanEq() {
        ProgramNode root = buildAst("b = 1 < 2 == TRUE;");
        AssignmentNode stmt = (AssignmentNode) root.statements.get(0);
        DoubleExprNode eq = (DoubleExprNode) stmt.value;
        assertEquals("==", eq.operator);
        assertEquals("<", ((DoubleExprNode) eq.expr1).operator);
    }

    //Checks that ! binds tighter than all binary operators.
    @Test
    public void notBindsBeforeAllOthers() {
        ProgramNode root = buildAst("b = !TRUE && FALSE;");
        AssignmentNode stmt = (AssignmentNode) root.statements.get(0);
        DoubleExprNode and = (DoubleExprNode) stmt.value;
        assertEquals("&&", and.operator);
        SingleExprNode not = (SingleExprNode) and.expr1;
        assertEquals("!", not.operator);
    }

    //Checks that ! applies to a parenthesised sub-expression.
    @Test
    public void notAppliesToParans() {
        ProgramNode root = buildAst("b = !(TRUE || FALSE);");
        AssignmentNode stmt = (AssignmentNode) root.statements.get(0);
        SingleExprNode booleanArithm = (SingleExprNode) stmt.value;
        assertInstanceOf(DoubleExprNode.class, booleanArithm.expression);
    }

    //Checks that a boolean expression is accepted as an if-condition.
    @Test
    public void boolUsedAsIfCondition() {
        ProgramNode root = buildAst("if (TRUE && FALSE) { print 1; }");
        assertInstanceOf(IfNode.class, root.statements.get(0));
    }

    //Rejects a statement that is missing its terminating semicolon.
    @Test
    public void rejectsOnSyntaxError() {
        assertThrows(ParseException.class, () -> buildAst("int x = 2"));
    }

    //Rejects an if or while body that is not wrapped in braces.
    @Test
    public void rejectsBraceLessIfOrWhileBlock() {
        assertThrows(ParseException.class, () -> buildAst("if (x) print 1"));
    }

    //Rejects an if statement with no condition.
    @Test
    public void rejectsMissingCondition() {
        assertThrows(ParseException.class, () -> buildAst("if () { print 1; }"));
    }

    //Checks that an else attaches to the immediately enclosing if.
    @Test
    public void elseAttachesToSamBlockIf() {
        ProgramNode root = buildAst("if (a) { if (b) { print 1; } else { print 2; } }");
        IfNode outer = (IfNode) root.statements.get(0);
        assertNull(outer.elseBlock);
        IfNode inner = (IfNode) outer.thenBlock.statements.get(0);
        assertNotNull(inner.elseBlock);
    }

    //Checks that an empty while body is parsed without errors.
    @Test
    public void emptyBlockParses() {
        ProgramNode root = buildAst("while (TRUE) {}");
        WhileNode node = (WhileNode) root.statements.get(0);
        assertEquals(0, node.block.statements.size());
    }

    //Checks that an array literal expression is parsed correctly.
    @Test
    public void arrayLiteralParses() {
        ProgramNode root = buildAst("x = [1, 2, 3];");
        AssignmentNode stmt = (AssignmentNode) root.statements.get(0);
        assertInstanceOf(ArrayLiteralNode.class, stmt.value);
    }

    //Checks that array indexing syntax is parsed without errors.
    @Test
    public void arrayIndexingParses() {
        ProgramNode root = buildAst("x = a[2];");
    }

    //Checks that an array type declaration is parsed as a DeclarationNode.
    @Test
    public void arrayDeclarationParses() {
        ProgramNode root = buildAst("int[5] a;");
        assertInstanceOf(DeclarationNode.class, root.statements.get(0));
    }

    //Checks that fork parses into a ForkNode.
    @Test
    public void forkParsesForkNode() {
        ProgramNode root = buildAst("fork { print 21; }");
        assertInstanceOf(ForkNode.class, root.statements.get(0));
    }

    //Checks that multiple statements inside a fork body are all captured.
    @Test
    public void forkParsesMultipleStatementsInBlock() {
        ProgramNode root = buildAst("fork { print 16; int x = 23; }");
        ForkNode fork = (ForkNode) root.statements.get(0);
        assertEquals(2, fork.body.statements.size());
    }

    //Checks that join parses into a JoinNode.
    @Test
    public void joinParsesToJoinNode() {
        ProgramNode root = buildAst("join;");
        assertInstanceOf(JoinNode.class, root.statements.get(0));
    }

    //Checks that multiple forks followed by a join are all parsed correctly.
    @Test
    public void multipleForksThenJoin() {
        ProgramNode root = buildAst("fork { print 273; } fork { x = 2 * 999; } join;");
        assertInstanceOf(ForkNode.class, root.statements.get(0));
        assertInstanceOf(ForkNode.class, root.statements.get(1));
        assertInstanceOf(JoinNode.class, root.statements.get(2));
    }

    //Checks that a fork nested inside another fork is parsed correctly.
    @Test
    public void nestedForkNodesParse() {
        ProgramNode root = buildAst("fork { fork { print 61; } }");
        ForkNode outer = (ForkNode) root.statements.get(0);
        assertInstanceOf(ForkNode.class, outer.body.statements.get(0));
    }

    //Checks that a lock declaration parses into a LockNode.
    @Test
    public void lockDeclParsesToLockNode() {
        ProgramNode root = buildAst("lock bankLock;");
        assertInstanceOf(LockNode.class, root.statements.get(0));
    }

    //Checks that acquire parses into a LockOpNode with the ACQUIRE operation.
    @Test
    public void acquireLockParsesToLockOpAQ() {
        ProgramNode root = buildAst("acquire(auraLock);");
        LockOpNode op = (LockOpNode) root.statements.get(0);
        assertEquals(LockOp.ACQUIRE, op.operation);
    }

    //Checks that release parses into a LockOpNode with the RELEASE operation.
    @Test
    public void releaseParsesToLockOpREL() {
        ProgramNode root = buildAst("release(auraLock);");
        LockOpNode op = (LockOpNode) root.statements.get(0);
        assertEquals(LockOp.RELEASE, op.operation);
    }

    //Checks that a shared declaration sets the isShared flag to true.
    @Test
    public void sharedDeclarationHasSharedFlag() {
        ProgramNode root = buildAst("shared int balance = 0;");
        DeclarationNode decl = (DeclarationNode) root.statements.get(0);
        assertTrue(decl.isShared);
    }

    //Checks that a normal declaration does not set the isShared flag.
    @Test
    public void nonSharedDeclarationNoSharedFlag() {
        ProgramNode root = buildAst("int x = 0;");
        DeclarationNode decl = (DeclarationNode) root.statements.get(0);
        assertFalse(decl.isShared);
    }

    //Checks that single-line comments are ignored by the parser.
    @Test
    public void commentsAreIgnored() {
        ProgramNode root = buildAst("""
                                            int x = 1; 
                                            // this is a comment;
                                            print x;
                                            """);
        assertEquals(2, root.statements.size());
        assertInstanceOf(DeclarationNode.class, root.statements.get(0));
        assertInstanceOf(PrintNode.class, root.statements.get(1));
    }

    //Checks that an enum declaration is parsed with the correct name and values.
    @Test
    public void enumDeclarationParses() {
        ProgramNode root = buildAst("enum Color { RED, GREEN, BLUE };");
        EnumNode enumNode = (EnumNode) root.statements.get(0);
        assertEquals("Color", enumNode.name);
        assertEquals(3, enumNode.enumValues.size());
        assertEquals("RED", enumNode.enumValues.get(0));
        assertEquals("GREEN", enumNode.enumValues.get(1));
        assertEquals("BLUE", enumNode.enumValues.get(2));
    }

    //Checks that a variable of an enum type is parsed with the ENUM kind.
    @Test
    public void enumVariableDeclarationParsesAsEnumType() {
        ProgramNode root = buildAst("Color c = GREEN;");
        DeclarationNode decl = (DeclarationNode) root.statements.get(0);
        assertEquals(TypeKind.ENUM, decl.type.kind);
        assertEquals("Color", decl.type.typeName);
    }

    //Checks that the division operator is parsed correctly.
    @Test
    public void divisionParses() {
        ProgramNode root = buildAst("int x = 10 / 2;");
        DeclarationNode decl = (DeclarationNode) root.statements.get(0);
        DoubleExprNode div = (DoubleExprNode) decl.value;
        assertEquals("/", div.operator);
    }

    //Checks that the != operator is parsed correctly.
    @Test
    public void notEqualsParses() {
        ProgramNode root = buildAst("bool b = 1 != 2;");
        DeclarationNode decl = (DeclarationNode) root.statements.get(0);
        DoubleExprNode neq = (DoubleExprNode) decl.value;
        assertEquals("!=", neq.operator);
    }

    //Checks that <= and >= are both parsed correctly.
    @Test
    public void gteAndLteParse() {
        ProgramNode root = buildAst("bool a = 1 <= 2; bool b = 3 >= 2;");
        DeclarationNode first = (DeclarationNode) root.statements.get(0);
        DeclarationNode second = (DeclarationNode) root.statements.get(1);
        assertEquals("<=", ((DoubleExprNode) first.value).operator);
        assertEquals(">=", ((DoubleExprNode) second.value).operator);
    }

    //Checks that the || operator is parsed correctly.
    @Test
    public void orBooleanOpParses() {
        ProgramNode root = buildAst("bool f = TRUE || FALSE;");
        DeclarationNode decl = (DeclarationNode) root.statements.get(0);
        DoubleExprNode or = (DoubleExprNode) decl.value;
        assertEquals("||", or.operator);
    }

    //Checks that assignment to an array element is parsed with an ArrayNode target.
    @Test
    public void arrayElementAssignmentParses() {
        ProgramNode root = buildAst("a[1] = 7;");
        AssignmentNode assignment = (AssignmentNode) root.statements.get(0);
        assertInstanceOf(ArrayNode.class, assignment.target);
    }

    //Checks that an empty array literal [] is parsed correctly.
    @Test
    public void emptyArrayLiteralParses() {
        ProgramNode root = buildAst("x = [];");
        AssignmentNode assignment = (AssignmentNode) root.statements.get(0);
        assertInstanceOf(ArrayLiteralNode.class, assignment.value);
    }

    //Rejects an enum declaration with a trailing comma.
    @Test
    public void rejectsBadEnumSyntax() {
        assertThrows(ParseException.class, () -> buildAst("enum Color { RED, };"));
    }

    //Rejects a lock operation written without parentheses.
    @Test
    public void rejectsBadLockSyntax() {
        assertThrows(ParseException.class, () -> buildAst("acquire bankLock;"));
    }

    //Rejects a fork without a block body.
    @Test
    public void rejectsBadForkSyntax() {
        assertThrows(ParseException.class, () -> buildAst("fork print 1;"));
    }

    //Rejects a bad array declaration from a file.
    @Test
    public void rejectsBadArrayDeclaration() {
        assertThrows(ParseException.class, () -> ParserRunner.parseFile(
                Path.of("src/test/resources/syntax/incorrect/test_bad_array_declaration.msm")));
    }

    //Rejects a bad enum declaration from a file.
    @Test
    public void rejectsBadEnumSyntaxFile() {
        assertThrows(ParseException.class, () -> ParserRunner.parseFile(
                Path.of("src/test/resources/syntax/incorrect/test_bad_enum_syntax.msm")));
    }

    //Rejects a fork without a block body from a file.
    @Test
    public void rejectsForkWithoutBlock() {
        assertThrows(ParseException.class, () -> ParserRunner.parseFile(
                Path.of("src/test/resources/syntax/incorrect/test_fork_without_block.msm")));
    }

    //Rejects an if statement without a block body from a file.
    @Test
    public void rejectsIfWithoutBlock() {
        assertThrows(ParseException.class, () -> ParserRunner.parseFile(
                Path.of("src/test/resources/syntax/incorrect/test_if_without_block.msm")));
    }

    //Rejects an expression with a missing sub-expression from a file.
    @Test
    public void rejectsMissingExpressionSubExpr() {
        assertThrows(ParseException.class, () -> ParserRunner.parseFile(
                Path.of("src/test/resources/syntax/incorrect/test_missing_expression_subexpr.msm")));
    }

    //Rejects a statement with a missing semicolon from a file.
    @Test
    public void rejectsMissingSemicolog() {
        assertThrows(ParseException.class, () -> ParserRunner.parseFile(
                Path.of("src/test/resources/syntax/incorrect/test_missing_semicolon.msm")));
    }

    //Rejects a lock operation with wrong syntax from a file.
    @Test
    public void rejectsWrongLockOperationSyntax() {
        assertThrows(ParseException.class, () -> ParserRunner.parseFile(
                Path.of("src/test/resources/syntax/incorrect/test_wrong_lock_operation_syntax.msm")));
    }

    //Accepts a syntactically correct program from a file.
    @Test
    public void acceptsCorrectSyntax() {
        assertDoesNotThrow(() -> ParserRunner.parseFile(
                Path.of("src/test/resources/syntax/correct/test_correct_syntax.msm")));
    }

    //Accepts a syntactically correct concurrency program from a file.
    @Test
    public void acceptsCorrectConcurrencySyntax() {
        assertDoesNotThrow(() -> ParserRunner.parseFile(
                Path.of("src/test/resources/syntax/correct/test_correct_conncurency_syntax.msm")));
    }

}
