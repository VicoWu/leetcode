package test;

import sun.misc.Launcher;

/**
 * Created by wuchang at 1/17/18
 */

public class TestClassLoader  {


    public static class MyClassLoader extends ClassLoader{

    }


   Launcher l;
    public static void main(String[] args) {
        System.out.println(new TestClassLoader().getClass().getClassLoader());
        System.out.println(new TestClassLoader().getClass().getClassLoader().getParent());
        System.out.println(new TestClassLoader().getClass().getClassLoader().getParent().getParent());
        System.out.println(new TestClassLoader().getClass().getClassLoader().getSystemClassLoader());
        System.out.println(javax.annotation.Generated.class.getClassLoader());
        ClassLoader cl = TestClassLoader.class.getClassLoader();
        System.out.println(TestClassLoader.class.getClass());
        System.out.println(TestClassLoader.class.getSuperclass());
        while(cl!=null){
            System.out.println(cl.toString());
            cl = cl.getParent();
        }
        ClassLoader cl1;
        //URLClassLoader ecl = new TestClassLoader().getClass().getClassLoader().getParent();

    }
}
