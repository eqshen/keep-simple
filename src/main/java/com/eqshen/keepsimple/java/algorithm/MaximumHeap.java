package com.eqshen.keepsimple.java.algorithm;


import java.util.NoSuchElementException;

/**
 * 大顶堆或大根堆的实现
 * 数组实现
 * cite https://www.cnblogs.com/noKing/p/7954898.html
 * cite https://www.bilibili.com/video/BV1ti4y1879c?from=search&seid=14449813587286729555
 * @param <T>
 */
public class MaximumHeap<T extends Comparable<T>> {

    public static void main(String[] args) {
        //test
        int[]nums = {2,4,1,3,6,7,100,0,-1};
        MaximumHeap<Integer> heap = new MaximumHeap<>(100);
        for (int num : nums) {
            heap.offer(num);
        }

        //display
        int size = heap.size();
        for (int i = 0; i < size; i++) {
            System.out.print(heap.poll()+"  ");
        }
    }

    /**
     * 当前堆大小
     */
    private int size;
    /**
     * 最大容量
     */
    private int capacity;

    /**
     * 数组，存放维护完全二叉树结构
     */
    private T[] heap;

    public MaximumHeap(int capacity){
        this.size = 0;
        this.capacity = capacity;
        this.heap = (T[]) new Comparable[capacity];
    }


    /**
     * 移除并返回堆顶元素
     * @return
     */
    public T poll(){

        T ele = peek();
        swap(0,size-1);
        size--;
        shiftDown();
        return ele;
    }

    /**
     * 往堆里面加入一个元素
     * @param element
     */
    public void offer(T element){
        //
        resize();
        heap[size++] = element;
        shiftUp();
    }

    /**
     * 返回堆顶元素
     * @return
     */
    public T peek(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        return heap[0];
    }

    public int size(){
        return size;
    }



    //========


    private void shiftUp(){
        int index = size - 1;
        while (hashParent(index)
                && heap[getParentIndex(index)].compareTo(heap[index]) < 0){
            swap(index,getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private void shiftDown(){
        int index = 0;
        while (hashLeft(index)){
            int largeNodeIndex = getLeftIndex(index);
            if(hashRight(index)){
                int rightNodeIndex = getRightIndex(index);
                if(heap[rightNodeIndex].compareTo(heap[largeNodeIndex]) > 0){
                    largeNodeIndex = rightNodeIndex;
                }
            }
            if(heap[index].compareTo(heap[largeNodeIndex]) < 0){
                swap(index,largeNodeIndex);
                index = largeNodeIndex;
            }else{
                break;
            }

        }
    }

    private void swap(int i,int j){
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resize(){
        if(size == capacity){
            //heap = Arrays.copyOf(heap,capacity*2);
            //capacity*=2;
            throw new RuntimeException("堆已满");
        }

    }

    private int getLeftIndex(int parentIndex){
        return parentIndex * 2 + 1;
    }

    private int getRightIndex(int parentIndex){
        return parentIndex * 2 + 2;
    }

    private int getParentIndex(int childIndex){
        return (childIndex-1) / 2;
    }

    private boolean hashLeft(int parentIndex){
        return getLeftIndex(parentIndex) < size;
    }

    private boolean hashRight(int parentIndex){
        return getRightIndex(parentIndex) < size;
    }

    private boolean hashParent(int childIndex){
        return getParentIndex(childIndex) >= 0;
    }
}
