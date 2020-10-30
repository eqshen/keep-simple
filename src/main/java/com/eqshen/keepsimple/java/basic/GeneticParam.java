package com.eqshen.keepsimple.java.basic;

import java.util.function.Function;

/**
 * 泛型使用、复习
 */
public class GeneticParam {
    public static void main(String[] args) {
        System.out.println(new GeneticParam().add(1, 2));
    }

    public <T extends Number> T add(T a , T b){
        return b;
    }
}
