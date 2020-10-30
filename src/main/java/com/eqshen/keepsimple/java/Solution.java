package com.eqshen.keepsimple.java;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Solution {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("1","2","3","4","5","6"));
        List<String> tmpList = list.subList(0, 2);
        tmpList.forEach(System.out::print);
        System.out.println();
        list.forEach(System.out::print);
        System.out.println();
        System.out.println("=======");
        tmpList.clear();
        list.forEach(System.out::print);
        System.out.println();

    }

    public static boolean equationsPossible(String[] equations) {

        //
        Map<String,Set<String>> setMap = new HashMap<>();


        List<String> uneqList = new ArrayList<>();
        for(String equ : equations){
            boolean equal = false;
            String ele[] = equ.split("!=");
            if(ele.length<2){
                ele = equ.split("==");
                equal = true;
            }
            String leftVar = ele[0];
            String rightVar = ele[1];


            if(equal){
                Set<String> leftSet = setMap.get(leftVar);
                Set<String> rightSet = setMap.get(rightVar);
                Set<String> finalSet = null;
                if(leftSet != null && rightSet != null && leftSet != rightSet){
                    //merge
                    leftSet.addAll(rightSet);
                    finalSet = leftSet;
                    for (String s : leftSet) {
                        setMap.put(s,finalSet);
                    }
                    for (String s : rightSet) {
                        setMap.put(s,finalSet);
                    }
                }else if(leftSet == null){
                    finalSet = rightSet;
                }else{
                    finalSet = leftSet;
                }
                if(finalSet == null){
                    finalSet = new HashSet<>();
                }

                finalSet.add(leftVar);
                finalSet.add(rightVar);
                setMap.put(leftVar,finalSet);
                setMap.put(rightVar,finalSet);


            }else{
                uneqList.add(equ);
            }
        }
        for (String s : uneqList) {
            String ele[] = s.split("!=");
            String leftVar = ele[0];
            String rightVar = ele[1];
            Set<String> leftSet = setMap.get(leftVar);
            Set<String> rightSet = setMap.get(rightVar);
            if(leftVar.equals(rightVar)){
                return false;
            }
            if(leftSet == rightSet && leftSet!= null){
                return false;
            }
        }
        return true;
    }
}
