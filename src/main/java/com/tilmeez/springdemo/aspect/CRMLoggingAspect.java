package com.tilmeez.springdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    // setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    //setup pointcut declarations
    @Pointcut("execution(* com.tilmeez.springdemo.controller.*.*(..))")
    private void forControllerPackage() {

    }

    // do the same for server and dao
    @Pointcut("execution(* com.tilmeez.springdemo.service.*.*(..))")
    private void forServicePackage() {

    }

    @Pointcut("execution(* com.tilmeez.springdemo.dao.*.*(..))")
    private void forDaoPackage() {

    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {

    }

    //Add @Before advice
    //Add @AfterReturning advice
}


