package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.UmsMenuNode;
import com.zzy.malladmin.mbg.mapper.UmsMenuMapper;
import com.zzy.malladmin.mbg.model.UmsMenu;
import com.zzy.malladmin.service.UmsMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UmsMenuController
 * @Author ZZy
 * @Date 2023/10/15 15:05
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/menu")
public class UmsMenuController {

    @Autowired
    UmsMenuService umsMenuService;

    //新增菜单 TODO  新增菜单和role、admin挂钩吗？还是全局的？
    @ApiOperation("新增菜单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody UmsMenu umsMenu) {
        int count = umsMenuService.create(umsMenu);
        if (count > 0) {
            return CommonResult.success("新增菜单成功");
        }
        return CommonResult.failed("新增菜单失败");
    }
    //修改某个菜单
    @ApiOperation("修改后台菜单")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id, @RequestBody UmsMenu umsMenu) {
        int count = umsMenuService.update(id, umsMenu);
        if (count == 1) {
            return CommonResult.success("更新成功");
        }
        return CommonResult.failed("更新失败");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult getMenu(@PathVariable Long id) {
        UmsMenu umsMenu = umsMenuService.getMenu(id);
        return CommonResult.success(umsMenu);
    }

    //删除菜单
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation("根据id删除菜单")
    public CommonResult delete(@PathVariable Long id) {
        int count = umsMenuService.delete(id);
        if (count == 1) {
            return CommonResult.success("删除成功");
        }
        return CommonResult.failed("删除失败");
    }


    //查找菜单
    @ApiOperation("查询所有菜单")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public CommonResult treeList() {
        List<UmsMenuNode> umsMenuNodeList = umsMenuService.treeList();
        return CommonResult.success(CommonPage.restPage(umsMenuNodeList));
    }

    //查找某个父id下的菜单
    @ApiOperation("查询某个菜单的直接下级菜单")
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    public CommonResult listDown(@PathVariable Long parentId,
                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<UmsMenu> menuList = umsMenuService.list(parentId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(menuList));

    }

    //更新显示状态
    @ApiOperation("更新是否隐藏")
    @RequestMapping(value = "/updateHidder/{id}", method = RequestMethod.POST)
    public CommonResult updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
        int count = umsMenuService.updateHidden(id, hidden);
        if (count == 1) {
            return CommonResult.success("更新成功");
        }
        return CommonResult.failed("更新隐藏属性失败");
    }


}
