package com.supermy.rest.service;

/**
 * Created by moyong on 14/12/24.
 */

import com.supermy.rest.aop.SpringAutoConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringAutoConfig.class)
//@TransactionConfiguration(transactionManager="txMgr", defaultRollback=false)
//@Transactional
public class SpringFrameWork32Test {

    @Autowired
    public MyMessageService myMessageService;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringAutoConfig.class);

        MyMessageService myprinter = context.getBean(MyMessageService.class);
        System.out.println(myprinter.getMessageCache("123"));
        System.out.println(myprinter.getMessageCache("123"));
        System.out.println(myprinter.getMessage("hello"));
        System.out.println(myprinter.getMessage("hello"));


    }

    @Test
    public void getMsg() {

        Assert.assertNotNull(myMessageService.getMessage("123"));
        Assert.assertNotNull(myMessageService.getMessage("456"));
        Assert.assertNotNull(myMessageService.getMessageCache("hello"));
        Assert.assertNotNull(myMessageService.getMessageCache("hello"));

    }


}
