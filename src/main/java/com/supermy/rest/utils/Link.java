package com.supermy.rest.utils;

/**
 * Created by moyong on 15/5/14.
 * 简单链表结构
 */
public class Link {
    private Node root;

    class Node {
        private String name;
        private Node Next;

        public Node(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void addNode(Node newNode) {
            if (this.Next == null) {
                this.Next = newNode;
            } else {
                this.Next.addNode(newNode);
            }
        }

        public void printNode() {
            System.out.print(this.name + "-->");
            if (this.Next != null) {
                this.Next.printNode();
            }
        }
    }

    public void add(String name) {
        Node newNode = new Node(name);
        if (this.root == null) {
            this.root = newNode;
        } else {
            this.root.addNode(newNode);
        }
    }

    public void print() {
        if (this.root != null) {
            this.root.printNode();
        }
    }
}
