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

    /**
     * constructor.
     * @param left - left side of the expression
     * @param right - right side of the expression
     */
    Xor(Expression left, Expression right) {
        super(left, right, " ^ ");
    }

    /**
     * constructor.
     */
    Xor() {
        super();
    }

    /**
     * sets the right side of the expression.
     * @param right
     */
    public void setRightXor(Expression right) {
        super.setRightAnd(right);
    }

    /**
     * sets the left side of the expression.
     * @param left
     */
    public void setLeftXor(Expression left) {
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
            return (!(super.left.evaluate(assignment) && super.right.evaluate(assignment))
                    && super.left.evaluate(assignment) != super.right.evaluate(assignment));
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
            return (!(super.left.evaluate() && super.right.evaluate()) && super.left.evaluate() != super.right.evaluate());
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
     * Returns a new expression in which all occurrences of the variable.
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * @param var - the var to change
     * @param expression - the expression to change with
     * @return
     */
    public Expression assign(String var, Expression expression) {
        Xor xor = new Xor();
        xor.setSymbol(" ^ ");
        if ( super.left.toString().equals(var) ) {
            xor.setLeftXor(expression);
        }
        else {
            xor.setLeftXor(super.left.assign(var,expression));
        }
        if ( super.right.toString().equals(var) ) {
            xor.setRightXor(expression);
        }
        else {
            xor.setRightXor(super.right.assign(var,expression));
        }
        return xor;
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
        Nand nand = new Nand(new Nand(leftNand, new Nand(leftNand, rightNand)),new Nand(rightNand, new Nand(leftNand, rightNand)));
        return nand;
   }

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    public Expression norify(){
         Expression leftNand = super.left.norify();
         Expression rightNand = super.right.norify();
         Nor nor = new Nor(new Nor(new Nor(leftNand, leftNand),new Nor(rightNand, rightNand)),new Nor(leftNand, rightNand));
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
            if(exLeft.evaluate() == false) {
                return exRight;
            }
        }
        catch (Exception e) {
            try {
                if(exRight.evaluate() == false) {
                    return new Val(false);
                }
                if(exRight.evaluate() == true) {
                    return new Not(exLeft);
                }
            }
            catch (Exception e2) {
                if (this.equals(exLeft, exRight)) {
                    return new Val(false);
                }
                return new Xor(exLeft, exRight);
            }
        }
        try {
            if(exRight.evaluate() == false) {
                return new Val(true);
            }
            if(exRight.evaluate() == true) {
                return new Val(false);
            }
        }
        catch (Exception e) {
            return new Not(exRight);
        }

        return and;
    }
}
