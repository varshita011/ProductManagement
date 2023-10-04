package com.varshitha.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodExecutionTimeLoggerAspect {

	Logger log = LoggerFactory.getLogger(MethodExecutionTimeLoggerAspect.class);
	
	@Around("execution(* com.varshitha.controller.*.*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Method {} executed in {}ms", className + "." + methodName, executionTime);
        log.info("Aspect method executed successfully.");
        return result; 
	}          
}
