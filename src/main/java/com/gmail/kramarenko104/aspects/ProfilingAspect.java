package com.gmail.kramarenko104.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfilingAspect {

   private Logger logger = LoggerFactory.getLogger(getClass());

   @Pointcut("execution(* com.gmail.kramarenko104.repositories.*.*(..))")
   private void callAspect(){}

   @Around("callAspect()")
   public void calcTime(ProceedingJoinPoint proceedingJoinPoint) {
       long start = System.currentTimeMillis();
       try {
           proceedingJoinPoint.proceed();
           long duration = System.currentTimeMillis() - start;
           logger.debug("Run duration = " + duration + " for" + proceedingJoinPoint.getSignature().toString());
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
   }
}
