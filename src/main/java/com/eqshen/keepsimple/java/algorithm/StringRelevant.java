package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Auther: eqshen
 * @Description 字符串相关
 * @Date: 2019/6/13 13:08
 */
public class StringRelevant extends BaseTest {
    public static void main(String[] args) {
        String str = "ababccbcd";
        String result1 = longestPalindrome(str);
        String result2 = longestPalindrome2(str);
        System.out.println(result1);
        System.out.println(result2);

        System.out.println(isMatch("aabce","a.bce"));
        System.out.println("============================");
        System.out.println(isPalindrome(0));
        System.out.println("============================");
        System.out.println(intToRoman(1994));
        System.out.println(romanToInt("MCMXCIV"));
        System.out.println("============================");
        System.out.println(letterCombinations("2354"));
    }

    /**
     * 5
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * 中心扩散法
     * @param s
     * @return
     * babad
     * acbba
     */
    public static String longestPalindrome(String s) {
        if(s == null || s.length() == 0)return s;
        int pos1 = 0;
        int pos2 = 0;
        for (int i = 0; i < s.length(); i++) {
            //单字符中心
            int len1 = calcLen(s,i,i);
            //双字符对称中心
            int len2 = calcLen(s,i,i+1);
            int len = Math.max(len1,len2);
            if(pos2 - pos1 < len){
                pos1 = i - (len - 1)/2;
                pos2 = i + len/2;
            }
        }
        return s.substring(pos1,pos2+1);
    }

    private static  int calcLen(String s,int left,int right){
        while(left >=0 && right<s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return right - left - 1;
    }


    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * 线性规划方法，基于暴力方法的一种改良
     * @param s
     * @return
     */
    public static String longestPalindrome2(String s){
        int len = s.length();
        if(len == 0) return s;
        //记录之前的状态
        boolean dp[][] = new boolean[len][len];
        int maxLength = 1;
        String longestPalindrome = s.substring(0,1); //substring 是左闭右开的

        //j往左跑，i往右跑
        for (int i = 0; i < len; i++) {
            for (int j = i; j >=0 ; j--) {

                //状态转移方程，条件
                // s[i] == s[j]
                //(i-j<=2 || dp[j+1][i-1]) 是 两种情况的化简
                //     即(s[i] == s[j] && i-j <= 2) ||  (s[i] == s[j] && i-j > 2 && dp[j+1][i-1])
                if(s.charAt(i) == s.charAt(j) && (i-j<=2 || dp[j+1][i-1])){
                    dp[j][i] = true;
                    if(i - j + 1 > maxLength){
                        maxLength = i - j + 1;
                        longestPalindrome = s.substring(j,i+1);
                    }

                }
            }
        }
        return longestPalindrome;
    }

    /**
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     * @param text
     * @param pattern
     * @return
     */
    public static boolean isMatch(String text, String pattern) {
        if (pattern.length() == 0) {
            return text.length() == 0;
        }
        boolean preMatch = text.length() != 0
                && (text.charAt(0) == pattern.charAt(0) || pattern.charAt(0) == '.');
        if (pattern.length() >= 2 && pattern.charAt(1) == '*') {
            return isMatch(text, pattern.substring(2)) ||
                    (preMatch && isMatch(text.substring(1), pattern));
        } else {
            return preMatch && isMatch(text.substring(1), pattern.substring(1));
        }
    }


    public static boolean isPalindrome(int x) {
        if(x < 0) return false;
        int oldX = x;
        int newX = 0;
        do{
            int r = x % 10;
            newX = newX*10 + r;
            x = x/10;
        }while(x != 0);

        return oldX == newX;
    }

    /**
     * 12.数字转罗马
     * @param num
     * @return
     */
    public static String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        while(num > 0){
            while(num<4&& num>0){
                sb.append("I");
                num--;
                continue;
            }

            if(num/1000 > 0){
                sb.append("M");
                num = num - 1000;
                continue;
            }
            if(num/900 >0){
                sb.append("CM");
                num = num - 900;
                continue;
            }
            if(num / 500 > 0){
                sb.append("D");
                num = num - 500;
                continue;
            }
            if(num/400 >0){
                sb.append("CD");
                num = num - 400;
                continue;
            }
            if(num/100 > 0){
                sb.append("C");
                num = num - 100;
                continue;
            }
            if(num/90 > 0){
                sb.append("XC");
                num = num - 90;
                continue;
            }
            if(num/50 > 0){
                sb.append("L");
                num = num - 50;
                continue;
            }
            if(num/40 > 0){
                sb.append("XL");
                num = num - 40;
                continue;
            }
            if(num/10 > 0){
                sb.append("X");
                num = num - 10;
                continue;
            }
            if(num/9 > 0){
                sb.append("IX");
                num = num - 9;
                continue;
            }
            if(num/5 > 0){
                sb.append("V");
                num = num - 5;
                continue;
            }
            if(num/4 > 0){
                sb.append("IV");
                num = num - 4;
                continue;
            }
        }
        return sb.toString();
    }

