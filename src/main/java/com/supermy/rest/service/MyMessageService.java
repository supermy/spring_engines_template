package com.supermy.rest.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by moyong on 14/12/24.
 */
@Component
public class MyMessageService {


    public String getMessage(String isbn) {
        //execAsyncAddLog("test","async",111,"paramvalue");
        System.out.println("来吧:"+isbn);
        return "Hello JamesMo!"+isbn;
    }

    @Cacheable(value = "defaultCache",key="'com.bonc.rest.service.getMessage'.hashCode()+#isbn.hashCode()")
    public String getMessageCache(String isbn) {
        //execAsyncAddLog("test","async",111,"paramvalue");
        System.out.println("来吧:"+isbn);
        return "Hello JamesMo!"+isbn;
    }

    @CacheEvict(value = "defaultCache", allEntries = true)
    public void delMessage(String isbn) {
        //execAsyncAddLog("test","async",111,"paramvalue");
    }

}