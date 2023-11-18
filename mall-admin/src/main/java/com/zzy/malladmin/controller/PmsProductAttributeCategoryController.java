package com.zzy.malladmin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.PmsProductAttributeCategoryChildren;
import com.zzy.malladmin.mbg.model.PmsProductAttributeCategory;
import com.zzy.malladmin.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PmsProductAttributeCategoryController
 * @Author ZZy
 * @Date 2023/11/15 10:53
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/productAttribute/category")
@Api(tags = "PmsProductAttributeCategoryController")
@Tag(name = "PmsProductAttributeCategoryController", description = "商品属性分类")
public class PmsProductAttributeCategoryController {

    @Autowired
    public PmsProductAttributeCategoryService attributeCategoryService;


    @ApiOperation("获取属性分类信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult get(@PathVariable("id") Long id) {
        PmsProductAttributeCategory attributeCategory = attributeCategoryService.getById(id);
        if (attributeCategory != null) {
            return CommonResult.success(attributeCategory);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("创建属性分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody PmsProductAttributeCategory attributeCategory) {
        //tb_product_attribute_category
        int count = attributeCategoryService.add(attributeCategory);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }

    }

    @ApiOperation("删除属性分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable("id") Long id) {
        int count = attributeCategoryService.deleteById(id);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("分页获取所有属性分类")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<List<PmsProductAttributeCategory>>> list() {
        List<PmsProductAttributeCategory> pmsProductAttributeCategories = attributeCategoryService.listAll();
        if (!CollUtil.isEmpty(pmsProductAttributeCategories)) {
            return CommonResult.success(JSONUtil.toJsonStr(CommonPage.restPage(pmsProductAttributeCategories)));
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取所有属性分类及下级属性")
    @RequestMapping(value = "/list/withChildren", method = RequestMethod.GET)
    public CommonResult listWithChildren() {
        List<PmsProductAttributeCategory> test = attributeCategoryService.test();
        List<PmsProductAttributeCategoryChildren> attributeCategoryNodeList = attributeCategoryService.listWithChildren();
        if (!CollUtil.isEmpty(attributeCategoryNodeList)) {
            return CommonResult.success(attributeCategoryNodeList);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改商品分类")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable("id") Long id, @RequestBody PmsProductAttributeCategory attributeCategory) {
        int count = attributeCategoryService.updateById(id, attributeCategory);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

}
