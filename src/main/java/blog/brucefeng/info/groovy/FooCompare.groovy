package blog.brucefeng.info.groovy

import blog.brucefeng.info.bean.Foo

class FooCompare{
    boolean comapre(String toCompare){
        Foo foo = new Foo();
        foo.name = "test";
        return foo.name == toCompare;
    }
}
