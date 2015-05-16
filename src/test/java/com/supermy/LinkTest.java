package com.supermy;

import com.supermy.rest.utils.Link;

/**
 * Created by moyong on 15/5/14.
 */
public class LinkTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Link link = new Link();
        link.add("根节点");
        link.add("第一节点");
        link.add("第二节点");
        link.add("第三节点");
        link.add("第四节点");
        link.print();
        System.out.println("null");
    }

}