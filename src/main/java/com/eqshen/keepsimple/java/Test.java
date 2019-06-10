package com.eqshen.keepsimple.java;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: eqshen
 * @Description TODO
 * @Date: 2019/5/23 15:11
 */
public class Test {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String str = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(str);
    }
}
