package com.zzy.malladmin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.PmsProductAttributeInfo;
import com.zzy.malladmin.dto.PmsProductAttributeParam;
import com.zzy.malladmin.mbg.model.PmsProductAttribute;
import com.zzy.malladmin.service.PmsProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PmsProductAttributeController
 * @Author ZZy
 * @Date 2023/11/16 11:21
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/attribute")
@Api(tags = "PmsProductAttributeController")
@Tag(name = "PmsProductAttributeController", description = "属性管理")
public class PmsProductAttributeController {

    @Autowired
    PmsProductAttributeService attributeService;

    @ApiOperation("根据单个商品属性")
    @RequestMapping("/{id}")
    public CommonResult get(@PathVariable("id") Long id) {
        PmsProductAttribute attribute = (PmsProductAttribute) attributeService.getByAttributeId(id);
        if (attribute != null) {
            return CommonResult.success(attribute);
        } else {
            //TODO 如何返回
            return CommonResult.failed("未查到此属性");
        }
    }

    @ApiOperation("根据商品分类id查询属性")
    @RequestMapping("/list/{id}")
    public CommonResult list(@PathVariable("id") Long id,
                                  @RequestParam("type")Integer type,
                                  @RequestParam("pageNum")Integer pageNum,
                                  @RequestParam("pageSize")Integer pageSize) {
        List<PmsProductAttribute> attributeList = attributeService.getAttributeListByCategoryId(id,type,pageNum,pageSize);
        if (!CollUtil.isEmpty(attributeList)) {
            return CommonResult.success(CommonPage.restPage(attributeList));
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation("新增属性")
    @RequestMapping("/create")
    public CommonResult create(@RequestBody PmsProductAttributeParam attributeParam) {
        int count = attributeService.add(attributeParam);
        if (count > 0) {
            return CommonResult.success("创建成功");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除属性")
    @RequestMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("ids") List<Long> ids) {
        int count = attributeService.delete(ids);
        if (count > 0) {
            return CommonResult.success("删除属性成功");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据商品分类，查询属性列表")
    @RequestMapping(value = "/attribute/{cid}",method = RequestMethod.GET)
    public CommonResult attribute(@PathVariable("cid") Long cid) {
        List<PmsProductAttributeInfo> attributeInfo = attributeService.getByAttributeId(cid);
        if (!CollUtil.isEmpty(attributeInfo)) {
            return CommonResult.success(attributeInfo);
        } else {
            return CommonResult.failed();
        }

    }

    @ApiOperation("修改商品属性信息")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@PathVariable("id") Long id,
                               @RequestBody PmsProductAttributeParam attributeParam) {
        int count = attributeService.update(id, attributeParam);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }

    }

}
