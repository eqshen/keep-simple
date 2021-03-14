package com.eqshen.keepsimple.java.algorithm.test;

import com.eqshen.keepsimple.java.algorithm.LinkedListRelevant;
import com.eqshen.keepsimple.java.algorithm.dto.ListNode;
import org.junit.Test;

import java.util.List;

/**
 * @author eqshen
 * @description
 * @date 2021/3/11
 */
public class LinkedListRelevantTest {

    private LinkedListRelevant lr = new LinkedListRelevant();

    @Test
    public void testMergeKlists(){

        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(5);
        list1.next.next = new ListNode(7);

        ListNode list2 = new ListNode(2);
        list2.next = new ListNode(8);

        ListNode[] lists = {list1,list2};

        ListNode resultList = lr.mergeKListsPlus(lists);

        while (resultList != null){
            System.out.println(resultList.val);
            resultList = resultList.next;
        }
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

        ListNode head = lr.swapPairs(node1);

        lr.showList(head);
    }

    @Test
    public void testReverse(){
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        lr.showList(l1);
        ListNode head = lr.reverseN(l1,3);
        lr.showList(head);
    }

    @Test
    public void testReverse2(){
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(5);
        list1.next.next = new ListNode(7);
        list1.next.next.next = new ListNode(8);
        list1.next.next.next.next = new ListNode(9);

        ListNode result = lr.reverseKGroupCur(list1,3);
        while (result != null){
            System.out.println(result.val);
            result = result.next;
        }
    }

    @Test
    public void testRotateRight(){
        ListNode head = this.createLink();
        ListNode result = lr.rotateRight(head,2);
        while (result != null){
            System.out.println(result.val);
            result = result.next;
        }
    }

    @Test
    public void testPalindrome(){
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(2);
        list1.next.next = new ListNode(3);
        list1.next.next.next = new ListNode(2);
        list1.next.next.next.next = new ListNode(1);
        System.out.println(lr.checkLinkPalindrome(list1));
    }

    private ListNode createLink(){
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(2);
        list1.next.next = new ListNode(3);
        list1.next.next.next = new ListNode(4);
        list1.next.next.next.next = new ListNode(5);
        return list1;
    }

}
