package blog.brucefeng.info.mvel;

import blog.brucefeng.info.bean.Foo;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RunMvel {
    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setName("test");
        Map context = new HashMap();
        String expression = "foo.name == 'test'";
        VariableResolverFactory functionFactory = new MapVariableResolverFactory(context);
        context.put("foo",foo);
        Boolean result = (Boolean) MVEL.eval(expression,functionFactory);
        System.out.println(result);


        Serializable str = MVEL.compileExpression(
                "( ( checkNullValue(a) + checkNullValue(b) + checkNullValue(c) ) > 2 ) ? d=2 : d=3");

//        MVEL.executeExpression(str, map, functionFactory);
//        System.out.println(map);
//        System.out.println(map.get("d"));

//        expression = "foobar >99";
//        Map vars = new HashMap();
//        vars.put("foobar", 100);
//        result = (Boolean) MVEL.eval(expression, vars);
//        System.out.println(expression + ",result = " + result);
//
//        Serializable compiled = MVEL.compileExpression(expression);
//
//        result = (Boolean) MVEL.executeExpression(compiled, vars);
//        System.out.println("executeExpression:" + expression + ",result = " + result);

    }
}
