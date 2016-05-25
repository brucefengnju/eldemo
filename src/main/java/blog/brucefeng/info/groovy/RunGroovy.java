package blog.brucefeng.info.groovy;

import blog.brucefeng.info.bean.Foo;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.IOException;

public class RunGroovy {

    public static void main(String[] args) throws IOException {
        Foo foo = new Foo();
        foo.setName("test");
        Binding binding = new Binding();
        binding.setVariable("foo", foo);
        GroovyShell shell = new GroovyShell(binding);
        String expression = "foo.name=='test'";
        Object result = shell.evaluate(expression);
        assert result.equals(Boolean.TRUE);
        System.out.println(result);

        GroovyClassLoader loader = new GroovyClassLoader();
        Class groovyClass = null;
        try {
            groovyClass = loader.parseClass(new File("/Users/brucechen/program/hack/eldemo/src/main/java/blog/brucefeng/info/groovy/FooCompare.groovy"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GroovyObject groovyObject = null;
        try {
            groovyObject = (GroovyObject) groovyClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        result = groovyObject.invokeMethod("comapre", "test");
        assert result.equals(Boolean.TRUE);
        System.out.println(result);


        String[] paths = {"/Users/brucechen/program/hack/eldemo/src/main/java/blog/brucefeng/info/groovy/"};
        GroovyScriptEngine gse = new GroovyScriptEngine(paths);
        binding = new Binding();

        binding.setVariable("foo",foo);

        try {
            result = gse.run("FooScript.groovy", binding);
        } catch (ResourceException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        assert result.equals(Boolean.TRUE);
        System.out.println(result);

        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine1 = factory.getEngineByName("groovy");
        engine1.put("foo",foo);
        try {
           result =  engine1.eval(expression);
        } catch (javax.script.ScriptException e) {
            e.printStackTrace();
        }

        System.out.println(result);


    }
}
