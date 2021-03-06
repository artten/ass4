/**
 * @author 319339198
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Not extends  UnaryExpression implements Expression {
    /**
     * constructor.
     * @param expression - the expression to add
     */
    Not(Expression expression) {
        super(expression, "~");
    }

    /**
     * constructor.
     */
    Not() {
        super();
    }

    public void setExpression(Expression expression) {
        super.expression = expression;
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
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        try {
            super.expression.evaluate(assignment);
            return !(super.expression.evaluate(assignment));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return
     * @throws Exception
     */
    public Boolean evaluate() throws Exception {
        try {
            super.expression.evaluate();
            return !(super.expression.evaluate());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getVariables() {
        return super.getVariables();
    }

    /**
     * Returns a nice string representation of the expression.
     * @return expression as string
     */
    @Override
    public String toString() {
        return symbol + "(" + expression + ")";
    }

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * @param var - the var to change
     * @param expression - the expression to change with
     * @return
     */
    public Expression assign(String var, Expression expression) {
        Not not = new Not();
        not.setSymbol("~");
        if (super.expression.toString().equals(var) ) {
            not.setExpression(expression);
        }
        else {
            not.setExpression(super.expression.assign(var, expression));
        }
        return not;
    }

    /**
     *  Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     */
    public Expression nandify(){
        Nand nand = new Nand(super.expression.nandify(), super.expression.nandify());
        return nand;
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    public Expression norify(){
        Nor nor = new Nor(super.expression.norify(), super.expression.norify());
        return nor;
    }

    /**
     * Returned a simplified version of the current expression.
     * @return a simplified version of the current expression.
     */
    public Expression simplify() {
        return new Not(super.expression.simplify());
    }
}


