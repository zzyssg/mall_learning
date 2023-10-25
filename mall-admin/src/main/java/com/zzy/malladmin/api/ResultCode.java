package com.zzy.malladmin.api;

import com.zzy.malladmin.common.IErrorCode;
import io.swagger.models.auth.In;

/**
 * @ClassName ResultCode
 * @Author ZZy
 * @Date 2023/10/17 9:52
 * @Description
 * @Version 1.0
 */
public enum ResultCode implements IErrorCode {

    SUCCESS(200,"成功"),
    FAILED(500, "失败"),
    FORBIDDEN(403, "无权限"),
    VALIDATE_FAILED(404,"参数校验失败"),
    UNAUTHENTICATED(401,"未授权");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return this.code;
    }


    public String getMessage() {
        return this.message;
    }
}
