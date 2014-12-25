package com.supermy.rest.utils;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;


/**
 * Created by moyong on 14/12/24.
 */
public class MyKeyGenerator implements KeyGenerator {
    public static final int NO_PARAM_KEY = 0;
    public static final int NULL_PARAM_KEY = 53;
    public Object generate(Object target, Method method, Object... params) {
        String prefixKey=(target.getClass().getName()+"."+method.getName()).hashCode()+"";
        if (params.length == 1) {
            return (params[0] == null ? prefixKey+NULL_PARAM_KEY : prefixKey+params[0]);
        }
        if (params.length == 0) {
            return prefixKey+NO_PARAM_KEY;
        }
        int hashCode = 17;
        for (Object object : params) {
            hashCode = 31 * hashCode + (object == null ? NULL_PARAM_KEY : object.hashCode());
        }
        return prefixKey+Integer.valueOf(hashCode);
    }
}
