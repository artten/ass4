import java.util.Map;
import java.util.TreeMap;

public class main {
    public static void main(String[] args) {
        Val val = new Val(false);
        Var var = new Var("x");
        And and = new And(val, var);
        Map<String, Boolean> m = new TreeMap<String, Boolean>();
        m.put("y",true);
        try {
            System.out.println(and.evaluate(m));
        }
        catch (Exception e) {

        }

    }
}
