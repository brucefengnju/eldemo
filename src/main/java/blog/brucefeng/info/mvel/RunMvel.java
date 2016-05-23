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


        Serializable compileExpression = MVEL.compileExpression(expression);
        result = (Boolean) MVEL.executeExpression(compileExpression, context, functionFactory);
        System.out.print(result);

    }
}
