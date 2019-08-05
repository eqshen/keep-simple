package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LinkedListRelevant extends BaseTest {

    @Test
    public void testMergeKlists(){

        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(5);
        list1.next.next = new ListNode(7);

        ListNode list2 = new ListNode(2);
        list2.next = new ListNode(8);

        ListNode[] lists = {list1,list2};

        ListNode resultList = mergeKListsPlus(lists);

        while (resultList != null){
            System.out.println(resultList.val);
            resultList = resultList.next;
        }


    }

    /**
     * 23。https://leetcode-cn.com/problems/merge-k-sorted-lists/
     * 合并 k 个排序链表，返回合并后的排序链表。
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode[] heads = new ListNode[lists.length];
        for (int i = 0; i < lists.length; i++) {
            heads[i] = lists[i];
        }

        ListNode movePoint = new ListNode(Integer.MIN_VALUE);
        ListNode result = movePoint;

        int index = -1;
        do{
            index = getMinIndex(heads);
            if(index == -1){
                break;
            }
            movePoint.next = heads[index];
            movePoint = movePoint.next;
            heads[index] = heads[index].next;

        }while (index > -1);
        return result.next;
    }

    /**
     * 合并k个有序链表-改进版
     * @param lists
     * @return
     */
    public ListNode mergeKListsPlus(ListNode[] lists){
        if(lists.length == 0) return null;
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(lists.length, Comparator.comparingInt(a -> a.val));
        ListNode resultHead = new ListNode(-1);
        ListNode curNode = resultHead;

        //入队每个链表第一个节点
        for (ListNode listNode : lists) {
            if(listNode != null){
                priorityQueue.add(listNode);
            }
        }

        while (!priorityQueue.isEmpty()){
            ListNode tmpNode = priorityQueue.poll();
            curNode.next = tmpNode;
            curNode = curNode.next;
            //往后移动一个位置后继续入队
            if(curNode.next != null){
                priorityQueue.add(curNode.next);
            }
        }
        return resultHead.next;
    }

    private int getMinIndex(ListNode[] lists){
        if(lists.length == 0){
            return -1;
        }
        int minNode = -1;
        for (int i = 0;i < lists.length;i++) {
            if(lists[i] == null) continue;
            if(minNode == -1) minNode = i;
            if(lists[minNode] != null && lists[i].val < lists[minNode].val){
                minNode = i;
            }
        }
        return minNode;
    }



    @Test
    public void testSwapPairs(){
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        ListNode head = swapPairs(node1);

        while (head != null){
            System.out.print(head.val);
            System.out.print(" ---> ");
            head = head.next;
        }
    }

    public ListNode swapPairs(ListNode head) {
        if(head==null || head.next ==null) return head;
        ListNode realHead = head.next==null?head:head.next;
        ListNode pre = null;
        while(head!=null && head.next != null ){
            ListNode first = head;
            ListNode second = head.next;
            head = second.next;
            if(pre != null){
                pre.next = first.next;
            }

            first.next = second.next;
            second.next = first;
            pre = first;


        }
        return realHead;
    }
}



class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
}