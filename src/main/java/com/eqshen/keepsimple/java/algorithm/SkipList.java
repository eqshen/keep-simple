package com.eqshen.keepsimple.java.algorithm;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

/**
 * 跳表实现
 * @author eqshen
 * @description
 * @date 2021/2/26
 */
@Slf4j
public class SkipList<T> {

    public static void main(String[] args) {
        SkipList<Integer>list=new SkipList<Integer>();
        for(int i=1;i<4;i++)
        {
            list.add(i,1);
        }
        list.displayCurrentList();
        list.remove(4);
        list.remove(8);
        list.displayCurrentList();


        list.add(5,777);
        list.displayCurrentList();

        SkipNode search = list.search(5);
        System.out.println(search.getValue());
    }


    private int MAX_LEVEL = 32;
    private int currentMaxLevel = 1;
    private SkipNode<T> head;
    private Random random;

    public SkipList(){
        head = new SkipNode<>(true,0,null);
        random = new Random();
    }

    public SkipNode search(int key){
        SkipNode<T> temp = head;
        while (temp != null){

            if(!temp.head && temp.key == key){
                return temp;
            }else if(temp.right == null || temp.right.key > key){
                temp = temp.down;
            }else{
                temp = temp.right;
            }
        }

        return null;
    }

    public void remove(int key){
        SkipNode temp = head;
        while (temp != null){
            if(temp.right == null || temp.right.key > key){
                temp = temp.down;
            }else if(temp.right.key == key){
                temp.right = temp.right.right;
                temp = temp.down;
            }else{
                temp = temp.right;
            }
        }
    }

    public void add(int key,T val){

        //先查找是否已经存在该节点
        SkipNode search = search(key);
        if(search != null){
            search.value = val;
            return;
        }

        Stack<SkipNode<T>> stack = new Stack<>();
        SkipNode temp = head;
        while (temp != null){
            if(temp.right == null || temp.right.key > key){
                stack.add(temp);
                temp = temp.down;
            }else {
                temp = temp.right;
            }
        }

        int curLevel = 1;//从最低层开始处理
        SkipNode preDownNode = null;//下层新增的节点
        while (!stack.isEmpty()){
            SkipNode<T> leftNode = stack.pop();
            SkipNode newNode = new SkipNode(key, val);
            //竖直方向
            newNode.down = preDownNode;
            preDownNode = newNode;

            //水平方向
            if(leftNode.right == null){
                leftNode.right = newNode;
            }else{
                newNode.right = leftNode.right;
                leftNode.right = newNode;
            }

            //当前层处理完后，考虑是否要将当前新增节点 复制到上层 以增加索引
            if(curLevel > MAX_LEVEL || random.nextDouble() > 0.5){
                //层数已经超标 或者 运气不好
                break;
            }

            curLevel++;

            //特殊情况处理，如果已经跑到了顶层，需要更换head
            if(curLevel > currentMaxLevel){
                currentMaxLevel = curLevel;
                SkipNode newHead = new SkipNode(true,0,null);
                newHead.down = head;
                head = newHead;
                stack.add(newHead);
            }
        }

    }

    public void displayCurrentList(){
        log.info("==========list print start=================");
        log.info("total level:{}",currentMaxLevel);
        SkipNode teamNode= head;
        int index=1;
        SkipNode last=teamNode;
        while (last.down!=null){
            last=last.down;
        }
        while (teamNode!=null) {
            SkipNode enumNode=teamNode.right;
            SkipNode enumLast=last.right;
            System.out.printf("%-8s","head->");
            while (enumLast!=null&&enumNode!=null) {
                if(enumLast.key==enumNode.key)
                {
                    System.out.printf("%-5s",enumLast.key+"->");
                    enumLast=enumLast.right;
                    enumNode=enumNode.right;
                }
                else{
                    enumLast=enumLast.right;
                    System.out.printf("%-5s","");
                }

            }
            teamNode=teamNode.down;
            index++;
            System.out.println();
        }
        log.info("==========list print end====================");
    }


    @Data
    static class SkipNode<T>{
        private int key;
        private T value;
        private SkipNode right;
        private SkipNode down;
        private boolean head;

        public SkipNode(int key,T value){
            this.key = key;
            this.value = value;
            this.head = false;
        }

        public SkipNode(boolean isHead,int key,T value){
            this.key = key;
            this.value = value;
            this.head = isHead;
        }
    }
}
