package com.eqshen.keepsimple;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class MyStack {

    private Queue<Integer> queue1;
    private Queue<Integer> queue2;



    /** Initialize your data structure here. */
    public MyStack() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        queue1.offer(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        if(queue2.isEmpty()){
            int size = queue1.size();
            for (int i = 0; i < size -1; i++) {
                queue2.offer(queue1.remove());
            }
        }
        int res = queue1.remove();
        Queue tmp = queue1;
        queue1 = queue2;
        queue2 = tmp;
        return res;
    }

    /** Get the top element. */
    public int top() {
        if(queue2.isEmpty()){
            int size = queue1.size();
            for (int i = 0; i < size - 1; i++) {
                queue2.offer(queue1.remove());
            }
        }
        int res = queue1.poll();
        queue2.offer(res);
        Queue tmp = queue1;
        queue1 = queue2;
        queue2 = tmp;
        return res;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue1.isEmpty() && queue2.isEmpty();
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        System.out.println(myStack.top());
        System.out.println(myStack.empty());
    }
}