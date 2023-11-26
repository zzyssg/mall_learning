package com.zzy.boot_bootis.common.api;

/**
 * @ClassName ResultCode
 * @Author ZZy
 * @Date 2023/9/3 18:02
 * @Version 1.0
 */
public enum ResultCode implements IErrorCode{
    SUCCESS(200,"成功"),
    FAILED(500, "失败"),
    UNAUTHORIZED(401,"暂未登录或者授权已过期"),
    FORBIDDEN(403,"没有相关权限"),
    VALIDATE_FAILED(404,"参数校验失败");



    private long code;

    private String message;

    private ResultCode(long code, String message) {
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
