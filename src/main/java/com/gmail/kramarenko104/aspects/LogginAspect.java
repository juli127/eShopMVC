package com.gmail.kramarenko104.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogginAspect {

    private Logger logger = Logger.getLogger(getClass());


}