    /**
     * 13. 罗马数字转整数
     * @param s
     * @return
     */
    public static int romanToInt(String s) {
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            if(i < s.length() - 1 && map.containsKey(s.substring(i,i+2))){
                val+= map.get(s.substring(i,i+2));
                i++;
            }else{
                val+= map.get(s.substring(i,i+1));
            }
        }
        return val;
    }

    public static List<String> letterCombinations(String digits) {
        if(digits == null || digits.equals("")){
            return new ArrayList<>();
        }
        Map<String,String> map = new HashMap<>();
        map.put("2","a b c");
        map.put("3","d e f");
        map.put("4","g h i");
        map.put("5","j k l");
        map.put("6","m n o");
        map.put("7","p q r s");
        map.put("8","t u v");
        map.put("9","w x y z");

        List list = new ArrayList();

        list.addAll(Arrays.asList(map.get(""+ digits.charAt(0)).split(" ")));
        for (int i = 1; i < digits.length(); i++) {
            list = letterCombinationHelp(list,map.get("" + digits.charAt(i)).split(" "));
        }
        return list;
    }

    private static List<String> letterCombinationHelp(List<String> oldList,String[] strs){
        List<String> newList = new ArrayList<>();
        for (String s : oldList) {
            for (String str : strs) {
                newList.add(s + str);
            }
        }
        return newList;
    }


    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     *
     * 有效字符串需满足：
     *
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/valid-parentheses
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @return
     */
    @Test
    public void isValid() {
        Map<Character,Character> map = new HashMap<>();
        map.put(']', '[');
        map.put('}', '{');
        map.put(')', '(');
        Stack<Character> stack = new Stack<>();


        String s= "]";
        boolean valid = true;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch == ' ') continue;
            if(!map.containsKey(ch)){
                stack.push(ch);
            }else{
                if(stack.empty()){
                    valid = false;
                    break;
                }
                char tmp = stack.pop();
                if(map.get(ch) != tmp){
                    valid = false;
                    break;
                }
            }
        }
        System.out.println(valid&&stack.empty());
    }

    /**
     *给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
     *
     * 例如，给出 n = 3，生成结果为：
     *
     * [
     *   "((()))",
     *   "(()())",
     *   "(())()",
     *   "()(())",
     *   "()()()"
     * ]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/generate-parentheses
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * 回溯算法基本思想：能进则进，进不了则换，换不了则退
     *
     * 括号生成算法类似于二叉树先序遍历
     * 可用递归生成问题解空间树
     * 再用剪枝函数来对解空间树进行剪枝
     * 括号生成:
     *
     * 进入左子树条件： (个数 小于 n
     * 进入右子树条件： ）个数 小于 （ 个数
     *
     * 作者：hikes
     * 链接：https://leetcode-cn.com/problems/two-sum/solution/hui-su-suan-fa-by-hikes/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    @Test
    public void testGenerateParenthesis(){
        System.out.println(generateParenthesis(3));
    }



    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        backtrack(ans, "", 0,0,n);

        return ans;
    }

    public  void backtrack(List<String> ans, String cur, int left, int right, int max){
        if(cur.length() == max * 2){
            ans.add(cur);
            System.out.println("====>" + cur);
            return;
        }
        System.out.println(cur);
        if(left < max){
            backtrack(ans, cur+"(",  left + 1, right, max);
        }

        if(right < left){
            backtrack(ans, cur +")", left, right+1, max);
        }
    }

    @Test
    public void testFindSubString(){
//        "barfoothefoobarman"
//                ["foo","bar"]
        String s = "barfoothefoobarman";
        String[] words = {"foo","bar"};
        System.out.println(findSubstring(s,words));
    }

    /**
     *给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
     *
     * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/substring-with-concatenation-of-all-words
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> resultList = new ArrayList<>();
        if(words.length == 0){
            return  resultList;
        }
        int wordNum = words.length;
        int wordLen = words[0].length();

        Map<String,Integer> wordCountMap = new HashMap<>();
        for (String word : words) {
            int value = wordCountMap.getOrDefault(word,0);
            wordCountMap.put(word,1 + value);
        }

        for (int i = 0; i <s.length() - wordLen*wordNum + 1 ; i++) {
            HashMap<String,Integer> wordOccurMap = new HashMap<>();
            int curNum = 0;
            while (curNum < wordNum){
                String word = s.substring(i + wordLen* curNum,i + wordLen*(curNum + 1));
                if(!wordCountMap.containsKey(word)){
                    break;
                }
                wordOccurMap.put(word,1 + wordOccurMap.getOrDefault(word,0));
                if(wordOccurMap.getOrDefault(word,0) > wordCountMap.get(word)){
                    break;
                }
                curNum++;
            }
            if(curNum == wordNum){
                resultList.add(i);
            }
        }
        return resultList;
    }

}
