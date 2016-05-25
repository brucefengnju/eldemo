package blog.brucefeng.info.performance;

import ognl.Ognl;
import ognl.OgnlException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RunPerform {

    public static void main(String[] args) {
        try{
            runJava();
            runOgnl();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void runJava(int xmax,int ymax,int zmax){
        Date start = new Date();
        Integer result = 0;
        for(int xval = 0; xval<xmax; xval++){
            for(int yval=0;yval<ymax;yval++){
                for(int zval = 0;zval<=zmax;zval++){
                    result += xval+ yval*2 -zval;
                }
            }
        }
        Date end = new Date();
        System.out.println("time is : "+ (end.getTime()-start.getTime()) + ",result is "+result);

    }
    public static void  runOgnl(int xmax,int ymax,int zmax) throws OgnlException {
        String expression = "x + y*2 - z";
        Map<String, Object> context = new HashMap<String, Object>();
        Integer result = 0;
        Date start = new Date();
        for(int xval = 0; xval<xmax; xval++){
            for(int yval=0;yval<ymax;yval++){
                for(int zval = 0;zval<=zmax;zval++){
                    context.put("x",xval);
                    context.put("y",yval);
                    context.put("z",zval);
                    Integer cal = (Integer) Ognl.getValue(expression,context);
                    result += cal;
                }
            }
        }
        Date end = new Date();
        System.out.println("time is : "+ (end.getTime()-start.getTime()) + ",result is "+result);
    }

    public static void runMvel(int xmax,int ymax,int zmax){

    }

    public static void runSpel(int xmax,int ymax,int zmax){

    }

    public static void runGroovy(int xmax,int ymax,int zmax){

    }
}
