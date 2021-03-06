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
abstract class BinaryExpression extends BasicExpression implements Expression{
    protected Expression left;
    protected Expression right;
    protected String symbol;

    /**
     * constructor.
     * @param left - left side of the expression
     * @param right - right side of the expression
     */
    BinaryExpression (Expression left, Expression right, String symbol) {
        this.left = left;
        this.right = right;
        this.symbol = symbol;
    }

    /**
     * constructor.
     */
    BinaryExpression () {
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
     * sets the symbol.
     * @param symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
    public List<String> getVariables() {
        List<String> list = new LinkedList<String>();
        List<String> leftList = left.getVariables();
        List<String> rightList = right.getVariables();
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
    abstract public Expression nandify();

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    abstract public Expression norify();

    /**
     * Returned a simplified version of the current expression.
     */
    abstract public Expression simplify();

    /**
     * compare the left side and the right side.
     * @return true if left side equals to the right side else false
     */
    public boolean equals(Expression left,Expression right) {
        List<String> leftList = left.getVariables().stream().sorted().collect(Collectors.toList());
        List<String> rightList = right.getVariables().stream().sorted().collect(Collectors.toList());
        if (leftList.size() != rightList.size() || !(leftList.equals(rightList))) {
            return false;
        }
        if (leftList.size() == 1 ) {
            if ( leftList.get(0) != rightList.get(0)) {
                return false;
            }
            return true;
        }
        int putTrue = 0;
        for (int i = 0; i < Math.pow(2,leftList.size()) ; i++ ) {
            Map<String, Boolean> map = new TreeMap<String, Boolean>();
            int numberOfTrue = i / leftList.size() + 1;
            int putTruePlus = numberOfTrue;
            int j = 0;
            for(String string : leftList) {
                if (numberOfTrue > 0 && j == putTrue) {
                    numberOfTrue--;
                    map.put(string, true);
                    putTrue = (putTrue + putTruePlus) % leftList.size();
                }
                else {
                    map.put(string, false);
                }
                j++;
               // System.out.println( "i:"+i);
               // System.out.println( numberOfTrue);
               // System.out.println(map);
                //System.out.println(leftList);
            }
            if (i == Math.pow(2,leftList.size()) - 1) {
                for (String ver : leftList) {
                    map.put(ver, false);
                }
            }
            try {
                if (right.evaluate(map) != left.evaluate(map)) {
                    return false;
                }
            }
            catch (Exception ex) {

            }

        }
        return true;
    }
}
