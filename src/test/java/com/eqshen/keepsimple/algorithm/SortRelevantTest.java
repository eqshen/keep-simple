package com.eqshen.keepsimple.algorithm;

import com.eqshen.keepsimple.BaseTest;
import com.eqshen.keepsimple.java.algorithm.SortRelevant;
import org.junit.Test;

/**
 * @Auther: eqshen
 * @Description
 * @Date: 2020/2/28 23:31
 */
public class SortRelevantTest extends BaseTest {
    @Test
    public void testFrequencySort(){
        String str = "tree";
        SortRelevant sortRelevant = new SortRelevant();
        String result = sortRelevant.frequencySort(str);
        System.out.println(result);
    }
}
