package blog.brucefeng.info.ognl;

import blog.brucefeng.info.bean.Foo;
import ognl.Ognl;
import ognl.OgnlException;

import java.util.HashMap;
import java.util.Map;

public class RunOgnl {

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setName("test");
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("foo",foo);
        String expression = "foo.name == 'test'";
        try {
            Boolean result = (Boolean) Ognl.getValue(expression,context);
            System.out.println(result);
        } catch (OgnlException e) {
            e.printStackTrace();
        }

    }
}
