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
    private String symbol = " | ";

    /**
     * constructor.
     * @param left - left side of the expression
     * @param right - right side of the expression
     */
    Or(Expression left, Expression right) {
        super(left, right , " | ");
    }

    /**
     * constructor.
     */
    Or() {
        super();
    }

    /**
     * sets the right side of the expression.
     * @param right
     */
    public void setRightOr(Expression right) {
        super.setRightAnd(right);
    }

    /**
     * sets the left side of the expression.
     * @param left
     */
    public void setLeftOr(Expression left) {
        super.setLeftAnd(left);
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
     * Returns a list of the variables in the expression.
     * @return
     */
    public List<String> getVariables() {
        return super.getVariables();
    }

    /**
     * Returns a nice string representation of the expression.
     * @return expression as string
     */
    @Override
    public String toString() {
        return super.toString();
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
        or.setSymbol(" | ");
        if ( super.left.toString().equals(var) ) {
            or.setLeftOr(expression);
        }
        else {
            or.setLeftOr(super.left.assign(var,expression));
        }
        if ( super.right.toString().equals(var) ) {
            or.setRightOr(expression);
        }
        else {
            or.setRightOr(super.right.assign(var,expression));
        }
        return or;
    }

    /**
     *  Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     */
    public Expression nandify(){
        Expression leftNand = super.left.nandify();
        Expression rightNand = super.right.nandify();
        Nand nand = new Nand(new Nand(leftNand, leftNand), new Nand(rightNand , rightNand ));
        return nand;
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    public Expression norify(){
        Expression leftNand = super.left.norify();
        Expression rightNand = super.right.norify();
        Nor nor = new Nor(new Nor(leftNand, rightNand), new Nor(leftNand, rightNand));
        return nor;
    }

    /**
     * Returned a simplified version of the current expression.
     * @return a simplified version of the current expression.
     */
    public Expression simplify() {
        And and = new And();
        Expression exLeft = super.left.simplify();
        Expression exRight = super.right.simplify();
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
                if (this.equals(exLeft, exRight)) {
                    return super.right;
                }
                return new Or(exLeft, exRight);
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
