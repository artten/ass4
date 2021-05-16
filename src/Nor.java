/**
 * @author 319339198
 */


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * and expression.
 */
public class Nor extends BinaryExpression implements Expression{

    /**
     * constructor.
     * @param left - left side of the expression
     * @param right - right side of the expression
     */
    Nor(Expression left, Expression right) {
        super(left, right, " ↓ ");
    }

    /**
     * constructor.
     */
    Nor() {
        super();
    }

    /**
     * sets the right side of the expression.
     * @param right
     */
    public void setRightAnd(Expression right) {
        super.setRightAnd(right);
    }

    /**
     * sets the left side of the expression.
     * @param left
     */
    public void setLeftAnd(Expression left) {
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
            super.left.evaluate(assignment);
            super.right.evaluate(assignment);
            return (!(super.left.evaluate(assignment) || super.right.evaluate(assignment)));
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
            super.left.evaluate();
            super.right.evaluate();
            return (!(super.left.evaluate() || super.right.evaluate()));
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
        Nor nor = new Nor();
        nor.setSymbol(" ↓ ");
        if (super.left.toString().equals(var) ) {
            nor.setLeftAnd(expression);
        }
        else {
            nor.setLeftAnd(super.left.assign(var,expression));
        }
        if ( super.right.toString().equals(var) ) {
            nor.setRightAnd(expression);
        }
        else {
            nor.setRightAnd(super.right.assign(var,expression));
        }
        return nor;
    }

    /**
     * Returns a list of the variables in the expression.
     * @return
     */
    public List<String> getVariables() {
        return super.getVariables();
    }

    /**
     *  Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     */
    public Expression nandify(){
        Expression leftNand = super.left.nandify();
        Expression rightNand = super.right.nandify();
        Nand nand = new Nand(new Nand(new Nand(leftNand, leftNand), new Nand(rightNand, rightNand)),
                new Nand(new Nand(leftNand, leftNand), new Nand(rightNand, rightNand)));
        return nand;
    }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    public Expression norify(){
        Expression leftNand = super.left.norify();
        Expression rightNand = super.right.norify();
        Nor nor = new Nor(leftNand, rightNand);
        return nor;
    }

    public Expression simplify() {
        And and = new And();
        Expression exLeft = super.left.simplify();
        Expression exRight = super.right.simplify();
        try {
            if(exLeft.evaluate() == false) {
                return new Not(exRight);
            }
        }
        catch (Exception e) {
            try {
                if(exRight.evaluate() == false) {
                    return new Not(exLeft);
                }
                if(exRight.evaluate() == true) {
                    return new Val(false);
                }
            }
            catch (Exception e2) {
                if (this.equals(exLeft, exRight)) {
                    return new Not(exLeft);
                }
                return new Nor(exLeft, exRight);
            }
        }
        try {
            if(exLeft.evaluate() == true) {
                return new Val(false);
            }
        }
        catch (Exception e) { }

        return and;
    }
}
