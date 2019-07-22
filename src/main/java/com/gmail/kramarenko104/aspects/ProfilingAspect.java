package com.gmail.kramarenko104.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfilingAspect {

   private Logger logger = LoggerFactory.getLogger(getClass());

   @Around("execution(* com.gmail.kramarenko104.repositories.*.*(..))")
   public Object calcTime(ProceedingJoinPoint proceedingJoinPoint){
        long start = System.currentTimeMillis();
        Object output = null;
       try {
           output = proceedingJoinPoint.proceed();
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
       long duration = System.currentTimeMillis() - start;
       logger.debug("Run duration = " + duration + " for" + proceedingJoinPoint.getSignature().toString());
       return output;
   }
}
