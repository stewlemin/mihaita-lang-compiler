grammar MyLang;

// we define a program as a sequence of zero or more statements, followed by EOF
program:
    statement* EOF
    ;

// all statements accepted defined in the grammar below
statement:
    declaration ';' # DeclarationStatement
    |assignment ';' # AssignmentStatement
    |print ';' # PrintStatement
    |ifElse # IfStatement
    |while # WhileStatement
    |block # BlockStatement
    |lockDecl ';' #LockDeclStatement
    |lockOp ';'#LockOpStatement
    |fork #ForkStatement
    |join ';' #JoinStatement
    |enumType ';' # EnumStatement
    ;

// declares a lock variable
lockDecl:
    'lock' ID
    ;

// lock operations for concurrent threads
lockOp:
    'acquire''('ID')'
    |'release''('ID')'
    ;

// starts a new concurrent thread that executes the given block
fork:
    'fork' block
    ;

// waits for forked threads to finish
join:
    'join'
    ;

// variable declaration that can be shared and initiliazed
declaration:
    (SHARED)? type ID ('=' expr)?
    ;

//variables, normal or array
var:
    ID
    |ID '['expr']'
    ;

//assignment to a variable
assignment:
    var '=' expr
    ;

//type rule
type:
    ('int' | 'bool' | ID) ('[' INT ']')?
    ;

//if statement with an optinional else block
ifElse:
    'if' '(' expr ')' block ('else' block)?
    ;

//while loop with a boolean condition and a body
while:
    'while' '(' expr ')' block
    ;

//a nested scope containing zero or more statements
block:
    '{' statement* '}'
    ;

// prints the result of an expression
print:
    'print' expr
    ;

// enum type declaration with one or more enum values
enumType:
    'enum' ID '{' ID (',' ID)* '}'
    ;

// all expressions supported by the language
expr:
    '!' expr # NotExpr
    |expr ('*' | '/') expr # MulExpr
    |expr ('+' | '-')  expr # AddSubExpr
    |expr ('<' | '<=' | '>=' | '>') expr # CompareExpr
    |expr ('==' | '!=') expr # EqualityExpr
    |expr '&&' expr # AndExpr
    |expr '||' expr # OrExpr
    |'(' expr ')' # ParenExpr
    |'[' (expr(','expr)*)? ']' # ArrayLiteralExpr
    |INT # IntExpr
    |BOOL # BoolExpr
    |var # VarExpr
    ;

// keyword to mark variables that are stored in the shared memory
SHARED:
    'shared'
    ;

//boolean literals
BOOL:
    'TRUE'
    |'FALSE'
    ;

// identifier names for variables, enums and locks
ID:
    [a-zA-Z]+[_0-9a-zA-Z']*
    ;

// integer literals
INT:
    '0'
    |[1-9][0-9]*
    ;

//single-line comments
COMMENT : '//' (~('\n'))* -> skip;

//whitespace
WS : [ \n\t\r]+ -> skip;
