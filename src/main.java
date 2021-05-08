import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class main {
    public static void main(String[] args) {
        Val val = new Val(false);
        Val val2 = new Val(true);
        Var var = new Var("x");
        Var var2 = new Var("y");
        And and = new And(val2, var);
        And and2 = new And(and, var2);
        And and3 = new And(and2, and);
        And and4 = new And(val,and3);
        Map<String, Boolean> m = new TreeMap<String, Boolean>();
        m.put("y",true);
        m.put("x",true);
        try {
            System.out.println(and.evaluate(m));
            List<String> vars = and4.getVariables();
            for (String v : vars) {
                System.out.println(v);
            }
        }
        catch (Exception e) {

        }

    }
}
