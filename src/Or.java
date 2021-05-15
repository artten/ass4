/**
 * @author 319339198
 */


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * and expression.
 */
public class Or extends BinaryExpression implements Expression{
    private Expression left;
    private Expression right;
    private String symbol = "|";

    /**
     * constructor.
     * @param left - left side of the expression
     * @param right - right side of the expression
     */
    Or(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * constructor.
     */
    Or() {
        this.left = null;
        this.right = null;
    }

    /**
     * sets the right side of the expression.
     * @param right
     */
    public void setRightOr(Expression right) {
        this.right = right;
    }

    /**
     * sets the left side of the expression.
     * @param left
     */
    public void setLeftOr(Expression left) {
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
            return (left.evaluate(assignment) || right.evaluate(assignment));
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
            return (left.evaluate() || right.evaluate());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        Or or = new Or();
        if ( this.left.toString().equals(var) ) {
            or.setLeftOr(expression);
        }
        else {
            or.setLeftOr(this.left.assign(var,expression));
        }
        if ( this.right.toString().equals(var) ) {
            or.setRightOr(expression);
        }
        else {
            or.setRightOr(this.right.assign(var,expression));
        }
        return or;
    }

    /**
     *  Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     */
    public Expression nandify(){
        Nand nand = new Nand(new Nand(this.left, this.left), new Nand(this.right, this.right));
        return nand;
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    public Expression norify(){
        Nor nor = new Nor(new Nor(this.left, this.right), new Nor(this.left, this.right));
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
            if(exLeft.evaluate() == true) {
                return new Val(true);
            }
        }
        catch (Exception e) {
            try {
                if(exRight.evaluate() == true) {
                    return new Val(true);
                }
                if(exRight.evaluate() == false) {
                    return exLeft;
                }
            }
            catch (Exception e2) {
                if (this.equals()) {
                    return this.right;
                }
                return this;
            }
        }
        try {
            if(exRight.evaluate() == true) {
                return new Val(true);
            }
            if(exRight.evaluate() == false) {
                return new Val(true);
            }
        }
        catch (Exception e) {
            return exRight;
        }

        return and;
    }
}
