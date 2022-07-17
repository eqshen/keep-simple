package com.eqshen.keepsimple.groovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.concurrent.TimeUnit;

public class GroovyMemLeak {

    public static void main(String[] args) throws Exception {
        //vm options:  -XX:PermSize=5M -XX:MaxPermSize=10M  -XX:MetaspaceSize=5M -XX:MaxMetaspaceSize=10M
//        while (true){
//            GroovyInvokeBasicUsage.invokeByClassLoader();
//            TimeUnit.MILLISECONDS.sleep(20);
//        }

        GroovyShell shell = new GroovyShell();
        final Script script = shell.parse("eval(1+2)");
        final Object result = script.run();
        System.out.println("invokeByGroovyShell1:" + result);
    }
}
