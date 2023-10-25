package com.zzy.malladmin.aop;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.db.handler.HandleHelper;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import net.logstash.logback.marker.Markers;
import org.apache.tomcat.util.http.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName WebLogAspect
 * @Author ZZy
 * @Date 2023/10/24 15:25
 * @Description 定义切面
 * @Version 1.0
 */
@Aspect
@Component
public class WebLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);


    @Pointcut("execution(* com.zzy.malladmin.controller.*.*(..))")
    public void webLog() {

    }

    @AfterReturning(value = "webLog()", returning = "result")
    public void afterReturning(Object result) {

    }

    @Before("webLog()")
    public void before(JoinPoint joinPoint) throws Throwable {
        //TODO before do
    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        //记录请求信息 TODO 通过Logstash传入Elasticsearch
        //设置描述信息
        WebLog webLog = new WebLog();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation annotation = method.getAnnotation(ApiOperation.class);
            webLog.setDescription(annotation.value());
        }
        //设置其他信息
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        webLog.setSpendTIme((int) spendTime);
        webLog.setStartTime(startTime);
        String urlStr = request.getRequestURL().toString();
        String basePath = StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath());
        webLog.setBasePath(basePath);
        webLog.setUrl(request.getRequestURL().toString());
        webLog.setUri(request.getRequestURI());
        webLog.setUsername(request.getRemoteUser());
        //TODO 遇到代理时，需要调用自定义的RequestUtil
        webLog.setIp(request.getRemoteHost());
        webLog.setMethod(request.getMethod());
        webLog.setResult(result);
        webLog.setParameters(getParameters(method, joinPoint.getArgs()));
        Map<String, Object> map = new HashMap<>();
        map.put("method", webLog.getMethod());
        map.put("url", webLog.getUrl());
        map.put("parameter", webLog.getParameters());
        map.put("spendTime", webLog.getSpendTIme());
        map.put("description", webLog.getDescription());
        //TODO 使用logstash-logback 记录日志
        LOGGER.info(Markers.appendEntries(map), JSONUtil.parse(webLog).toString());
        return result;

    }

    private Object getParameters(Method method, Object[] args) {
        //TODO 查看args的参数列表
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            //将requestBody修饰的参数作为请求参数
            RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将requestParam修饰的参数作为请求参数
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameter.getName();
                if (!StrUtil.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                if (args[i] != null) {
                    map.put(key, args[i]);
                }

            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }

    }


}
