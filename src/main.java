import java.util.*;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args) {
//        Val val = new Val(false);
//        Val val2 = new Val(true);
//        Var var = new Var("x");
//        Var var2 = new Var("y");
//        Xor and = new Xor(val2, var);
//        And and2 = new And(and, var2);
//        And and3 = new And(and2, and);
//        And and4 = new And(val,and3);
//        Map<String, Boolean> m = new TreeMap<String, Boolean>();
//        m.put("y",true);
//        m.put("x",true);
//        try {
//            System.out.println(and);
//            List<String> vars = and4.getVariables();
//            for (String v : vars) {
//                System.out.println(v);
//            }
//        }
//        catch (Exception e) {
//
//        }
        List<String> listOne = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "f"));
        List<String> listTwo = new ArrayList<>(Arrays.asList("a", "b", "c", "f", "d")).stream().sorted().collect(Collectors.toList());
        Boolean isEqual = listOne.equals(listTwo);      //true
        System.out.println(isEqual);

    }
}
