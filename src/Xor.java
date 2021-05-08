/**
 * @author 319339198
 */


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * and expression.
 */
public class Xor extends BinaryExpression implements Expression{
    private Expression left;
    private Expression right;
    private String symbol = "^";

    /**
     * constructor.
     * @param left - left side of the expression
     * @param right - right side of the expression
     */
    Xor(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * constructor.
     */
    Xor() {
        this.left = null;
        this.right = null;
    }

    /**
     * sets the right side of the expression.
     * @param right
     */
    public void setRightXor(Expression right) {
        this.right = right;
    }

    /**
     * sets the left side of the expression.
     * @param left
     */
    public void setLeftXor(Expression left) {
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
            left.evaluate(assignment);
            right.evaluate(assignment);
            return (!(left.evaluate() && right.evaluate()) && left.evaluate() != right.evaluate());
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
            return (!(left.evaluate() && right.evaluate()) && left.evaluate() != right.evaluate());
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
        List<String> leftList = this.left.getVariables();
        List<String> rightList = this.right.getVariables();
        for (String variable : leftList) {
            if (!list.contains(variable)) {
                list.add(variable);
            }
        }
        for (String variable : rightList) {
            if (!list.contains(variable)) {
                list.add(variable);
            }
        }
        return list;
    }

    /**
     * Returns a nice string representation of the expression.
     * @return expression as string
     */
    @Override
    public String toString() {
        return "(" + left.toString() + symbol + right.toString() + ")";
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
        Xor xor = new Xor();
        if ( this.left.toString().equals(var) ) {
            xor.setLeftXor(expression);
        }
        else {
            xor.setLeftXor(this.left.assign(var,expression));
        }
        if ( this.right.toString().equals(var) ) {
            xor.setRightXor(expression);
        }
        else {
            xor.setRightXor(this.right.assign(var,expression));
        }
        return xor;
    }
}
