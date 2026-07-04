package ut.pp.tests;

import org.junit.jupiter.api.Test;
import ut.pp.ast.ProgramNode;
import ut.pp.compiler.checker.Checker;
import ut.pp.compiler.checker.CheckerException;
import ut.pp.compiler.parser.ParserRunner;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestChecker {

    //Checks source code directly.
    private void check(String input) {
        ProgramNode root = ParserRunner.parseString(input);
        new Checker().check(root);
    }

    //Checks an already parsed program.
    private void checkNode(ProgramNode root) {
        new Checker().check(root);
    }

    //Accepts integer expressions.
    @Test
    public void intExprOk() {
        assertDoesNotThrow(() -> check("int x = 1 + 2;"));
    }

    //Accepts boolean expressions.
    @Test
    public void boolExprOk() {
        assertDoesNotThrow(() -> check("bool b = TRUE && FALSE;"));
    }

    //Rejects a boolean stored as an integer.
    @Test
    public void intBoolFail() {
        assertThrows(CheckerException.class, () -> check("int x = FALSE;"));
    }

    //Rejects an integer stored as a boolean.
    @Test
    public void boolIntFail() {
        assertThrows(CheckerException.class, () -> check("bool b = 5;"));
    }

    //Rejects two declarations with the same name.
    @Test
    public void sameScopeFail() {
        assertThrows(CheckerException.class, () -> check("int x = 1; int x = 2;"));
    }

    //Rejects use of an undeclared variable.
    @Test
    public void undeclaredUseFail() {
        assertThrows(CheckerException.class, () -> check("int x = y;"));
    }

    //Rejects assignment to an undeclared variable.
    @Test
    public void undeclaredAssignFail() {
        assertThrows(CheckerException.class, () -> check("x = 5;"));
    }

    //Rejects assignment with the wrong type.
    @Test
    public void assignTypeFail() {
        assertThrows(CheckerException.class, () -> check("int x = 1; x = TRUE;"));
    }

    //Accepts assignment with the correct type.
    @Test
    public void assignOk() {
        assertDoesNotThrow(() -> check("int x = 1; x = 2;"));
    }

    //Rejects arithmetic with booleans.
    @Test
    public void boolMathFail() {
        assertThrows(CheckerException.class, () -> check("int x = 1 + TRUE;"));
    }

    //Rejects ordering comparisons on booleans.
    @Test
    public void boolCompareFail() {
        assertThrows(CheckerException.class, () -> check("bool b = TRUE > FALSE;"));
    }

    //Rejects boolean AND on integers.
    @Test
    public void intAndFail() {
        assertThrows(CheckerException.class, () -> check("bool b = 1 && 2;"));
    }

    //Rejects equality between different types.
    @Test
    public void mixedEqualFail() {
        assertThrows(CheckerException.class, () -> check("bool b = 1 == TRUE;"));
    }

    //Accepts equality between matching types.
    @Test
    public void sameEqualOk() {
        assertDoesNotThrow(() -> check("bool b = 1 == 2;"));
    }

    //Rejects NOT on an integer.
    @Test
    public void intNotFail() {
        assertThrows(CheckerException.class, () -> check("bool b = !5;"));
    }

    //Rejects an integer if-condition.
    @Test
    public void ifIntFail() {
        assertThrows(CheckerException.class, () -> check("if (5) { print 1; }"));
    }

    //Rejects an integer while-condition.
    @Test
    public void whileIntFail() {
        assertThrows(CheckerException.class, () -> check("while (5) { print 1; }"));
    }

    //Accepts a boolean if-condition.
    @Test
    public void ifBoolOk() {
        assertDoesNotThrow(() -> check("int x = 1; if (x > 0) { print x; }"));
    }

    //Allows the same name in a nested scope.
    @Test
    public void innerRedeclareOk() {
        assertDoesNotThrow(() -> check("int x = 1; if (TRUE) { int x = 2; }"));
    }

    //Rejects a variable used outside its scope.
    @Test
    public void scopeUseFail() {
        assertThrows(CheckerException.class, () -> check("if (TRUE) { int y = 1; } print y;"));
    }

    //Allows an outer variable inside a nested scope.
    @Test
    public void outerUseOk() {
        assertDoesNotThrow(() -> check("int x = 1; if (TRUE) { print x; }"));
    }

    //Accepts use after assignment.
    @Test
    public void initThenUseOk() {
        assertDoesNotThrow(() -> check("int x; x = 5; print x;"));
    }

    //Accepts an array with one element type.
    @Test
    public void arrayLiteralOk() {
        assertDoesNotThrow(() -> check("int[3] a = [1, 2, 3];"));
    }

    //Rejects mixed types in an array.
    @Test
    public void mixedArrayFail() {
        assertThrows(CheckerException.class, () -> check("int[2] a = [1, TRUE];"));
    }

    //Rejects a non-integer array index.
    @Test
    public void boolIndexFail() {
        assertThrows(CheckerException.class,
                () -> check("int[3] a = [1,2,3]; int x = a[TRUE];"));
    }

    //Rejects indexing a normal variable.
    @Test
    public void nonArrayIndexFail() {
        assertThrows(CheckerException.class, () -> check("int x = 1; int y = x[0];"));
    }

    //Rejects nested arrays.
    @Test
    public void nestedArrayFail() {
        assertThrows(CheckerException.class, () -> check("int[3] x; x = [[1,2],32];"));
    }

    //Rejects a fork inside a conditional inside a loop.
    @Test
    public void forkInIfLoopFail() {
        assertThrows(CheckerException.class,
                () -> check("bool x = TRUE; while(x){ if(x) { fork { x = FALSE;}}}"));
    }

    //Rejects a fork inside a loop.
    @Test
    public void forkInLoopFail() {
        assertThrows(CheckerException.class,
                () -> check("bool x = TRUE; int balance = 0; while(x){fork{balance = balance + 100;}}"));
    }

    //Accepts nested forks.
    @Test
    public void nestedForkOk() {
        assertDoesNotThrow(() -> check("fork{fork{int x = 21;}}"));
    }

    //Accepts acquiring a declared lock.
    @Test
    public void lockAcquireOk() {
        assertDoesNotThrow(() -> check("lock baustela; acquire(baustela);"));
    }

    //Rejects a nested fork inside a loop.
    @Test
    public void nestedForkLoopFail() {
        assertThrows(CheckerException.class, () -> check("fork{ while(TRUE){fork{int x = 1;}}}"));
    }

    //Rejects use of an undeclared lock.
    @Test
    public void missingLockFail() {
        assertThrows(CheckerException.class, () -> check("int x = 1; release(baustela);"));
    }

    //Rejects lock operations on other types.
    @Test
    public void wrongLockTypeFail() {
        assertThrows(CheckerException.class, () -> check("int x = 1; acquire(x);"));
    }

    //Rejects declaring the same lock twice.
    @Test
    public void lockRedeclareFail() {
        assertThrows(CheckerException.class, () -> check("lock aura; lock aura;"));
    }

    //Rejects a lock with an existing variable name.
    @Test
    public void lockVarNameFail() {
        assertThrows(CheckerException.class, () -> check("int terzic; lock terzic;"));
    }

    //Accepts a default integer value.
    @Test
    public void defaultIntOk() {
        assertDoesNotThrow(() -> check("int x; print x;"));
    }

    //Accepts a default boolean value.
    @Test
    public void defaultBoolOk() {
        assertDoesNotThrow(() -> check("bool b; print b;"));
    }

    //Accepts a default integer array.
    @Test
    public void defaultIntArrayOk() {
        assertDoesNotThrow(() -> check("""
                int[3] a;
                print a[0];
                print a[1];
                print a[2];
                """));
    }

    //Accepts a default boolean array.
    @Test
    public void defaultBoolArrayOk() {
        assertDoesNotThrow(() -> check("""
                bool[2] flags;
                print flags[0];
                print flags[1];
                """));
    }

    //Accepts an enum and enum variable.
    @Test
    public void enumVarOk() {
        assertDoesNotThrow(() -> check("""
                enum Color { RED, GREEN, BLUE };
                Color c = GREEN;
                print c;
                """));
    }

    //Rejects an unknown enum type.
    @Test
    public void unknownEnumFail() {
        assertThrows(CheckerException.class, () -> check("Color c = RED;"));
    }

    //Rejects a repeated value in one enum.
    @Test
    public void duplicateEnumValueFail() {
        assertThrows(CheckerException.class, () -> check("enum Color { RED, GREEN, RED };"));
    }

    //Rejects the same value in different enums.
    @Test
    public void duplicateEnumAcrossFail() {
        assertThrows(CheckerException.class, () -> check("""
                enum Color { RED, GREEN };
                enum Direction { LEFT, RED };
                """));
    }

    //Rejects assignment between different enum types.
    @Test
    public void enumAssignFail() {
        assertThrows(CheckerException.class, () -> check("""
                enum Color { RED, GREEN };
                enum Direction { LEFT, RIGHT };

                Color c = RED;
                c = LEFT;
                """));
    }

    //Accepts equality within one enum type.
    @Test
    public void enumEqualOk() {
        assertDoesNotThrow(() -> check("""
                enum Color { RED, GREEN };
                bool same = RED == GREEN;
                """));
    }

    //Rejects equality between different enum types.
    @Test
    public void enumEqualFail() {
        assertThrows(CheckerException.class, () -> check("""
                enum Color { RED, GREEN };
                enum Direction { LEFT, RIGHT };

                bool same = RED == LEFT;
                """));
    }

    //Accepts an array of enum values.
    @Test
    public void enumArrayOk() {
        assertDoesNotThrow(() -> check("""
                enum Color { RED, GREEN, BLUE };
                Color[2] colors = [RED, GREEN];
                """));
    }

    //Rejects an array literal with the wrong length.
    @Test
    public void arrayLengthFail() {
        assertThrows(CheckerException.class, () -> check("int[3] a = [1, 2];"));
    }

    //Accepts copying arrays of the same length.
    @Test
    public void arrayCopyOk() {
        assertDoesNotThrow(() -> check("""
                int[2] a = [1, 2];
                int[2] b = a;
                """));
    }

    //Rejects copying arrays of different lengths.
    @Test
    public void arrayCopyLengthFail() {
        assertThrows(CheckerException.class, () -> check("""
                int[2] a = [1, 2];
                int[3] b = a;
                """));
    }

    //Accepts a correct array element assignment.
    @Test
    public void arraySetOk() {
        assertDoesNotThrow(() -> check("""
                int[3] a = [1, 2, 3];
                a[0] = 99;
                """));
    }

    //Rejects a wrong array element type.
    @Test
    public void arraySetFail() {
        assertThrows(CheckerException.class, () -> check("""
                int[3] a = [1, 2, 3];
                a[0] = TRUE;
                """));
    }

    //Rejects a known out-of-bounds index.
    @Test
    public void constIndexFail() {
        assertThrows(CheckerException.class, () -> check("""
                int[3] a = [1, 2, 3];
                print a[3];
                """));
    }

    //Rejects an empty array.
    @Test
    public void emptyArrayFail() {
        assertThrows(CheckerException.class, () -> check("int[0] a = [];"));
    }

    //Rejects division by a known zero.
    @Test
    public void zeroDivisionFail() {
        assertThrows(CheckerException.class, () -> check("int x = 10 / 0;"));
    }

    //Accepts division when zero is only known at runtime.
    @Test
    public void variableDivisionOk() {
        assertDoesNotThrow(() -> check("""
                int zero = 0;
                int x = 10 / zero;
                """));
    }

    //Accepts a shared variable protected by a lock.
    @Test
    public void sharedForkLockOk() {
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

    //Rejects a lock used outside its scope.
    @Test
    public void scopedLockFail() {
        assertThrows(CheckerException.class, () -> check("""
                {
                    lock bank;
                }

                acquire(bank);
                """));
    }

    //Accepts several forks followed by join.
    @Test
    public void multiForkJoinOk() {
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

    //Accepts the correct array test file.
    @Test
    public void arrayFileOk() {
        assertDoesNotThrow(() -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/correct/array.msm"))));
    }

    //Accepts the correct boolean test file.
    @Test
    public void boolFileOk() {
        assertDoesNotThrow(() -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/correct/boolean_op.msm"))));
    }

    //Accepts the correct enum test file.
    @Test
    public void enumFileOk() {
        assertDoesNotThrow(() -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/correct/enum.msm"))));
    }

    //Accepts the correct fork test file.
    @Test
    public void forkFileOk() {
        assertDoesNotThrow(() -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/correct/fork.msm"))));
    }

    //Accepts the correct integer test file.
    @Test
    public void intFileOk() {
        assertDoesNotThrow(() -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/correct/int_op.msm"))));
    }

    //Accepts the correct multi-scope test file.
    @Test
    public void scopeFileOk() {
        assertDoesNotThrow(() -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/correct/multiscope_declaration.msm"))));
    }

    //Rejects array equality in the test file.
    @Test
    public void arrayEqualityFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/array_equality.msm"))));
    }

    //Rejects an out-of-bounds array index.
    @Test
    public void boundsFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/array_index_out_of_bounds.msm"))));
    }

    //Rejects mixed array types in the test file.
    @Test
    public void mixedArrayFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/array_with_mixed_types.msm"))));
    }

    //Rejects boolean operations on integers.
    @Test
    public void boolOnIntFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/bool_op_on_int.msm"))));
    }

    //Rejects a comparison with different types.
    @Test
    public void compareFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/comparison_type_mismatch.msm"))));
    }

    //Rejects division by zero in the test file.
    @Test
    public void zeroFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/division_by_int_zero.msm"))));
    }

    //Rejects a repeated enum value.
    @Test
    public void enumValueFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/duplicate_enum_val.msm"))));
    }

    //Rejects a fork inside a loop.
    @Test
    public void forkLoopFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/fork_inside_loop.msm"))));
    }

    //Rejects a non-boolean if-condition.
    @Test
    public void ifTypeFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/if_cond_not_bool.msm"))));
    }

    //Rejects integer operations on booleans.
    @Test
    public void intOnBoolFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/int_op_on_bool.msm"))));
    }

    //Rejects multiple declarations in one scope.
    @Test
    public void declarationFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/multiple_declaration.msm"))));
    }

    //Rejects NOT on an integer.
    @Test
    public void notFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/not_int.msm"))));
    }

    //Rejects a wrong initialization type.
    @Test
    public void initTypeFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/type_mismatch_init.msm"))));
    }

    //Rejects an undeclared lock in the test file.
    @Test
    public void lockFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/undeclared_lock.msm"))));
    }

    //Rejects an undeclared variable in the test file.
    @Test
    public void variableFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/undeclared_variable.msm"))));
    }

    //Rejects an array index with the wrong type.
    @Test
    public void indexTypeFileFail() {
        assertThrows(CheckerException.class, () -> checkNode(ParserRunner.parseFile(
                Path.of("src/test/resources/contextual/incorrect/wrong_type_array_index.msm"))));
    }
}
