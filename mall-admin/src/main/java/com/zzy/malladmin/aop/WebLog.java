package com.zzy.malladmin.aop;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName WebLog
 * @Author ZZy
 * @Date 2023/10/24 15:02
 * @Description 封装需要记录的日志信息，包括：操作时间，url，请求人，ip，请求参数以及返回结果，操作描述
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
public class WebLog {


    private String description;

    private Long startTime;

    private Integer spendTIme;

    private String username;

    private String url;

    private String uri;

    private String ip;

    private String basePath;

    private String method;

    private Object parameters;

    private Object result;



}
