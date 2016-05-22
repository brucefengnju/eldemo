package blog.brucefeng.info.mvel;

import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;

import javax.script.ScriptEngineManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RunMvel {
    public static void main(String[] args) {
//        Foo foo = new Foo();
//        Foo testFoo = (Foo)MVEL.eval("name='test'",foo);
//        System.out.print(testFoo);

        Map map = new HashMap();

        VariableResolverFactory functionFactory = new MapVariableResolverFactory(map);
        MVEL.eval("checkNullValue = def (x) { x == null ? 0 : x };", functionFactory);

        map.put("a", null);
        map.put("b", 1);
        map.put("c", 1);

        Serializable str = MVEL.compileExpression("( ( checkNullValue(a) + checkNullValue(b) + checkNullValue(c) ) > 2 ) ? d=2 : d=3");

        MVEL.executeExpression(str, map, functionFactory);
        System.out.println(map);
        System.out.println(map.get("d"));

        String  expression= "foobar >99";
        Map vars = new HashMap();
        vars.put("foobar",100);
        Boolean result = (Boolean)MVEL.eval(expression,vars);
        System.out.println(expression + ",result = " + result);

        Serializable compiled =MVEL.compileExpression(expression);

        result = (Boolean)MVEL.executeExpression(compiled, vars);
        System.out.println("executeExpression:"+expression + ",result = " + result);

    }
}
