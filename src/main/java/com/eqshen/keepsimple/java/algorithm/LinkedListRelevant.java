package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import org.junit.Test;

public class LinkedListRelevant extends BaseTest {

    @Test
    public void testWwapPairs(){
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