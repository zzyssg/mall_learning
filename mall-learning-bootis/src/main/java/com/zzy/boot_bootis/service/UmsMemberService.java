package com.zzy.boot_bootis.service;

import com.zzy.boot_bootis.common.api.CommonResult;

/**
 * @ClassName UmsMemberService
 * @Author ZZy
 * @Date 2023/9/10 22:56
 * @Description
 * @Version 1.0
 */
public interface UmsMemberService {

    public CommonResult generateAuthCode(String phoneNum);

    public CommonResult verifyAuthCode(String phoneNum, String authCode);
}
