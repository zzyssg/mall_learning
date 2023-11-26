package com.zzy.malllearningmybatis.common.api;

import lombok.Data;

@Data
public class CommonResult<T> {
    private long code;
    private String msg;
    private T data;

    protected CommonResult(long code,String msg,T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;

    }

    /**
     * 成功返回
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),data);
    }

}
