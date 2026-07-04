package ut.pp.compiler.checker;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ut.pp.ast.ExprNode;
import ut.pp.ast.ProgramNode;
import ut.pp.ast.StatementNode;
import ut.pp.ast.concurrency.ForkNode;
import ut.pp.ast.concurrency.JoinNode;
import ut.pp.ast.concurrency.LockNode;
import ut.pp.ast.concurrency.LockOpNode;
import ut.pp.ast.expr.*;
import ut.pp.ast.statement.*;
import ut.pp.ast.type.TypeKind;
import ut.pp.ast.type.TypeNode;
import ut.pp.ast.variable.ArrayNode;
import ut.pp.ast.variable.VarNode;
import ut.pp.ast.variable.VariableNode;


/**
 * Performs contextual checks (type checking and scope analysis) on the AST.
 * Collects all errors and throws a {@link CheckerException} at the end if any were found.
 */
public class Checker {
    private final List<String> errors = new ArrayList<>();
    private boolean isInsideLoop = false;
    private final SymbolTable symbols = new SymbolTable();

    /**
     * Checks the given program for type and scope errors.
     * Throws a {@link CheckerException} listing all found errors.
     *
     * @param program the program AST to check
     */
    public void check(ProgramNode program) {
        errors.clear();
        symbols.enterScope();

        for (StatementNode statement : program.statements) {
            checkStatement(statement);
        }

        symbols.exitScope();

        if (!errors.isEmpty()) {
            throw new CheckerException(errors);
        }
    }

    //Sends a statement node to the appropriate check method.
    private void checkStatement(StatementNode statement) {
        if (statement instanceof DeclarationNode declaration) {
            checkDeclaration(declaration);
        } else if (statement instanceof AssignmentNode assignment) {
            checkAssignment(assignment);
        } else if (statement instanceof IfNode ifNode) {
            checkIfNode(ifNode);
        } else if (statement instanceof WhileNode whileNode) {
            checkWhileNode(whileNode);
        } else if (statement instanceof BlockNode block) {
            checkBlock(block);
        } else if (statement instanceof PrintNode print) {
            checkPrint(print);
        } else if (statement instanceof EnumNode enumDeclaration) {
            checkEnum(enumDeclaration);
        } else if (statement instanceof LockNode lock) {
            checkLockDecl(lock);
        } else if (statement instanceof LockOpNode lockOp) {
            checkLockOp(lockOp);
        } else if (statement instanceof ForkNode fork) {
            checkFork(fork);
        } else if (statement instanceof JoinNode) {
            //nothing to check :P
        } else {
            //TODO: add personalized error for statement
        }
    }

    //Returns the type of the given expression, or null if it has an error.
    private TypeNode typeOfExpr(ExprNode expr) {
        if (expr instanceof IntNode) {
            return new TypeNode(TypeKind.INT);
        }
        if (expr instanceof BoolNode) {
            return new TypeNode(TypeKind.BOOL);
        }

        if (expr instanceof VariableNode var) {
            return typeOfVariable(var);
        }

        if (expr instanceof ArrayNode array) {
            return typeOfArray(array);
        }

        if (expr instanceof SingleExprNode singleExpr) {
            return typeOfSingleExpr(singleExpr);
        }

        if (expr instanceof DoubleExprNode doubleExprNode) {
            return typeOfDoubleExpr(doubleExprNode);
        }

        if (expr instanceof ArrayLiteralNode arrayLiteral) {
            return typeOfArrayLiteral(arrayLiteral);
        }

        //TODO: add personalized error for expr
        return null;
    }

    //Returns the array type of an array literal, checking that all elements have the same type.
    private TypeNode typeOfArrayLiteral(ArrayLiteralNode arrayLiteral) {
        if (arrayLiteral.elements.isEmpty()) {
            error("Empty array literal has unknown type.");
            return null;
        }

        TypeNode firstType = typeOfExpr(arrayLiteral.elements.get(0));
        if (firstType == null) {
            return null;
        }
        if (firstType.isArray()) {
            error("Nested arrays are not supported.");
            return null;
        }

        for (int i = 1; i < arrayLiteral.elements.size(); i++) {
            TypeNode currentType = typeOfExpr(arrayLiteral.elements.get(i));

            if (currentType != null && !CheckerUtils.sameType(firstType, currentType)) {
                error("Array literal elements must all have the same type.");
                return null;
            }
        }

        if (firstType.kind == TypeKind.ENUM) {
            return new TypeNode(TypeKind.ENUM, firstType.typeName, arrayLiteral.elements.size());
        }

        return new TypeNode(firstType.kind, arrayLiteral.elements.size());
    }

