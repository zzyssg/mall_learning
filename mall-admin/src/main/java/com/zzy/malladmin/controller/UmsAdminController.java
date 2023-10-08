package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.UmsAdminLoginParam;
import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName UmsAdminController
 * @Author ZZy
 * @Date 2023/9/27 14:50
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "UmsAdminController")
@Tag(name = "UmsAdminController", description = "后台用户功能")
public class UmsAdminController {

    @Autowired
    UmsAdminService umsAdminService;

    @Value("${jwt.head}")
    String tokenHead;

    /**
     * 根据传递的用户注册信息，新建后台用户并且返回通用类
     * controller：传入注册信息调用service层的register方法
     * service：
     * @param umsAdmin
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "管理员注册")
    public CommonResult register(@RequestBody UmsAdmin umsAdmin) {
        int count = umsAdminService.register(umsAdmin);
        if (count > 0) {
            return CommonResult.success("注册成功");
        } else {
            return CommonResult.failed("注册失败");
        }
    }

    /**
     * 根据传递的账号、密码获取token，返回通用类
     * controller：通过service使用（username,password)调用service层的login方法，对返回的token封装并返回
     * service：
     * @param umsAdminLoginParam
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam) {

        String token = umsAdminService.login(umsAdminLoginParam);
        if (token == null) {
            return CommonResult.failed("用户名或者密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    /**
     * 根据请求刷新token
     * 从请求通过getHeader()方法传入约定好的“tokenHeader”中获取token
     * 调用service层的refreshToken方法更新token
     * 封装新token，返回一个map对象到T，
     * @param request
     * @return
     */
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHead);
        String newToken = umsAdminService.refreshToken(token);
        if (newToken == null) {
            return CommonResult.failed("token无效");
        }
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", newToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    /**
     * 展示用户列表
     *
     * @param keywords
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywords", value = "用户姓名、昵称"),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量"),
            @ApiImplicitParam(name = "pageNum", value = "页码")
    })
    @ApiOperation(value = "展示用户列表")
    public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam("keywords") String keywords,
                                                   @RequestParam("pageSize") Integer pageSize,
                                                   @RequestParam("pageNum") Integer pageNum) {
        //查询用户列表
        List<UmsAdmin> umsAdminList = umsAdminService.list(keywords, pageNum, pageSize);
        //返回
        return CommonResult.success(CommonPage.restPage(umsAdminList));
    }

    /**
     * 根据用id搜索用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id搜索用户")
    public CommonResult getItem(@PathVariable("id") Long id) {
        UmsAdmin umsAdmin = umsAdminService.getItem(id);
        if (umsAdmin == null) {
            CommonResult.failed("未查询到用户");
        }
        return CommonResult.success(umsAdmin);
    }


    /**
     * 更新用户信息
     *
     * @param umsAdmin
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户信息")
    public CommonResult update(@RequestBody UmsAdmin umsAdmin) {
        Long id = umsAdmin.getId();
        UmsAdmin item = umsAdminService.getItem(id);
        if (id == null || item == null) {
            return CommonResult.failed("用户不存在");
        }
        int count = umsAdminService.update(umsAdmin);
        if (count != 1) {
            return CommonResult.failed("更新用户失败");
        } else {
            return CommonResult.success("更新成功");
        }
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除用户信息")
    public CommonResult delete(@PathVariable("id") Long id) {
        int cout = umsAdminService.deleteById(id);
        if (cout == 1) {
            return CommonResult.success("删除用户成功OK");
        } else {
            return CommonResult.failed("删除用户信息失败FAILED");
        }
    }

}
