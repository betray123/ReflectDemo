package com.zk;

import com.zk.demo.Foo;
import com.zk.reflectionSimulateSpringPrinciple.ApplacationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws Exception
    {
        ApplacationContext app = new ApplacationContext("applicationContext.xml");
        Foo foo = (Foo) app.getBean("foo");
        Foo foo1 = app.getBean("foo",Foo.class);
        System.out.println(foo);
        System.out.println(foo1);
    }
}
