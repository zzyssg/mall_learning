package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.mbg.model.UmsMenu;
import com.zzy.malladmin.mbg.model.UmsRole;
import com.zzy.malladmin.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.monitor.CounterMonitor;
import java.util.List;

/**
 * @ClassName UmsRoleController
 * @Author ZZy
 * @Date 2023/10/4 13:45
 * @Description
 * @Version 1.0
 */
@RestController(value = "/role")
@Api(tags = "UmsRoleController")
@Tag(name = "UmsRoleController", description = "用户角色")
public class UmsRoleController {

    @Autowired
    UmsRoleService umsRoleService;

    @RequestMapping(value = "/searchMenuById", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id查询菜单")
    public CommonResult searchMenuById(Long adminId) {
        List<UmsMenu> umsMenuList = umsRoleService.searchMenuById(adminId);
        return CommonResult.success(umsMenuList);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "展示角色列表")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "keyword", value = "关键字"),
                    @ApiImplicitParam(name = "pageNum", value = "页码"),
                    @ApiImplicitParam(name = "pageSize", value = "每页条数")}
    )
    public CommonResult<CommonPage<UmsRole>> list(@RequestParam(value = "keyword") String keyword,
                                                  @RequestParam(value = "pageNum") Integer pageNum,
                                                  @RequestParam(value = "pageSize") Integer pageSize) {

        List<UmsRole> umsRoleList = umsRoleService.list(keyword, pageNum, pageSize);
        CommonResult<CommonPage<UmsRole>> success = CommonResult.success(CommonPage.restPage(umsRoleList));
        return success;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加角色")
    public CommonResult add(@RequestBody UmsRole umsRole) {
        int count = umsRoleService.insert(umsRole);
        if (count == 1) {
            return CommonResult.success("添加角色成功");

        } else {
            return CommonResult.failed("添加失败");
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新角色信息")
    public CommonResult update(@RequestBody UmsRole umsRole) {
        int count = umsRoleService.update(umsRole);
        if (count > 0) {
            return CommonResult.success("更新成功");
        } else {
            return CommonResult.failed("更新失败");
        }
    }

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.GET)
    @ApiOperation(value = "批量删除角色")
    public CommonResult deleteBatch(@RequestParam("ids") List<Integer> ids) {
        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除用户信息")
    public CommonResult delete(@PathVariable("id") Long id) {
        umsRoleService.delete(id);
        return CommonResult.success("删除成功");
    }


}
