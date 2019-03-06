package com.kikiiz.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
AOP:切面截获，对所有Controller做统一处理。一种思想。
Log?
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.kikiiz.demo.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StringBuilder sb =new StringBuilder();
        for(Object arg:joinPoint.getArgs()){//joinpoint是什么？获得了网页的参数
            sb.append("arg:"+arg.toString()+"|");
        }
        logger.info("before method: "+sb.toString());
    }

    @After("execution(* com.kikiiz.demo.controller.IndexController.*(..))")
    public void afterMethod(){
        logger.info("after method "+new Date());
    }
}
