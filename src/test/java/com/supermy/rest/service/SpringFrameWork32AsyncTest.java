package com.supermy.rest.service;

/**
 * Created by moyong on 14/12/24.
 */

import com.supermy.rest.aop.SpringAutoConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringAutoConfig.class)
//@TransactionConfiguration(transactionManager="txMgr", defaultRollback=false)
//@Transactional
public class SpringFrameWork32AsyncTest {


    @Autowired
    public MyAsyncService myAsyncService;


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringAutoConfig.class);



        MyAsyncService myAsyncService = context.getBean(MyAsyncService.class);

        //可以使用future.get(time,unit)，在指定的时间内获取返回值，如果超过设置的时间则抛出异常，异步运行.
        Future<String> future = myAsyncService.returnSomething13(123);
        try {

            System.out.println(future.get(1, TimeUnit.MINUTES));

        } catch (TimeoutException e) {
            System.out.println("等待中...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(future.isDone());

    }


    @Test
    public void Scheduled() {
        myAsyncService.doSomething();
        myAsyncService.doSomething1();
        myAsyncService.doSomething2();
    }
}
