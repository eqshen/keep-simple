package com.eqshen.keepsimple.algorithm;

import com.eqshen.keepsimple.BaseTest;
import com.eqshen.keepsimple.java.algorithm.SortRelevant;
import org.junit.Test;

import java.util.Arrays;

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

    @Test
    public void testSortColors(){
        int[] nums = {1,2,0};
        SortRelevant sortRelevant = new SortRelevant();
        sortRelevant.sortColors(nums);
        Arrays.stream(nums).forEach(System.out::println);
    }
}
