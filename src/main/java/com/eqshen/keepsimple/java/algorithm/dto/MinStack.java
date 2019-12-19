package com.eqshen.keepsimple.java.algorithm.dto;

import java.util.ArrayList;
import java.util.List;

public class MinStack {
    private List<Integer> stack;
    private List<Integer> mins;
    /** initialize your data structure here. */
    public MinStack() {
        stack = new ArrayList<>();
        mins = new ArrayList<>();
    }

    public void push(int x) {
        stack.add(x);
        if(mins.size() == 0){
            mins.add(x);
        }else{
            int min = mins.get(mins.size() - 1);
            if(x <= min){
                mins.add(x);
            }
        }

    }

    public void pop() {
        emptyCheck();
        int top = top();
        int minTop = mins.get(mins.size() -1);
        if(top <= minTop){
            mins.remove(mins.size()-1);
        }
        stack.remove(stack.size() -1);
    }

    public int top() {
        emptyCheck();
        return stack.get(stack.size()-1);
    }

    public int getMin() {
        emptyCheck();
        return mins.get(mins.size() -1);
    }

    private void emptyCheck(){
        if(stack.size() == 0){
            throw new RuntimeException("stack is empty");
        }
        if(mins.size() == 0){
            throw new RuntimeException("min stack is empty");
        }
    }
}
