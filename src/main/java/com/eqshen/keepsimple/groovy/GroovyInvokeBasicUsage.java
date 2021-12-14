package com.eqshen.keepsimple.groovy;

import groovy.lang.*;
import groovy.util.GroovyScriptEngine;
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Groovy调用，几种常见的方式
 */
public class GroovyInvokeBasicUsage {

    public static void main(String[] args) throws Exception {
        invokeByGroovyShell();
        System.out.println("=====================");
        invokeByScriptEngine();
        System.out.println("=====================");
        invokeByClassLoader();
    }

    public static void invokeByGroovyShell(){
        Object[] args = {"tom"};
        String scriptStr = "def sayHello(String username) {return \"hello \" + username}";

        //方式1
        GroovyShell shell = new GroovyShell();
        final Script script = shell.parse(scriptStr);
        final Object result = script.invokeMethod("sayHello", args);
        System.out.println("invokeByGroovyShell1:" + result);

        //方式2
        Binding binding = new Binding();
        binding.setVariable("username","hendy");
        shell = new GroovyShell(binding);
        shell.parse("println \"hello $username\";").run();

        //其他，Binding,Script,Shell 灵活组合，使用方式很多

    }

    public static void invokeByClassLoader() throws Exception {
        Object[] args = {"tom"};
        String scriptStr = "def sayHello(String username) {return \"hello \" + username}";

        GroovyClassLoader classLoader = new GroovyClassLoader();
        final Class aClass = classLoader.parseClass(scriptStr);
        System.out.println(aClass.getClassLoader()+" ====="+ aClass);

        final GroovyObject object = (GroovyObject) aClass.getDeclaredConstructor().newInstance();
        final Object result = object.invokeMethod("sayHello", args);
        System.out.println("invokeByClassLoader: " + result);
    }

    private static GroovyScriptEngineFactory engineFactory = new GroovyScriptEngineFactory();

    public static void invokeByScriptEngine() throws Exception {
        Object[] args = {"tom"};

        String scriptStr = "def sayHello(String username) {return \"hello \" + username}";

        final ScriptEngine scriptEngine = engineFactory.getScriptEngine();
        scriptEngine.eval(scriptStr);
        Invocable invocable = (Invocable) scriptEngine;
        final Object result = invocable.invokeFunction("sayHello", args);
        System.out.println("invokeByScriptEngine: " + result);

    }
}
