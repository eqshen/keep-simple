package com.eqshen.keepsimple.groovy;

import java.util.concurrent.TimeUnit;

public class GroovyMemLeak {

    public static void main(String[] args) throws Exception {
        //vm options:  -XX:PermSize=5M -XX:MaxPermSize=10M  -XX:MetaspaceSize=5M -XX:MaxMetaspaceSize=10M
        while (true){
            GroovyInvokeBasicUsage.invokeByClassLoader();
            TimeUnit.MILLISECONDS.sleep(20);
        }
    }
}
