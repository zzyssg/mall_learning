package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.mbg.model.UmsResourceCategory;
import com.zzy.malladmin.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.management.monitor.CounterMonitor;
import java.util.List;

/**
 * @ClassName UmsResourceCategoryController
 * @Author ZZy
 * @Date 2023/10/15 18:55
 * @Description
 * @Version 1.0
 */
@RequestMapping("/resourceCategory")
@Api(tags = "UmsResourceCategoryController")
@Tag(name = "UmsResourceCategoryController", description = "资源类别控制器")
public class UmsResourceCategoryController {

    @Autowired
    UmsResourceCategoryService umsResourceCategoryService;

    @ApiOperation("查询所有资源分类信息")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult listAll() {
        List<UmsResourceCategory> umsResourceCategories = umsResourceCategoryService.listAll();
        return CommonResult.success(umsResourceCategories);
    }

    @ApiOperation("新增资源类别")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody UmsResourceCategory umsResourceCategory) {
        int count = umsResourceCategoryService.add(umsResourceCategory);
        if (count == 1) {
            return CommonResult.success("新增成功");
        }
        return CommonResult.failed("新增失败");
    }

    @ApiOperation("根据id更新资源信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable("id") Long id, @RequestBody UmsResourceCategory umsResourceCategory) {
        int count = umsResourceCategoryService.update(id, umsResourceCategory);
        if (count == 1) {
            return CommonResult.success("更新成功");
        }
        return CommonResult.failed();
    }


    @ApiOperation("根据ID删除资源分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable("id") Long id) {
        int count = umsResourceCategoryService.delete(id);
        if (count > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
