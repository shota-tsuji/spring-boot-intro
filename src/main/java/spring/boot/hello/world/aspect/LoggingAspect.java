package spring.boot.hello.world.aspect;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(String spring.boot.hello.world.controller.HomeController.showList(..))")
    public void startLog(JoinPoint jp) {
        logger.info("{}: Before Processing", jp.getSignature());
    }

    @After("execution(* spring.boot.hello.world.controller.HomeController.*(..))")
    public void endLog(JoinPoint jp) {
        logger.info("{}: After Processing", jp.getSignature());
    }

    @Around("execution(* spring.boot.hello.world..*(..))")
    public Object startAndEndLog(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("{}: Around Pre-Processing", pjp.getSignature());
        Object result = pjp.proceed();
        logger.info("{}: Around Post-Processing", pjp.getSignature());

        return result;
    }

    @AfterReturning(
            pointcut = "within(spring.boot.hello.world.controller.*Controller)",
            returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        logger.info("{}: return result = {}", jp.getSignature(), result);
    }

    @AfterThrowing(
            pointcut = "bean(homeController)",
            throwing = "e")
    public void afterThrowing(JoinPoint jp, Throwable e) {
        logger.error("{}: Exception occurred in Processing: {}",
                jp.getSignature(), e.getMessage());
    }
}
