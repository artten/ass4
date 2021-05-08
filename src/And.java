/**
 * @author 319339198
 */


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * and expression
 */
public class And extends BinaryExpression implements Expression{
    private Expression left;
    private Expression right;

    /**
     * constructor.
     * @param left - left side of the expression
     * @param right - right side of the expression
     */
    And(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * constructor.
     */
    And() {
        this.left = null;
        this.right = null;
    }

    /**
     * sets the right side of the expression.
     * @param right
     */
    public void setRightAnd(Expression right) {
        this.right = right;
    }

    /**
     * sets the left side of the expression.
     * @param left
     */
    public void setLeftAnd(Expression left) {
        this.left = left;
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
            return (left.evaluate(assignment) && right.evaluate(assignment));
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
            left.evaluate();
            right.evaluate();
            return (left.evaluate() && right.evaluate());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of the variables in the expression.
     * @return
     */
    public List<String> getVariables() {
        List<String> list = new LinkedList<String>();
        List<String> leftList = (List<String>) this.left.getVariables();
        List<String> rightList = (List<String>) this.right.getVariables();
        for (String variable : leftList) {
            list.add(variable);
        }
        for (String variable : rightList) {
            list.add(variable);
        }
        return list;
    }

    /**
     * Returns a nice string representation of the expression.
     * @return expression as string
     */
    @Override
    public String toString() {
        // super();
        return "(" + left.toString() + " & " + right.toString() + ")";
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
        And and = new And();
        if ( this.left.toString().equals(var) ) {
            and.setLeftAnd(expression);
        }
        else {
            and.setLeftAnd(this.left.assign(var,expression));
        }
        if ( this.right.toString().equals(var) ) {
            and.setRightAnd(expression);
        }
        else {
            and.setRightAnd(this.right.assign(var,expression));
        }
        return and;
    }
}
