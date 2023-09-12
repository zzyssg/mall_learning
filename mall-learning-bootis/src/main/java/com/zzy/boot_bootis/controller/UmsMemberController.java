package com.zzy.boot_bootis.controller;

import com.zzy.boot_bootis.common.api.CommonResult;
import com.zzy.boot_bootis.service.UmsMemberService;
import io.lettuce.core.dynamic.annotation.Command;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UmsMemberController
 * @Author ZZy
 * @Date 2023/9/10 23:18
 * @Description 会员登录注册管理
 * @Version 1.0
 */
@Api(tags = "UmsMemberController")
@Tag(name = "UmsMemberController", description = "会员登录注册管理")
@RestController
@RequestMapping(value = "/sso")
public class UmsMemberController {
    @Autowired
    UmsMemberService umsMemberService;

    @ApiOperation("生成验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    public CommonResult getAuthCode(@RequestParam("phone") String phone) {
        return umsMemberService.generateAuthCode(phone);
    }

    @ApiOperation("校验验证码")
    @RequestMapping(value = "validAuthCode", method = RequestMethod.POST)
    public CommonResult validAuthCode(@RequestParam("phone") String phone,
                                      @RequestParam("authCode") String code) {
        return umsMemberService.verifyAuthCode(phone, code);
    }

}
