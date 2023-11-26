package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.mbg.model.UmsMenu;
import com.zzy.malladmin.mbg.model.UmsResource;
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
@RestController
@RequestMapping(value = "/role")
@Api(tags = "UmsRoleController")
@Tag(name = "UmsRoleController", description = "用户角色")
public class UmsRoleController {

    @Autowired
    UmsRoleService umsRoleService;

    /**
     * 根据传递来的信息，新增角色
     * @param umsRole
     * @return
     */
    @ApiOperation("新增角色")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody UmsRole umsRole) {
        int count = umsRoleService.add(umsRole);
        if (count == 1) {
            return CommonResult.success("新增角色成功");
        }
        return CommonResult.failed("创建失败");
    }



    @RequestMapping(value = "/searchMenuById", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户id查询菜单")
    public CommonResult searchMenuById(Long adminId) {
        List<UmsMenu> umsMenuList = umsRoleService.searchMenuById(adminId);
        return CommonResult.success(umsMenuList);
    }

    /**
     * 获取所有角色信息
     * @return
     */
    public CommonResult listAll() {
        List<UmsRole> roleList = umsRoleService.listAll();
        return CommonResult.success(roleList);
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

    /**
     * 更新角色状态 可以调用service层的update
     * @param id
     * @param umsRole
     * @return
     */
    @ApiOperation("更新角色状态信息")
    @RequestMapping(value = "/updateStatus/{id}",method = RequestMethod.POST)
    public CommonResult updateStatus(@PathVariable Long id, @RequestBody UmsRole umsRole) {
        int count = umsRoleService.update(id, umsRole);
        if (count == 1) {
            return CommonResult.success("更新状态成功");
        }
        return CommonResult.failed("更新状态失败");
    }

    /**
     * 获取某个角色的id
     * @param roleId
     * @return
     */
    @ApiOperation("获取某个角色的菜单列表")
    @RequestMapping(value = "/listMenu/{roleId}",method = RequestMethod.GET)
    public CommonResult listMenu(@PathVariable Long roleId) {
        List<UmsMenu> menuList = umsRoleService.listUmsMenu(roleId);
        return CommonResult.success(menuList);

    }

    /**
     * 根据角色id获取角色的资源（接口）列表
     * @param roleId
     * @return
     */
    @ApiOperation("获取某个角色的资源列表")
    @RequestMapping(value = "/listResource/{roleId}", method = RequestMethod.GET)
    public CommonResult listResource(@PathVariable Long roleId) {
        List<UmsResource> umsResourceList = umsRoleService.listUmsResource(roleId);
        return CommonResult.success(umsResourceList);
    }

    /**
     * 类似admin，给角色分配权限，先删除原来绑定的权限，再重新设置权限
     * @param roleId
     * @param menus
     * @return
     */
    @ApiOperation("给角色分配菜单")
    @RequestMapping(value = "/allocMenu/{roleId}",method = RequestMethod.POST)
    public CommonResult allocMenu(@PathVariable Long roleId, @RequestParam List<Integer> menus) {
        umsRoleService.allocateMenu(roleId, menus);
        return CommonResult.success("分配成功");
    }

    @ApiOperation("分配资源")
    @RequestMapping(value = "/allocResource/{roleId}", method = RequestMethod.POST)
    public CommonResult allocResource(@PathVariable Long roleId, @RequestParam List<Long> resources) {
        umsRoleService.allocateResource(roleId,resources);
        return CommonResult.success("分配资源成功");
    }


    /**
     * 根据id和用户信息umsRole更新角色
     * @param umsRole
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "更新角色信息")
    public CommonResult update(@PathVariable Long id, @RequestBody UmsRole umsRole) {
        int count = umsRoleService.update(id, umsRole);
        if (count > 0) {
            return CommonResult.success("更新成功");
        } else {
            return CommonResult.failed("更新失败");
        }
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


    /**
     * 批量删除角色信息，【注意】：仅删除角色表本表信息即可，无需删除关联表
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.GET)
    @ApiOperation(value = "批量删除角色")
    public CommonResult deleteBatch(@RequestParam("ids") List<Long> ids) {
        int count = umsRoleService.deleteBatch(ids);
        if (count > 0) {
            return CommonResult.success("删除成功");
        } else if (count == -1) {
            return CommonResult.failed("删除角色列表为空");
        } else {
            return CommonResult.failed("删除失败");
        }

    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除用户信息")
    public CommonResult delete(@PathVariable("id") Long id) {
        umsRoleService.delete(id);
        return CommonResult.success("删除成功");
    }


}
