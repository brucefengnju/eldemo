package blog.brucefeng.info.performance;

import blog.brucefeng.info.spel.RunSpel;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import ognl.Ognl;
import ognl.OgnlException;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RunPerform {

    public static void main(String[] args) {
        try {
            int xmax = 100,ymax = 100,zmax= 10;
            runJava(xmax, ymax, zmax);
            runOgnl(xmax, ymax, zmax);
            runMvel(xmax, ymax, zmax);
            runSpel(xmax, ymax, zmax);
            runGroovyClass(xmax, ymax, zmax);
//            runGroovy(xmax, ymax, zmax);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void runJava(int xmax, int ymax, int zmax) {
        Date start = new Date();
        Integer result = 0;
        for (int xval = 0; xval < xmax; xval++) {
            for (int yval = 0; yval < ymax; yval++) {
                for (int zval = 0; zval <= zmax; zval++) {
                    result += xval + yval * 2 - zval;
                }
            }
        }
        Date end = new Date();
        System.out.println("time is : " + (end.getTime() - start.getTime()) + ",result is " + result);

    }

    public static void runOgnl(int xmax, int ymax, int zmax) throws OgnlException {
        String expression = "x + y*2 - z";
        Map<String, Object> context = new HashMap<String, Object>();
        Integer result = 0;
        Date start = new Date();
        for (int xval = 0; xval < xmax; xval++) {
            for (int yval = 0; yval < ymax; yval++) {
                for (int zval = 0; zval <= zmax; zval++) {
                    context.put("x", xval);
                    context.put("y", yval);
                    context.put("z", zval);
                    Integer cal = (Integer) Ognl.getValue(expression, context);
                    result += cal;
                }
            }
        }
        Date end = new Date();
        System.out.println("Ognl:time is : " + (end.getTime() - start.getTime()) + ",result is " + result);
    }

    public static void runMvel(int xmax, int ymax, int zmax) {
        Map context = new HashMap();
        String expression = "x + y*2 - z";
        Serializable compileExpression = MVEL.compileExpression(expression);
        Integer result = 0;
        Date start = new Date();
        for (int xval = 0; xval < xmax; xval++) {
            for (int yval = 0; yval < ymax; yval++) {
                for (int zval = 0; zval <= zmax; zval++) {
                    context.put("x", xval);
                    context.put("y", yval);
                    context.put("z", zval);
                    VariableResolverFactory functionFactory = new MapVariableResolverFactory(context);
                    Integer cal = (Integer) MVEL.executeExpression(compileExpression, context, functionFactory);
                    result += cal;
                }
            }
        }
        Date end = new Date();
        System.out.println("MVEL:time is : " + (end.getTime() - start.getTime()) + ",result is " + result);

    }

    public static void runSpel(int xmax, int ymax, int zmax) {
        SpelParserConfiguration config;
        ExpressionParser parser;
        config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, RunSpel.class.getClassLoader());
        parser = new SpelExpressionParser(config);
        StandardEvaluationContext context = new StandardEvaluationContext();
        Integer result = 0;
        String expressionStr = "#x + #y*2 - #z";
        Date start = new Date();
        for (Integer xval = 0; xval < xmax; xval++) {
            for (Integer yval = 0; yval < ymax; yval++) {
                for (Integer zval = 0; zval <= zmax; zval++) {
                    context.setVariable("x", xval);
                    context.setVariable("y", yval);
                    context.setVariable("z", zval);
                    Expression expression = parser.parseExpression(expressionStr);
                    Integer cal = expression.getValue(context, Integer.class);
//                    System.out.println("x:"+xval+",y:"+yval+",z"+zval+ ",cal:"+cal);
                    result += cal;
                }
            }
        }
        Date end = new Date();
        System.out.println("SpEL:time is : " + (end.getTime() - start.getTime()) + ",result is " + result);

    }

    public static void runGroovy(int xmax, int ymax, int zmax) {
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        String expression = "x + y*2 - z";
        Integer result = 0;
        Date start = new Date();
        for (int xval = 0; xval < xmax; xval++) {
            for (int yval = 0; yval < ymax; yval++) {
                for (int zval = 0; zval <= zmax; zval++) {
                    binding.setVariable("x", xval);
                    binding.setVariable("y", yval);
                    binding.setVariable("z", zval);
                    Integer cal = (Integer) shell.evaluate(expression);
                    result += cal;
                }
            }
        }
        Date end = new Date();
        System.out.println("Groovy:time is : " + (end.getTime() - start.getTime()) + ",result is " + result);
    }

    public static void runGroovyClass(int xmax, int ymax, int zmax) {
        GroovyClassLoader loader = new GroovyClassLoader();
        Class groovyClass = null;
        try {
            groovyClass = loader.parseClass(new File(
                    "/Users/brucechen/program/hack/eldemo/src/main/java/blog/brucefeng/info/performance/GroovyCal.groovy"));
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
        Integer result = 0;
        Date start = new Date();
        for (int xval = 0; xval < xmax; xval++) {
            for (int yval = 0; yval < ymax; yval++) {
                for (int zval = 0; zval <= zmax; zval++) {
                    Object[] args = {xval,yval,zval};
                    Integer cal = (Integer) groovyObject.invokeMethod("cal", args);
                    result += cal;
                }
            }
        }
        Date end = new Date();
        System.out.println("Groovy Class:time is : " + (end.getTime() - start.getTime()) + ",result is " + result);

    }
}
