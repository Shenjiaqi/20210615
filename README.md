# About
This is a RPN(reverse polish notation) calculator demo.    
Although a demo, all classes are designed to be extended, and replaced to make up a more powerful calculator, for example:
* One can replace RPNParser with a PNParse or InfixExpressionParser, while other part do not need to change.
* One can implement a common-subexpression-extract-optimizer for ExpressionEvaluator.
* One can add imaginary-number, vector or even matrix support with adding new Operator and extending Parser. 

# How to use
## Examples
* Run command line RPNCalculator with `./run.sh` 
* Run examples with `./run_example.sh`


# Build
## Requests
Built and tested with:
* openjdk-16
* gradle 7.1

## Build
* Build with: `./build.sh`

# Design
## Steps of calculation
1. Tokenization
    * Input: List of String as input string.
    * output: List of Token stands for const value or math operator.
2. Parse
    * Input: PreviousExpressionStack, List of Token.
    * Output: Pop old Expression on PreviousExpressionStack and push new Expressions into it.
3. Evaluate  
    - Generate ExecutionPlan (Not implemented)     
        * Input: List of Expression on the stack.
        * Output: DAG of ExecutionBlock.
    - Optimize (Not implemented)
        * Input: DAG of ExecutionBlock.
        * Output: DAG of ExecutionBlock.
    - Execute (Only naive implementation)
        * Input: DAG of ExecutionBlock.
        * Output: Evaluated Expression on the stack.

## Modules
### CommandLineTool
* Can be invoked and used from command line.
* Preprocess and convert user input to data structure which RPNCalculator expects.
* Show usage guide.
* Show readable error prompt if exists.

### Calculator
* Use parser to convert input sting into Expression.
* Use ExpressionEvaluator to get result of expression.
* Commit calculation and update ExpressionStack.
* Control behavior of following calculation on error.
  * If an error occurred when parsing expression, an error prompt shows and further expression is not evaluated. (as required in doc.)
  * If an error occurred when evaluating expression, an error prompt shows and whole expression this batch is not evaluated. (not mentioned in doc.)
    * Divided by zero.
    * Sqrt(-1) when use a RealNumber calculator.

#### ExpressionStack
Used to record previous calculated expressions.
* Store expression on the stack. 

#### Parser
* Takes a serial of tokens as input and output an Expression.
Currently, only RPNParser supported, may support other parser in the future.

#### Expression
Expression separates parsing of input and evaluating of input.

  * Formed by operators in shape of a tree.
  * Represents result of other operators.
  * Can be further processed by operator.

#### ExpressionEvaluator
In the future, calculator may support heavy calculation, which multithreading execution may be needed. 
* Take an Expression and build an execution plan.
* Optimize the execution plan.
* Evaluate expression(maybe parallel) according to execution plan.

#### Operator
An operator receives a number of ordered Expressions and generate a new Expression.

Typical operator:
* ConstValue-operator: adding a const value to calculator stack is abstracted as an operator.
* Math-operator: perform '+', '-', '*', '/', 'sqrt'.
* Undo-operator: undo previous ConstValue-operator / Math-operator
  * Cannot undo Undo-Operator, Clear-operator.
  * An operator cause error, such as divided by zero can be undone. For example: "1 0 /" can raise an exception 
      while "1 0 / undo" will not.
* Clear-operator: remove all expression on the stack.

#### Token
* Store a string representing an Expression or Operator.
* Record debug info such as line number and column number.

# TODO list

* Add limitation on:
  * Max number of undo-operation, or all expression should be saved in memory. (Current no such limit)
  * Maximum and minimum number allowed in input and on the stack.(Current no such limit)
* Add configuration:
  * Precision of value in calculation(Default 15)
  * Precision of displayed number.(Default 10)
* Add type check when parsing instead of runtime check.
* Add sonar to check style.