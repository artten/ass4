/**
 * @author 319339198
 */


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * and expression.
 */
abstract class BasicExpression implements Expression{
    private String symbol;

    BasicExpression () {
    }


    /**
     *
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result. If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     * @param assignment - maps all the variable to values
     * @return - the new value of the
     * @throws Exception
     */
    abstract public Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return
     * @throws Exception
     */
    abstract public Boolean evaluate() throws Exception;

    /**
     * Returns a list of the variables in the expression.
     * @return
     */
    abstract public List<String> getVariables();

    /**
     * Returns a nice string representation of the expression.
     * @return expression as string
     */
    abstract public String toString();

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * @param var - the var to change
     * @param expression - the expression to change with
     * @return
     */
    abstract public Expression assign(String var, Expression expression);

    /**
     *  Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     */
    abstract public Expression nandify();

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    abstract public Expression norify();
}
