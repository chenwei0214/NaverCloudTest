package com.example.problem2;

/**
 * Project Name: NaverCloudTest
 * Package Name: com.example.problem2
 * Date: 2022/4/7 14:50
 * Created by: wei.chen
 */
public class ArrayQueue {
    private String[] items;
    private int head = 0;
    private int tail = 0;
    private final int n;

    public ArrayQueue(int capacity) {
        this.n = capacity;
        items = new String[capacity];
    }

    public void push(String val) {
        if (this.tail == this.n) {
            System.out.println("Stack is full ");
            return;
        }
        items[this.tail] = val;
        this.tail++;
    }

    public Object pop() {
        if (this.head == this.tail) {
            System.out.println("queue empty");
            return null;
        }
        String val = items[this.head];
        this.head++;
        return val;
    }

    public Object peek() {
        if (this.head == this.tail) {
            System.out.println("queue empty");
            return null;
        }
        return items[this.head];
    }

    public boolean empty() {
        int size = items.length;
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }
        return true;
    }

    public static void main(String[] args) {

        ArrayQueue queue = new ArrayQueue(10);
        for (int i = 0; i < 10; i++) {
            queue.push("" + i);
        }

        System.out.println(queue.peek());
        System.out.println(queue.pop());
        queue.empty();
        System.out.println(queue.peek());
    }
}
