package com.kikiiz.demo.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/*
AOP:切面截获，对所有Controller做统一处理
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.kikiiz.demo.controller.IndexController.*(..))")
    public void beforeMethod(){
        logger.info("before method");
    }

    @After("execution(* com.kikiiz.demo.controller.IndexController.*(..))")
    public void afterMethod(){
        logger.info("after method");
    }
}