    //Returns the result type of a binary expression, checking that operand types are compatible.
    private TypeNode typeOfDoubleExpr(DoubleExprNode doubleExpr) {
        TypeNode left = typeOfExpr(doubleExpr.expr1);
        TypeNode right = typeOfExpr(doubleExpr.expr2);

        if (left == null || right == null) {
            return null;
        }

        switch (doubleExpr.operator) {
            case "+", "-", "*", "/" -> {
                if (!CheckerUtils.isInt(left) || !CheckerUtils.isInt(right)) {
                    error("Operator " + doubleExpr.operator + " requires int operands.");
                    return null;
                }
                if (doubleExpr.operator.equals("/")) {
                    if (doubleExpr.expr2 instanceof IntNode intNode) {
                        if (intNode.value == 0) {
                            error("You can not divide with a 0!");
                            return null;
                        }
                    }
                }

                return CheckerUtils.intType();
            }
            case "<", "<=", ">=", ">" -> {
                if (!CheckerUtils.isInt(left) || !CheckerUtils.isInt(right)) {
                    error("Operator " + doubleExpr.operator + " requires int operands.");
                    return null;
                }

                return CheckerUtils.boolType();
            }
            case "==", "!=" -> {
                if (left.isArray() && right.isArray()) {
                    error("Operator " + doubleExpr.operator + " cannot be used on arrays");
                }

                if (!CheckerUtils.sameType(left, right)) {
                    error("Operator " + doubleExpr.operator +
                                  " requires operands of the same type.");
                    return null;
                }

                return CheckerUtils.boolType();
            }
            case "&&", "||" -> {
                if (!CheckerUtils.isBool(left) || !CheckerUtils.isBool(right)) {
                    error("Operator " + doubleExpr.operator + " requires bool operands.");
                    return null;
                }

                return CheckerUtils.boolType();
            }
            default -> {
                error("Unknown binary operator '" + doubleExpr.operator + "'.");
                return null;
            }
        }
    }

    //Returns the result type of a unary expression (currently only !).
    private TypeNode typeOfSingleExpr(SingleExprNode singleExpr) {
        TypeNode exprType = typeOfExpr(singleExpr.expression);
        if (exprType == null) {
            return null;
        }

        if (singleExpr.operator.equals("!")) {
            if (!CheckerUtils.isBool(exprType)) {
                error("Operator ! required a bool expression.");
                return null;
            }

            return CheckerUtils.boolType();
        }

        error("Unknown unary operator: '" + singleExpr.operator + "'.");
        return null;
    }

    //Returns the element type of an array access,
    // checking that the variable is an array and the index is int.
    private TypeNode typeOfArray(ArrayNode array) {
        Symbol symbol = symbols.lookup(array.name);
        if (symbol == null) {
            error("Array '" + array.name + "' is not declared.");
            return null;
        }


        TypeNode arrayType = symbol.getType();
        if (!arrayType.isArray()) {
            error("'" + array.name + "' is not an array.");
            return null;
        }

        TypeNode indexType = typeOfExpr(array.index);
        if (indexType == null) {
            return null;
        }
        if (!CheckerUtils.isInt(indexType)) {
            error("Array index of '" + array.name + "' must be int.");
            return null;
        }

        if (array.index instanceof IntNode i) {
            if (i.value >= arrayType.arrayLength || i.value < 0) {
                error("Array index out of bounds '" + i.value + "'");
                return null;
            }
        }

        return CheckerUtils.elementType(arrayType);
    }

    //Returns the type of a variable reference, also handling enum value names.
    private TypeNode typeOfVariable(VariableNode var) {
        Symbol symbol = symbols.lookup(var.name);

        if (symbol != null) {

            return symbol.getType();
        }

        if (symbols.isEnumVal(var.name)) {
            String enumTypeName = symbols.enumTypeOfVal(var.name);
            return new TypeNode(TypeKind.ENUM, enumTypeName);
        }
        error("Variable or enum value '" + var.name + "' is not declared.");
        return null;
    }

    //Returns the type of an assignment target (plain variable or array element).
    private TypeNode typeOfAssignmentTarget(VarNode target) {
        if (target instanceof VariableNode var) {
            Symbol symbol = symbols.lookup(var.name);
            if (symbol == null) {
                error("Variable '" + var.name + "' is not declared.");
                return null;
            }

            return symbol.getType();
        }

        if (target instanceof ArrayNode array) {
            return typeOfArray(array);
        }

        error("Unknown assignment target.");
        return null;
    }

