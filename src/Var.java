/**
 * @author 319339198
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * and expression
 */
public class Var implements Expression{
    private String variable;

    /**
     * constructor.
     * @param var - the val new value
     */
    Var(String var) {
        this.variable = var;
    }

    /**
     * constructor.
     */
    Var() {
        this.variable = null;
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
        System.out.println("asdasdasd");
         if(assignment.get(this.variable) != null) {
             return assignment.get(this.variable);
         }
         throw new RuntimeException(new NullPointerException());
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return
     * @throws Exception
     */
    public Boolean evaluate() throws Exception {
        throw new RuntimeException();
    }

    /**
     * Returns a list of the variables in the expression.
     * @return
     */
    public List<String> getVariables() {
        List<String> list = new LinkedList<String>();
        return list;
    }

    /**
     * Returns a nice string representation of the expression.
     * @return expression as string
     */
    @Override
    public String toString() {
        // super();
        return this.variable;
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
        return this;
    }
}
