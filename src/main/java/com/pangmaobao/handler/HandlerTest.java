package com.pangmaobao.handler;

import com.pangmaobao.annotation.AnnotationTest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author: he.feng
 * @date: 13:43 2018/1/10
 * @desc:
 **/
@Component
@Aspect
public class HandlerTest {

    private static final Logger logger = LogManager.getLogger(HandlerTest.class);

    /**
     * 定义一个切点
     */
    @Pointcut("execution(* com.pangmaobao.controller..*.*(..))")
    public void testPointcut(){}


    @Around(value = "testPointcut() && @annotation(test)")
    @Order(1)
    public String doAroundTest(ProceedingJoinPoint proceedingJoinPoint,AnnotationTest test) throws Throwable {
        logger.info("切面doAroundTest 方法【" + proceedingJoinPoint.getSignature().getName() + "】");
        logger.info("切面doAroundTest 参数【" + proceedingJoinPoint.getArgs()[0]+ "】");
        logger.info("注解的值【" + test.value() + "】");
        String result = (String) proceedingJoinPoint.proceed();
        logger.info("执行切面方法返回参数:【 " + result + "】");
        return result;

    }
}
