package com.supermy.rest.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by moyong on 14/12/25.
 */
@Component
@Aspect
public class MyAspect {
    private final Logger logger = Logger.getLogger(MyAspect.class);


    @Pointcut("execution(public * com.supermy..*.service(..))")
    private void anyPublicOperation() {}

    @Pointcut("execution(* *(..))")
    private void pointcut() {}

    /**
     * 定义前置通知
     */
    @Before("anyPublicOperation()")
    public void doBefore() {
//		logger.debug("日志前置通知");
        System.out.println(">>>before");

    }

    /**
     * 定义后置通知
     */
    @AfterReturning("anyPublicOperation()")
    public void doAfterReturning() {
//		logger.debug("日志后置通知");
    }

    /**
     * 定义例外通知
     */
    @AfterThrowing("anyPublicOperation()")
    public void doAfterException() {
//		logger.debug("日志异常通知");
    }

    /**
     * 定义最终通知
     */
    @After("anyPublicOperation()")
    public void doAfter() {
//		logger.debug("日志最终通知");
        System.out.println(">>>after");

    }


    /**
     * 记录方法参数，计算完成方法消耗时间
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("com.supermy.rest.aop.SystemArchitecture.inWebLayer() || com.supermy.rest.aop.SystemArchitecture.inServiceLayer() || com.supermy.rest.aop.SystemArchitecture.inDataAccessLayer() || com.supermy.rest.aop.SystemArchitecture.businessService() || com.supermy.rest.aop.SystemArchitecture.dataAccessOperation()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {

        String fullmethodName = pjp.getSignature().getDeclaringTypeName() + "."
                + pjp.getSignature().getName();
        System.out.println(">>>>>>>>>>>>>>>>>>进入方法:" + fullmethodName);
        long start = System.currentTimeMillis();
        String userid = "test";
        Object[] args = pjp.getArgs();
        String paramvalue="";
		for (Object obj : args) {
			System.out.println(">>>>>>>>>>>>>>>>>>参数:" + obj);
			paramvalue=paramvalue+"###"+obj.toString();
		}

        // 必须执行pjp.proceed()方法,如果不执行此方法,业务bean的方法以及后续通知都不执行
        // start stopwatch
        Object retVal = pjp.proceed();
        long end = System.currentTimeMillis();
        System.out.println(" fullmethodName:"+fullmethodName+" have time:"+(end - start)+"  paramvalue:"+ paramvalue);
        System.out.println(">>>>>>>>>>>>>>>>>退出方法");
        // stop stopwatch
        return retVal;
    }
}
