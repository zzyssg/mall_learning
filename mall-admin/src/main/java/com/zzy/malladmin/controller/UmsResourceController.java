package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.component.DynamicSecurityMetadataSource;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.service.DynamicSecurityService;
import com.zzy.malladmin.service.UmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UmsResourceController
 * @Author ZZy
 * @Date 2023/10/15 18:13
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/resource")
@Api(tags = "UmsResourceController")
@Tag(name = "UmsResourceController",description = "资源控制器")
public class UmsResourceController {

    @Autowired
    UmsResourceService umsResourceService;

    @Autowired
    DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("新增资源")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody UmsResource umsResource) {
        int count = umsResourceService.create(umsResource);
        //动态权限资源控制 TODO
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success("新增资源成功");
        }
        return CommonResult.failed("新增资源失败");
    }

    @ApiOperation("更新后台资源")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id, @RequestBody UmsResource umsResource) {
        int count = umsResourceService.update(id, umsResource);
        //动态资源权限控制 TODO
        if (count == 1) {
            return CommonResult.success();
        }
        return CommonResult.failed("更新失败");
    }

    @ApiOperation("获取某个资源信息")
    @RequestMapping(value = "/getItem/{id}", method = RequestMethod.GET)
    public CommonResult getItem(@PathVariable("id") Long id) {
        UmsResource umsResource = umsResourceService.getResource(id);
        return CommonResult.success(umsResource);
    }


    @ApiOperation("根据ID删除资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable("id") Long id) {
        int count = umsResourceService.delete(id);
        //动态资源控制 TODO
        if (count > 0) {
            return CommonResult.success("删除成功");
        }
        return CommonResult.failed("删除资源失败");
    }

    @ApiOperation("分页模糊查询")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult list(@RequestParam(value = "name",required = false) String name,
                             @RequestParam(value = "location",required = false)String location,
                             @RequestParam(value = "category",required = false)Long category,
                             @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize) {
        List<UmsResource> umsResourceList =  umsResourceService.list(name,location,category, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(umsResourceList));
    }

    @ApiOperation("查询后台所有资源")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult listAll() {
        List<UmsResource> umsResourceList = umsResourceService.listAll();
        return CommonResult.success(umsResourceList);
    }


}