    //Checks that the value type matches the target type and marks the variable as initialised.
    private void checkAssignment(AssignmentNode assignment) {
        TypeNode targetType = typeOfAssignmentTarget(assignment.target);
        TypeNode valueType = typeOfExpr(assignment.value);

        if (targetType != null && valueType != null &&
                !CheckerUtils.sameType(targetType, valueType)) {
            error("Cannot assign value of type " + CheckerUtils.toString(valueType) +
                          " to target of type " + CheckerUtils.toString(targetType) + ".");
            return;
        }

        if (assignment.target instanceof VariableNode var) {
            symbols.markInitiliazed(var.name);
        }
    }

    //Checks a variable declaration for duplicate names, unknown enum types, and type mismatches.
    private void checkDeclaration(DeclarationNode declaration) {
        if (symbols.isDeclaredInCurrScope(declaration.identifier)) {
            error("Variable '" + declaration.identifier + "' is already declared in this scope.");
            return;
        }


        if (declaration.type.kind == TypeKind.ENUM &&
                !symbols.isEnumType(declaration.type.typeName)) {
            error("Unknown enum type '" + declaration.type.typeName + "'.");
            return;
        }
        if (declaration.value != null) {
            TypeNode valueType = typeOfExpr(declaration.value);

            if (valueType != null && !CheckerUtils.sameType(declaration.type, valueType)) {
                error("Cannot initialize variable '" + declaration.identifier + "' of type " +
                              CheckerUtils.toString(declaration.type) + " with value of type " +
                              CheckerUtils.toString(valueType) + ".");
            }
        }

        symbols.declare(declaration.identifier, declaration.type, true, false);
    }

    //Checks that the if condition is bool and recursively checks both branches.
    private void checkIfNode(IfNode ifNode) {
        TypeNode condition = typeOfExpr(ifNode.condition);
        if (condition != null && !CheckerUtils.isBool(condition)) {
            error("If condition must be bool, but got " + CheckerUtils.toString(condition) + ".");
        }

        checkBlock(ifNode.thenBlock);

        if (ifNode.elseBlock != null) {
            checkBlock(ifNode.elseBlock);
        }
    }


    //Checks that the while condition is bool and marks the body as being inside a loop.
    private void checkWhileNode(WhileNode whileNode) {
        TypeNode condition = typeOfExpr(whileNode.expression);
        if (condition != null && !CheckerUtils.isBool(condition)) {
            error("While condition must be bool, but got " + CheckerUtils.toString(condition) +
                          ".");
        }
        boolean oldValue = isInsideLoop;
        isInsideLoop = true;
        checkBlock(whileNode.block);
        isInsideLoop = oldValue;
    }

    //Opens a new scope, checks every statement in the block, then closes the scope.
    private void checkBlock(BlockNode block) {
        symbols.enterScope();

        for (StatementNode statement : block.statements) {
            checkStatement(statement);
        }

        symbols.exitScope();
    }

    //Checks that the printed expression is well-typed.
    private void checkPrint(PrintNode print) {
        typeOfExpr(print.expression);
    }

    //Checks that the lock name is not already in use and registers it in the symbol table.
    private void checkLockDecl(LockNode lock) {
        if (symbols.isDeclaredInCurrScope(lock.identifier)) {
            error("Identifier '" + lock.identifier + "' already defined in current scope");
            return;
        }

        symbols.declareLock(lock.identifier);
    }

    //Checks that the target of an acquire/release is a declared lock.
    private void checkLockOp(LockOpNode lockOp) {
        Symbol foundSym = symbols.lookup(lockOp.identifier);
        if (foundSym == null) {
            error("Lock '" + lockOp.identifier + "' has not been declared");
        } else {
            if (!foundSym.isLock()) {
                error("Cannot use '" + lockOp.operation + "' on type:" + foundSym.getType().kind);
            }
        }
    }


    //Checks that the fork does not appear inside a loop and validates its body.
    private void checkFork(ForkNode fork) {
        if (isInsideLoop) {
            error("Forks cannot occur inside loops");
        }
        checkBlock(fork.body);
    }


    //Checks that the enum has no duplicate values and that its name is unique.
    private void checkEnum(EnumNode enumD) {
        Set<String> viewd = new HashSet<>();
        for (String value : enumD.enumValues) {
            if (viewd.contains(value)) {
                error("PROBLEM Duplicate enum value'" + value + "'.");
                return;
            }
            viewd.add(value);
        }

        if (symbols.declareEnum(enumD.name, enumD.enumValues)) {
        } else {
            error("PROBLEM Enum name or enum value is already declared.");
        }
    }

    //Adds an error message to the list of errors found so far.
    private void error(String message) {
        errors.add(message);
    }
}
