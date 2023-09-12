package com.zzy.boot_bootis.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.zzy.boot_bootis.common.api.CommonResult;
import com.zzy.boot_bootis.service.RedisService;
import com.zzy.boot_bootis.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @ClassName UmsMemberServiceImpl
 * @Author ZZy
 * @Date 2023/9/10 22:58
 * @Description
 * @Version 1.0
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Value("${redis.key.prefix.authCode}")
    String REDIS_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    Long REDIS_EXPIRE_TIME_SECONDS;

    @Autowired
    RedisService redisService;

    @Override
    public CommonResult generateAuthCode(String phoneNum) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        //根据手机号生成验证码
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        //存储到redis，设置时间
        redisService.set(REDIS_PREFIX_AUTH_CODE+phoneNum,code,REDIS_EXPIRE_TIME_SECONDS);
        return CommonResult.success(code);
    }

    @Override
    public CommonResult verifyAuthCode(String phoneNum, String authCode) {
        if (StringUtil.isEmpty(authCode)) {
            return CommonResult.failed("请出入验证码");
        }
        //根据phone获取cacheCode
        String cacheCode = (String) redisService.get(REDIS_PREFIX_AUTH_CODE+phoneNum);
        Boolean result = cacheCode.equals(authCode);
        //校验cacheCode
        if (result) {
            return CommonResult.success("验证码正确");
        } else {
            return CommonResult.failed("验证码错误，请重新输入");
        }

    }
}
