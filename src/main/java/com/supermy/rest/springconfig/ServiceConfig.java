package com.supermy.rest.springconfig;

/**
 * Created by moyong on 14/12/24.
 */
import com.supermy.rest.utils.MyKeyGenerator;
import com.google.code.ssm.Cache;
import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.CacheConfiguration;
import com.google.code.ssm.providers.spymemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.spring.SSMCache;
import com.google.code.ssm.spring.SSMCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.*;

/**
 * 1.使用注解完成:约定俗成；架构分层、缓存、调试信息;业务与架构分离，技术人员专心于业务<br/>
 * 2.架构划分3层，数据存储层，业务逻辑层，控制表现层；<br/>
 * 3.引入memcached缓存；<br/>
 * 4.脱离数据库环境进行测试；<br/>
 *
 */
@Configuration
@ComponentScan({"**.aop","**.service","hello","**.web"})
@EnableAspectJAutoProxy
@EnableAsync
//@EnableScheduling
@PropertySource("classpath:app.properties")
@EnableCaching
//@EnableLoadTimeWeaving //允许时间监控
public abstract  class ServiceConfig implements CachingConfigurer ,LoadTimeWeavingConfigurer{

    @Override
    public LoadTimeWeaver getLoadTimeWeaver() {
        return new ReflectiveLoadTimeWeaver();
    }

    @Autowired
    Environment env;

    @Bean
    @Override
    public CacheManager cacheManager() {
        // configure and return an implementation of Spring's CacheManager SPI
        SSMCacheManager sm=new SSMCacheManager();

        MemcacheClientFactoryImpl cacheClientFactory=new MemcacheClientFactoryImpl();

        DefaultAddressProvider addressProvider=new   DefaultAddressProvider();
        addressProvider.setAddress(env.getProperty("memcached1_hostport"));

        CacheConfiguration configuration=new CacheConfiguration();
        configuration.setConsistentHashing(true);


        CacheFactory defaultCache=new CacheFactory();
        defaultCache.setCacheName("defaultCache");
        defaultCache.setCacheClientFactory(cacheClientFactory);
        defaultCache.setAddressProvider(addressProvider);
        defaultCache.setConfiguration(configuration);
        Cache cache = null;
        try {
            cache = defaultCache.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SSMCache ssmc=new SSMCache(cache,300,false);


        List set=new ArrayList();
        set.add(ssmc);
        sm.setCaches(set);


        return sm;


    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        // configure and return an implementation of Spring's KeyGenerator SPI
        return new MyKeyGenerator();
    }

}
