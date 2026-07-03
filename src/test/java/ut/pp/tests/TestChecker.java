package ut.pp.tests;

import ut.pp.ast.ProgramNode;
import ut.pp.compiler.checker.Checker;
import ut.pp.compiler.checker.CheckerException;

import org.junit.jupiter.api.Test;
import ut.pp.compiler.parser.ParserRunner;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestChecker {
    private void check(String input) {
        ProgramNode root = ParserRunner.parseString(input);
        new Checker().check(root);
    }

    private void checkPrgNode(ProgramNode root) {
        new Checker().check(root);
    }

    @Test
    public void acceptsExprIntDeclaration() {
        assertDoesNotThrow(() -> check("int x = 1 + 2;"));
    }

    @Test
    public void acceptsExprBoolDeclaration() {
        assertDoesNotThrow(() -> check("bool b = TRUE && FALSE;"));
    }

    @Test
    public void rejectsIntDeclaredWithBool() {
        assertThrows(CheckerException.class, () -> check("int x = FALSE;"));
    }

    @Test
    public void rejectsBoolDeclaredWithInt() {
        assertThrows(CheckerException.class, () -> check("bool b = 5;"));
    }

    @Test
    public void rejectsRedeclarationInSameScope() {
        assertThrows(CheckerException.class, () -> check("int x = 1; int x = 2;"));
    }

    @Test
    public void rejectsUndeclaredVariableUse() {
        assertThrows(CheckerException.class, () -> check("int x = y;"));
    }

    @Test
    public void rejectsAssignmentToUndeclared() {
        assertThrows(CheckerException.class, () -> check("x = 5;"));
    }

    @Test
    public void rejectsAssignmentTypeMismatch() {
        assertThrows(CheckerException.class, () -> check("int x = 1; x = TRUE;"));
    }

    @Test
    public void acceptsValidAssignment() {
        assertDoesNotThrow(() -> check("int x = 1; x = 2;"));
    }

    @Test
    public void rejectsArithmeticOnBool() {
        assertThrows(CheckerException.class, () -> check("int x = 1 + TRUE;"));
    }

    @Test
    public void rejectsComparisonOnBool() {
        assertThrows(CheckerException.class, () -> check("bool b = TRUE > FALSE;"));
    }

    @Test
    public void rejectsAndOnInt() {
        assertThrows(CheckerException.class, () -> check("bool b = 1 && 2;"));
    }

    @Test
    public void rejectsEqualityOnDiffTypes() {
        assertThrows(CheckerException.class, () -> check("bool b = 1 == TRUE;"));
    }

    @Test
    public void acceptsEqualityOnSameType() {
        assertDoesNotThrow(() -> check("bool b = 1 == 2;"));
    }

    @Test
    public void rejectsNotOnInt() {
        assertThrows(CheckerException.class, () -> check("bool b = !5;"));
    }

    @Test
    public void rejectsNonBoolIfCondition() {
        assertThrows(CheckerException.class, () -> check("if (5) { print 1; }"));
    }

    @Test
    public void rejectsNonBoolWhileCondition() {
        assertThrows(CheckerException.class, () -> check("while (5) { print 1; }"));
    }

    @Test
    public void acceptsBoolIfCondition() {
        assertDoesNotThrow(() -> check("int x = 1; if (x > 0) { print x; }"));
    }

    @Test
    public void acceptsRedeclarationInDifferentScopes() {
        assertDoesNotThrow(() -> check("int x = 1; if (TRUE) { int x = 2; }"));
    }

    @Test
    public void rejectsVariableOutsideItsScope() {
        assertThrows(CheckerException.class, () -> check("if (TRUE) { int y = 1; } print y;"));
    }

    @Test
    public void acceptsOuterVariableInInnerScope() {
        assertDoesNotThrow(() -> check("int x = 1; if (TRUE) { print x; }"));
    }

    @Test
    public void acceptsUseAfterInitialization() {
        assertDoesNotThrow(() -> check("int x; x = 5; print x;"));
    }

    @Test
    public void acceptsSameTypeArrayLiteral() {
        assertDoesNotThrow(() -> check("int[3] a = [1, 2, 3];"));
    }

    @Test
    public void rejectsMixedTypeArrayLiteral() {
        assertThrows(CheckerException.class, () -> check("int[2] a = [1, TRUE];"));
    }

    @Test
    public void rejectsNonIntArrayIndex() {
        assertThrows(CheckerException.class,
                () -> check("int[3] a = [1,2,3]; int x = a[TRUE];"));
    }

    @Test
    public void rejectsIndexingNonArray() {
        assertThrows(CheckerException.class, () -> check("int x = 1; int y = x[0];"));
    }

    @Test
    public void rejectsNestedArray() {
        assertThrows(CheckerException.class, () -> check("int[3] x; x = [[1,2],32];"));
    }

    @Test
    public void rejectsForkInsideConditionalInsideLoop() {
        assertThrows(CheckerException.class, () -> check("bool x = TRUE; while(x){ if(x) { fork { x = FALSE;}}}"));
    }

    @Test
    public void rejectsForkInsideLoopFile() {
        assertThrows(CheckerException.class, () -> check("bool x = TRUE; int balance = 0; while(x){fork{balance = balance + 100;}}"));
    }

    @Test
    public void acceptsNestedForks() {
        assertDoesNotThrow(() -> check("fork{fork{int x = 21;}}"));
    }

    @Test
    public void acceptsAcquiringDeclaredLock() {
        assertDoesNotThrow(() -> check("lock baustela; acquire(baustela);"));
    }

    @Test
    public void rejectsNestedForkInsideLoop() {
        assertThrows(CheckerException.class, () -> check("fork{ while(TRUE){fork{int x = 1;}}}"));
    }

    @Test
    public void rejectsOperationUndeclaredLock() {
        assertThrows(CheckerException.class, () -> check("int x = 1; release(baustela);"));
    }

    @Test
    public void rejectsLockOperationOnOtherType() {
        assertThrows(CheckerException.class, () -> check("int x = 1; acquire(x);"));
    }

    @Test
    public void rejectsRedeclaration() {
        assertThrows(CheckerException.class, () -> check("lock aura; lock aura;"));
    }

    @Test
    public void rejectLockWithSameIdentifierAsVar() {
        assertThrows(CheckerException.class, () -> check("int terzic; lock terzic;"));
    }


    //ADDED THE NEW ONES THEY HAVE TO BE PROVERENI!!!!!!!!11\\
    @Test
    public void acceptsDefaultInitializedInt() {
        assertDoesNotThrow(() -> check("int x; print x;"));
    }

    @Test
    public void acceptsDefaultInitializedBool() {
        assertDoesNotThrow(() -> check("bool b; print b;"));
    }

    @Test
    public void acceptsDefaultInitializedIntArray() {
        assertDoesNotThrow(() -> check("""
                int[3] a;
                print a[0];
                print a[1];
                print a[2];
                """));
    }

    @Test
    public void acceptsDefaultInitializedBoolArray() {
        assertDoesNotThrow(() -> check("""
                bool[2] flags;
                print flags[0];
                print flags[1];
                """));
    }

    @Test
    public void acceptsEnumDeclarationAndVariable() {
        assertDoesNotThrow(() -> check("""
                enum Color { RED, GREEN, BLUE };
                Color c = GREEN;
                print c;
                """));
    }

    @Test
    public void rejectsUnknownEnumType() {
        assertThrows(CheckerException.class, () -> check("Color c = RED;"));
    }

    @Test
    public void rejectsDuplicateEnumValueInSameEnum() {
        assertThrows(CheckerException.class, () -> check("enum Color { RED, GREEN, RED };"));
    }

    @Test
    public void rejectsDuplicateEnumValueAcrossEnums() {
        assertThrows(CheckerException.class, () -> check("""
                enum Color { RED, GREEN };
                enum Direction { LEFT, RED };
                """));
    }

    @Test
    public void rejectsEnumTypeMismatchInAssignment() {
        assertThrows(CheckerException.class, () -> check("""
                enum Color { RED, GREEN };
                enum Direction { LEFT, RIGHT };
                
                Color c = RED;
                c = LEFT;
                """));
    }

    @Test
    public void acceptsEnumEqualitySameType() {
        assertDoesNotThrow(() -> check("""
                enum Color { RED, GREEN };
                bool same = RED == GREEN;
                """));
    }

    @Test
    public void rejectsEnumEqualityDifferentTypes() {
        assertThrows(CheckerException.class, () -> check("""
                enum Color { RED, GREEN };
                enum Direction { LEFT, RIGHT };
                
                bool same = RED == LEFT;
                """));
    }

    @Test
    public void acceptsEnumArrayLiteral() {
        assertDoesNotThrow(() -> check("""
                enum Color { RED, GREEN, BLUE };
                Color[2] colors = [RED, GREEN];
                """));
    }

    @Test
    public void rejectsArrayLiteralWrongLength() {
        assertThrows(CheckerException.class, () -> check("int[3] a = [1, 2];"));
    }

    @Test
    public void acceptsArrayCopySameLength() {
        assertDoesNotThrow(() -> check("""
                int[2] a = [1, 2];
                int[2] b = a;
                """));
    }

    @Test
    public void rejectsArrayCopyDifferentLength() {
        assertThrows(CheckerException.class, () -> check("""
                int[2] a = [1, 2];
                int[3] b = a;
                """));
    }

    @Test
    public void acceptsArrayElementAssignmentCorrectType() {
        assertDoesNotThrow(() -> check("""
                int[3] a = [1, 2, 3];
                a[0] = 99;
                """));
    }

    @Test
    public void rejectsArrayElementAssignmentWrongType() {
        assertThrows(CheckerException.class, () -> check("""
                int[3] a = [1, 2, 3];
                a[0] = TRUE;
                """));
    }

    @Test
    public void rejectsCompileTimeArrayIndexOutOfBounds() {
        assertThrows(CheckerException.class, () -> check("""
                int[3] a = [1, 2, 3];
                print a[3];
                """));
    }

    @Test
    public void rejectsEmptyArrayLiteral() {
        assertThrows(CheckerException.class, () -> check("int[0] a = [];"));
    }

    @Test
    public void rejectsCompileTimeDivisionByZero() {
        assertThrows(CheckerException.class, () -> check("int x = 10 / 0;"));
    }

    @Test
    public void acceptsRuntimeDivisionByVariable() {
        assertDoesNotThrow(() -> check("""
                int zero = 0;
                int x = 10 / zero;
                """));
    }

    @Test
    public void acceptsSharedVariableWithForkAndLock() {
        assertDoesNotThrow(() -> check("""
                shared int balance = 0;
                lock bank;
                
                fork {
                    acquire(bank);
                    balance = balance + 1;
                    release(bank);
                }
                
                join;
                print balance;
                """));
    }

    @Test
    public void rejectsLockUsedOutsideItsScope() {
        assertThrows(CheckerException.class, () -> check("""
                {
                    lock bank;
                }
                
                acquire(bank);
                """));
    }

    @Test
    public void acceptsMultipleForksThenJoin() {
        assertDoesNotThrow(() -> check("""
                shared int x = 0;
                lock l;
                
                fork {
                    acquire(l);
                    x = x + 1;
                    release(l);
                }
                
                fork {
                    acquire(l);
                    x = x + 1;
                    release(l);
                }
                
                fork {
                    acquire(l);
                    x = x + 1;
                    release(l);
                }
                
                fork {
                    acquire(l);
                    x = x + 1;
                    release(l);
                }
                
                join;
                print x;
                """));
    }

    @Test
    public void acceptsArray(){
        assertDoesNotThrow(() -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/correct/array.msm"))));
    }

    @Test
    public void acceptsBooleanOp(){
        assertDoesNotThrow(() -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/correct/boolean_op.msm"))));
    }

    @Test
    public void acceptsEnum(){
        assertDoesNotThrow(() -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/correct/enum.msm"))));
    }

    @Test
    public void acceptsFork(){
        assertDoesNotThrow(() -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/correct/fork.msm"))));
    }

    @Test
    public void acceptsIntOp(){
        assertDoesNotThrow(() -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/correct/int_op.msm"))));
    }

    @Test
    public void acceptsMultiscopeDeclaration(){
        assertDoesNotThrow(() -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/correct/multiscope_declaration.msm"))));
    }

    @Test
    public void rejectsArrayEquality(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/array_equality.msm"))));
    }

    @Test
    public void rejectsArrayIndexOutOfBounds(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/array_index_out_of_bounds.msm"))));
    }

    @Test
    public void rejectsArrayWithMixedTypes(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/array_with_mixed_types.msm"))));
    }

    @Test
    public void rejectsBoolOpOnInt(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/bool_op_on_int.msm"))));
    }

    @Test
    public void rejectsComparisonTypeMismatch(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/comparison_type_mismatch.msm"))));
    }

    @Test
    public void rejectsDivisionByIntZero(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/division_by_int_zero.msm"))));
    }

    @Test
    public void rejectsDuplicateEnumVal(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/duplicate_enum_val.msm"))));
    }

    @Test
    public void rejectsForkInsideLoop(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/fork_inside_loop.msm"))));
    }

    @Test
    public void rejectsIfCondNotBool(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/if_cond_not_bool.msm"))));
    }

    @Test
    public void rejectsIntOpOnBool(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/int_op_on_bool.msm"))));
    }

    @Test
    public void rejectsMultipleDeclaration(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/multiple_declaration.msm"))));
    }

    @Test
    public void rejectsNotInt(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/not_int.msm"))));
    }

    @Test
    public void rejectsTypeMismatchInit(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/type_mismatch_init.msm"))));
    }

    @Test
    public void rejectsUndeclaredLock(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/undeclared_lock.msm"))));
    }

    @Test
    public void rejectsUndeclaredVariable(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/undeclared_variable.msm"))));
    }

    @Test
    public void rejectsWrongTypeArrayIndex(){
        assertThrows(CheckerException.class, () -> checkPrgNode(ParserRunner.parseFile(Path.of("src/test/resources/contextual/incorrect/wrong_type_array_index.msm"))));
    }
}
