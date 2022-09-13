package com.tilmeez.springdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    // setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    //setup pointcut declarations
    //Add @Before advice
    //Add @AfterReturning advice
}


