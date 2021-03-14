package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import com.eqshen.keepsimple.java.algorithm.dto.ListNode;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class LinkedListRelevant extends BaseTest {



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

    public ListNode reverseKGroupCur(ListNode head,int k){
        if(head == null){
            return null;
        }
        //翻转前k个
        ListNode a,b;
        a = b = head;
        for (int i = 0; i < k; i++) {
            if(b == null){
                return head;
            }
            b = b.next;
        }
        ListNode newHead = reverseBetween(a,b);
        a.next = reverseKGroupCur(b,k);
        return newHead;
    }

    /**
     * 翻转[a,b)两个节点之间的节点
     * @param a
     * @param b
     * @return
     */
    public ListNode reverseBetween(ListNode a,ListNode b){
        ListNode pre = null;
        ListNode cur = a;
        ListNode next = a;
        while (next != b){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
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




    /**
     * 61
     * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null){
            return head;
        }
        int len = 0;
        ListNode cur = head;
        ListNode tail = null;
        while (cur != null){
            len++;
            if(cur.next == null){
                tail = cur;
            }
            cur = cur.next;
        }
        k = k % len;
        if(k == 0){
            return head;
        }

        //寻找倒数第k个结点（正数len-k+1个），并将其作为首结点
        ListNode oldHead = head;
        for (int i = 0; i <len-k -1 ; i++) {
            head = head.next;
        }
        ListNode newHead = head.next;
        head.next = null;
        tail.next = oldHead;
        return newHead;
    }



    /**
     * 翻转链表的前N个节点
     * 递归实现
     * @param head
     * @param n [1,]
     * @return
     */
    private ListNode successor = null;
    public ListNode reverseN(ListNode head,int n){
        //终止条件
        if(n == 1){
            successor = head.next;
            return head;
        }
        ListNode newHead = reverseN(head.next,n-1);
        head.next.next = head;
        head.next = successor;
        return newHead;
    }

    /**
     * 翻转[m,n] 部分节点
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode revertBetween(ListNode head,int m,int n){

        //base case
        if(m == 1){
            return reverseN(head,n);
        }
        head.next = revertBetween(head.next,m-1,n-1);
        return head;
    }

    public void showList(ListNode head){
        while (head != null){
            System.out.print(head.val+" -> ");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * 测试链表是否是回文链表
     * @param head
     * @return
     */
    private ListNode left;
    public boolean checkLinkPalindrome(ListNode head){
        left = head;
        return palindromeHelp(head);
    }

    public boolean palindromeHelp(ListNode head){
        if(head == null) return true;
        boolean subRes = palindromeHelp(head.next);
        if(!subRes || head.val != left.val){
            return false;
        }
        left = left.next;
        return true;
    }
}
