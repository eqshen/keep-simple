package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import org.junit.Test;

/**
 * @Auther: eqshen
 * @Description
 * @Date: 2019/8/7 13:23
 */
public class BasicRelevant extends BaseTest {
    /**
     * 50
     * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if(n < 0){
            x = 1/x;
            n *=-1;
        }
        return  n%2 == 0? myPowHelper(x*x,n/2,1):myPowHelper(x*x,n/2,x);
    }

    private double myPowHelper(double x,int n,double remain){
        if(n == 0){
            return remain;
        }else if(n == 1){
            return x * remain;
        }
        return n%2 == 0? myPowHelper(x*x,n/2,remain):myPowHelper(x*x,n/2,x*remain);
    }

    @Test
    public void testMyPow(){
        System.out.println(myPow(3,4));
    }

}
