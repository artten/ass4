/**
 * @author 319339198
 */


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * and expression.
 */
public class And extends BinaryExpression implements Expression{
    private Expression left;
    private Expression right;
    private String symbol = "&";

    /**
     * constructor.
     * @param left - left side of the expression
     * @param right - right side of the expression
     */
    And(Expression left, Expression right) {
        this.right = right;
        this.left = left;
    }

    /**
     * constructor.
     */
    And() {
        this.right = null;
        this.left = null;
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
            left.evaluate(assignment);
            right.evaluate(assignment);
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
     * Returns a nice string representation of the expression.
     * @return expression as string
     */
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

    /**
     *  Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     */
    public Expression nandify(){
        Nand nand = new Nand(new Nand(this.left, this.right), new Nand(this.left, this.right));
        return nand;
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    public Expression norify(){
        Nor nor = new Nor(new Nor(this.left, this.left), new Nor(this.right, this.right));
        return nor;
    }

    /**
     * Returned a simplified version of the current expression.
     * @return a simplified version of the current expression.
     */
    public Expression simplify() {
        And and = new And();
        Expression exLeft = this.left.simplify();
        Expression exRight = this.right.simplify();
        try {
            if(exLeft.evaluate() == false) {
                return new Val(false);
            }
        }
        catch (Exception e) {
            try {
                if(exRight.evaluate() == false) {
                    return new Val(false);
                }
                if(exRight.evaluate() == true) {
                    return exLeft;
                }
            }
            catch (Exception e2) {
                if (this.equals()) {
                   return exLeft;
                }
                return this;
            }
        }
        try {
            if(exRight.evaluate() == false) {
                return new Val(false);
            }
            if(exRight.evaluate() == true) {
                return new Val(true);
            }
        }
        catch (Exception e) {
            return exRight;
        }

        return and;
    }
}
