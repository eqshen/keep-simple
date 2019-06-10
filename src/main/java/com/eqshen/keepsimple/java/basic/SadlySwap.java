package com.eqshen.keepsimple.java.basic;

import java.lang.reflect.Field;

/**
 * @Auther: eqshen
 * @Description 忧伤地交换
 * @Date: 2019/4/12 13:04
 */
public class SadlySwap {

    public static void main(String[] args) throws Exception {
        Integer a = 3;
        Integer b = 5;
        System.out.println("before swap: a="+ a + ",b=" + b);//before swap: a=3,b=5
        swap(a,b);
        System.out.println("after swap: a="+ a + ",b=" + b);//after swap: a=5,b=5
    }

    public static void swap(Integer a,Integer b) throws Exception {
        Field valueField = Integer.class.getDeclaredField("value");
        valueField.setAccessible(true);
        int tmpA = a.intValue();//3
        int tmpB = b.intValue();//5
        System.out.println(Integer.valueOf(3)+"======" + Integer.valueOf(5));
        valueField.set(a,new Integer(tmpB));
        
        System.out.println(Integer.valueOf(3)+"======" + Integer.valueOf(5));
        valueField.set(b,new Integer(tmpA));
        System.out.println(Integer.valueOf(3)+"======" + Integer.valueOf(5));
    }


    public static void swap2(Integer a,Integer b) throws Exception {
        Field valueField = a.getClass().getDeclaredField("value");
        valueField.setAccessible(true);
        int tmpA = a.intValue();//3
        int tmpB = b.intValue();//5
        valueField.set(a,new Integer(tmpB));//命中IntegerCache,获取cache(5)即5,并更新缓存cache(3)=5
        System.out.println(3 + "========>" + Integer.valueOf(3));//此处输出5
        System.out.println(5 + "========>" + Integer.valueOf(5));
        valueField.set(b,new Integer(tmpA));//获取cache(3),命中缓存,即 5
    }

    public static void testIntegerCache(){
        System.out.println();
        //round1
        Integer a = 5;
        Integer b = 5;
        System.out.println(b == a);

        //round2
        Integer d = 500;
        Integer e = 500;
        System.out.println(e == d);
    }
}
