package com.zzy.malladmin.common;

import com.zzy.malladmin.api.ResultCode;
import lombok.Data;

/**
 * @ClassName CommonResult
 * @Author ZZy
 * @Date 2023/9/27 16:02
 * @Description
 * @Version 1.0
 */
@Data
public class CommonResult<T> {

    private String msg;
    private String code;
    private T data;


    public CommonResult() {
    }

    public CommonResult(String msg, String code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
    //成功
    public static <T> CommonResult<T> success() {
        CommonResult result = new CommonResult();
        result.setMsg("成功");
        result.setData(null);
        result.setCode("200");
        return result;
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult result = new CommonResult();
        result.setMsg("成功");
        result.setData(data);
        result.setCode("200");
        return result;
    }

    public static <T> CommonResult<T> success(String msg) {
        CommonResult result = new CommonResult();
        result.setMsg(msg);
        result.setData(null);
        result.setCode("200");
        return result;
    }

    //失败
    public static <T> CommonResult<T> failed(String msg) {
        CommonResult result = new CommonResult();
        result.setMsg(msg);
        result.setData(null);
        result.setCode("300");
        return result;
    }

    public static <T> CommonResult<T> failed() {
        CommonResult result = new CommonResult();
        result.setMsg("失败");
        result.setData(null);
        result.setCode("300");
        return result;
    }


    public static <T> CommonResult<T> forbidden(String message) {
        CommonResult result = new CommonResult();
        result.setMsg(ResultCode.FORBIDDEN.getMessage());
        result.setData(null);
        result.setCode(ResultCode.FORBIDDEN.getCode() + "");
        return result;
    }

    public static <T> CommonResult<T> unauthorized(String message) {
        CommonResult result = new CommonResult();
        result.setCode(ResultCode.UNAUTHENTICATED.getCode()+"");
        result.setData(null);
        result.setMsg(ResultCode.UNAUTHENTICATED.getMessage());
        return result;
    }
}
