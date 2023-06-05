package com.zzy.malllearningmybatis.controller;

import com.zzy.malllearningmybatis.common.api.CommonPage;
import com.zzy.malllearningmybatis.common.api.CommonResult;

import com.zzy.malllearningmybatis.dto.UmsAdminRoleCountDto;
import com.zzy.malllearningmybatis.mbg.model.UmsAdmin;
import com.zzy.malllearningmybatis.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "UmsAdminController")
@Tag(name = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
    @Autowired
    UmsAdminService umsAdminService;


    @GetMapping("/listAll")
    @ApiOperation("分页查询所有")
    public CommonResult<List<UmsAdmin>> listAll(@RequestParam(value = "pageNum", defaultValue = "1")
                                                @ApiParam("页码") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "5")
                                                @ApiParam("行数") Integer pageSize
    ) {
        List<UmsAdmin> umsAdmins = umsAdminService.selectPage(pageNum, pageSize);
        return CommonResult.success(umsAdmins);

    }

    @ApiOperation("根据名字和状态分页查询")
    @GetMapping("/listByNameAndStatus")
    public CommonResult<List<UmsAdmin>> listByNNamesAndStatus2(
            @RequestParam(value = "pageNum", defaultValue = "1") @ApiParam("页数") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") @ApiParam("每页行数") Integer pageSize,
            @RequestParam(required = false) @ApiParam("用户名称") String username,
            @RequestParam @ApiParam(value = "状态列表", required = true) List<Integer> statusList
    ) {
        List<UmsAdmin> umsAdmins = umsAdminService.listByNameAndStatus(pageNum, pageSize, username, statusList);
        return CommonResult.success(umsAdmins);
    }

    @ApiOperation("分页条件查询")
    @GetMapping("/list")
    public CommonResult<CommonPage<UmsAdmin>> listByNNamesAndStatus(@RequestParam(value = "pageNum", defaultValue = "1")
                                                                    @ApiParam("页码") Integer pageNum,
                                                                    @RequestParam(value = "pageSize", defaultValue = "5")
                                                                    @ApiParam("每页数量") Integer pageSize,
                                                                    @RequestParam(required = false)
                                                                    @ApiParam("用户名") String username,
                                                                    @RequestParam
                                                                    @ApiParam(value = "状态", required = true) List<Integer> statusList) {
        List<UmsAdmin> list = umsAdminService.listByNameAndStatus(pageNum, pageSize, username, statusList);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @GetMapping("/subList")
    @ApiOperation("子查询-roleId")
    public CommonResult<List<UmsAdmin>> subList(@RequestParam("roleId") @ApiParam(value = "角色ID") Long roleId) {
        List<UmsAdmin> umsAdmins = umsAdminService.subList(roleId);
        return CommonResult.success(umsAdmins);
    }

    @GetMapping("/listRoleCount")
    @ApiOperation("查询角色及人数")
    public CommonResult<List<UmsAdminRoleCountDto>> listRoleCount() {
        List<UmsAdminRoleCountDto> umsAdminRoleCountDtos = umsAdminService.listRoleCount();
        return CommonResult.success(umsAdminRoleCountDtos);
    }

    @GetMapping("/deleteByName")
    @ApiOperation("根据名字删除")
    public CommonResult<Integer> deteleByName(String name) {
        int i = umsAdminService.deleteUserByName(name);
        return CommonResult.success(i);
    }

    @GetMapping("/deleteByNameAndNickName")
    @ApiOperation("根据名字和昵称删除")
    public CommonResult<Integer> deleteByNameAndNickName(String name,String nickName) {
        int i = umsAdminService.deleteUserByNameAndNickName(name,nickName);
        return CommonResult.success(i);
    }
}
