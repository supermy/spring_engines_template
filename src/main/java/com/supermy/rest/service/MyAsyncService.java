package com.supermy.rest.service;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Future;

/**
 * 只要初始化，计划方法就一直执行
 * Created by moyong on 14/12/24.
 */
@Component
public class MyAsyncService {

    private final Logger logger = Logger.getLogger(MyAsyncService.class);


    /**
     * 每5秒執行一次打印
     */
    @Scheduled(fixedDelay=5000)
    public void doSomething() {
        // something that should execute periodically
        logger.debug(new Date());
    }

    /**
     * 一秒後，每5秒執行一次打印
     */
    @Scheduled(initialDelay=1000, fixedRate=5000)
    public void doSomething1() {
        // something that should execute periodically
        logger.debug(new Date());
    }

    /**
     * cron 定义具体的时间
     */
    @Scheduled(cron="*/5 * * * * MON-FRI")
    public void doSomething2() {
        // something that should execute on weekdays only
        logger.debug(new Date());
    }


    @Async
    public void doSomething11() {
        // this will be executed asynchronously
        logger.debug(new Date());
    }

    @Async
    public void doSomething12(String s) {
        // this will be executed asynchronously
        logger.debug(new Date());
    }

    @Async
    public Future<String> returnSomething13(int i) {
        // this will be executed asynchronously
        return new AsyncResult<String>(i+"");
    }


}