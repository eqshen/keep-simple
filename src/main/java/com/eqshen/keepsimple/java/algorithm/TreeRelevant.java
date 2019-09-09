package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeRelevant extends BaseTest {

    /**
     * 94
     * 给定一个二叉树，返回它的中序 遍历。
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List < Integer > res = new ArrayList< >();
        recursiveTraversal(res,root);
        return res;
    }

    private void recursiveTraversal(List<Integer> result,TreeNode root){
        if(root == null)return;
        if(root.left != null){
            recursiveTraversal(result,root.left);
        }
        result.add(root.val);
        if(root.right != null){
            recursiveTraversal(result,root.right);
        }

    }

    /**
     * 解法2，迭代法
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal2(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        TreeNode cur = root;
        while(cur != null||!stack.isEmpty()){
            //无限往左下潜
            while(cur!=null){
                stack.push(cur);
                cur = cur.left;
            }
            //当到达左下方底部
            cur = stack.pop();
            res.add(cur.val);
            cur = cur.right;
        }
        return res;
    }
}


class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
}
