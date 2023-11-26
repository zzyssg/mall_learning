package com.zzy.malladmin.common;

/**
 * @program: mall_learning
 * @description: 通用结果判断处理
 * @author: zzy
 * @create: 2023-11-26
 */
public class CommonResultAssert {

    public static CommonResult deal(int count) {
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }


}