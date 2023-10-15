package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.UmsAdminLoginParam;
import com.zzy.malladmin.dto.UpdatePasswordParam;
import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.mbg.model.UmsMenu;
import com.zzy.malladmin.mbg.model.UmsRole;
import com.zzy.malladmin.service.UmsAdminService;
import com.zzy.malladmin.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    UmsRoleService umsRoleService;

    @Value("${jwt.head}")
    String tokenHead;

    /**
     * 根据传递的用户注册信息，新建后台用户并且返回通用类
     * controller：传入注册信息调用service层的register方法
     * service：
     *
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
     *
     * @param umsAdminLoginParam
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
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
     *
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
     * TODO 没有放在service层统一处理
     * 获取登录人员信息  （为什么不在登录时直接返回，反而单独的接口？）
     * 通过principal获取账号
     * 通过adminService获取人员信息
     * 封装username、menu、icon、resourceList和roleList到map中返回
     *
     * @param principal
     * @return
     */
    public CommonResult info(Principal principal) {
        String username = principal.getName();
        //通过username获取admin信息
        UmsAdmin umsAdmin = umsAdminService.getAdminByUsername(username);
        if (umsAdmin == null) {
            return CommonResult.failed("info(A a) : 未查到此人员");
        }
        //获取username、icon、menuList、roleList
        Map<String, Object> result = new HashMap<>();
        List<UmsMenu> menuList = umsRoleService.getMenuList(umsAdmin.getId());
        List<UmsRole> roleList = umsAdminService.getRole(umsAdmin.getId());
        List<String> roleNameList = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
        if (roleNameList != null && roleNameList.size() > 0) {
            result.put("role", roleNameList);
        }
        result.put("username", username);
        result.put("icon", umsAdmin.getIcon());
        result.put("menu", menuList);
        return CommonResult.success(result);
    }

    /**
     * 登出功能由前端实现，删除登录凭证
     *
     * @return
     */
    public CommonResult logout() {
        return CommonResult.success(null);
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
     * 更新指定用户信息
     *
     * @param umsAdmin
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户信息")
    public CommonResult update(@PathVariable Long id, @RequestBody UmsAdmin umsAdmin) {
        int count = umsAdminService.update(id, umsAdmin);
        if (count == 1) {
            return CommonResult.success("用户更新成功");
        } else {
            return CommonResult.failed("用户更新失败");
        }
    }

    /**
     * 更新用户密码
     * 1、调用service层updatePassword方法
     * 2、判断参数是否合法，否则返回错误信息，在controller层处理
     * 3、判断旧密码是否正确
     * 4、更细成功后，去除cache里的内容
     *
     * @param updatePasswordwordParam
     * @return
     */
    public CommonResult updatePassword(@Validated @RequestBody UpdatePasswordParam updatePasswordwordParam) {
        int status = umsAdminService.updatePassword(updatePasswordwordParam);
        if (status == 1) {
            return CommonResult.success("更新成功");
        } else if (status == -1) {
            return CommonResult.failed("参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("账号不存在");
        } else if (status == -3) {
            return CommonResult.failed("旧密码不正确");
        } else {
            return CommonResult.failed("更新失败");
        }
    }

    /**
     * 调用service层，公用update(Integer, UmsAdmin)，更新的方法应该是selective
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/updateStatus/{id}",method = RequestMethod.POST)
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam("status") int status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(id);
        umsAdmin.setStatus(status);
        int count = umsAdminService.update(id, umsAdmin);
        if (count > 0) {
            return CommonResult.success("更新成功");
        } else {
            return CommonResult.failed("更新失败");
        }
    }

    /**
     * 分配角色列表
     * 调用service层方法：先删除旧方法，再重新分配
     * @param adminId
     * @param roleIds
     * @return
     */
    @ApiOperation("更新用户角色信息")
    @RequestMapping(value = "/role/updateRole",method = RequestMethod.POST)
    public CommonResult  updateRole(@RequestParam("admin") Long adminId,
                                    @RequestParam("roleIds") List<Integer> roleIds) {
        int count = umsAdminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success();
        }
        return CommonResult.failed("更新角色列表失败");
    }

    /**
     * 涉及到多表联查，使用自定义Dao
     * @param adminId
     * @return
     */
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = umsAdminService.getRoleList(adminId);
        return CommonResult.success(roleList);
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
