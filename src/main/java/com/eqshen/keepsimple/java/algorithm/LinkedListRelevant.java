package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
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

    /**
     * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
     *
     * k 是一个正整数，它的值小于或等于链表的长度。
     *
     * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/reverse-nodes-in-k-group
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {

        ListNode sourceHead = head;
        int modCount = 0;

        ListNode sliceHead = head;
        ListNode sliceTail = head;
        ListNode lastTail = head;

        ListNode result = null;
        while (head != null){
            modCount++;
            if(modCount %k == 0){
                //断链
                ListNode tmpHead = head;
                head = head.next;
                tmpHead.next = null;

                //翻转前面半段
                ListNode tmpResult = reverseNode(sliceHead);
                //组装
                if(result == null){
                    result = tmpResult;
                }else{
                    lastTail.next = sliceTail;
                }
                lastTail = sliceHead;


                sliceHead = head;
                sliceTail = head;
            }else{
                head = head.next;
                sliceTail=sliceTail.next;
            }
        }

        if(modCount < k){
            return sourceHead;
        }
        lastTail.next = sliceHead;
        return result;
    }

    public  ListNode reverseNode(ListNode head){
        ListNode curPoint = head,nextPoint = head,prePoint = null;

        while (nextPoint != null){
            nextPoint = curPoint.next;
            curPoint.next = prePoint;
            prePoint = curPoint;
            curPoint = nextPoint;
        }
        return prePoint;
    }

    @Test
    public void testReverse(){
        ListNode list1 = new ListNode(1);
//        list1.next = new ListNode(5);
//        list1.next.next = new ListNode(7);
//        list1.next.next.next = new ListNode(8);
//        list1.next.next.next.next = new ListNode(9);

        ListNode result = reverseKGroup(list1,2);
        while (result != null){
            System.out.println(result.val);
            result = result.next;
        }
    }


}



class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
}