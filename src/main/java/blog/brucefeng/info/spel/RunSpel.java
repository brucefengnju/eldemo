package blog.brucefeng.info.spel;

import blog.brucefeng.info.bean.Foo;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class RunSpel {
    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setName("test");
        SpelParserConfiguration config = new SpelParserConfiguration(true,true);
        ExpressionParser parser = new SpelExpressionParser(config);
        String expressionStr = "#foo.name == 'test'";
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("foo",foo);
        Expression expression = parser.parseExpression(expressionStr);
        Boolean result = expression.getValue(context,Boolean.class);
        System.out.println(result);


        config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, RunSpel.class.getClassLoader());
        parser = new SpelExpressionParser(config);
        context = new StandardEvaluationContext();
        context.setVariable("foo",foo);
        expression = parser.parseExpression(expressionStr);
        result = expression.getValue(context,Boolean.class);
        System.out.println(result);
    }
}
