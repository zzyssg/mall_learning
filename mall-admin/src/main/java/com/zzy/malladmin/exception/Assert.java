package com.zzy.malladmin.exception;

import com.zzy.malladmin.common.IErrorCode;

/**
 * @ClassName Assert
 * @Author ZZy
 * @Date 2023/10/6 18:39
 * @Description
 * @Version 1.0
 */
public class Assert {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
