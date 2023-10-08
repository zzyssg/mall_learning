package com.zzy.malladmin.exception;

import com.zzy.malladmin.common.IErrorCode;

/**
 * @ClassName ApiException
 * @Author ZZy
 * @Date 2023/10/6 18:35
 * @Description
 * @Version 1.0
 */
public class ApiException extends RuntimeException{

    IErrorCode iErrorCode;

    public ApiException(IErrorCode iErrorCode) {
        super(iErrorCode.getMessage());
        this.iErrorCode = iErrorCode;
    }

    public ApiException(String message, IErrorCode iErrorCode) {
        super(message);
        this.iErrorCode = iErrorCode;
    }

    public ApiException(String message, Throwable cause, IErrorCode iErrorCode) {
        super(message, cause);
        this.iErrorCode = iErrorCode;
    }

    public ApiException(Throwable cause, IErrorCode iErrorCode) {
        super(cause);
        this.iErrorCode = iErrorCode;
    }

    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, IErrorCode iErrorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.iErrorCode = iErrorCode;
    }

    public ApiException(String message) {
        super(message);
    }
}
