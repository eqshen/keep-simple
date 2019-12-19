package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import com.eqshen.keepsimple.java.algorithm.dto.MinStack;
import org.junit.Test;

public class StackRelevant extends BaseTest {
    /**
     * 155设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
     *
     * push(x) -- 将元素 x 推入栈中。
     * pop() -- 删除栈顶的元素。
     * top() -- 获取栈顶元素。
     * getMin() -- 检索栈中的最小元素。
     *
     */
    @Test
    public void minStackTest(){
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());//-3
        minStack.pop();
        System.out.println(minStack.top());// 返回 0.
        System.out.println(minStack.getMin());//-2
    }
}
