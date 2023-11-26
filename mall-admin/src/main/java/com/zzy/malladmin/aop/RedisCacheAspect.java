package com.zzy.malladmin.aop;

import com.zzy.malladmin.annotation.CacheException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName RedisCacheAspect
 * @Author ZZy
 * @Date 2023/10/25 23:02
 * @Description
 * @Version 1.0
 */
@Aspect
@Component
public class RedisCacheAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheAspect.class);

    @Pointcut("execution(public * com.zzy.malladmin.controller.*.* (..))")
    public void redis() {

    }

    @Around("redis()")
    public Object aroundRedis(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            if (method.isAnnotationPresent(CacheException.class)) {
                throw e;
            } else {
                LOGGER.error(e.getMessage());
            }
        }
        return result;
    }

}
