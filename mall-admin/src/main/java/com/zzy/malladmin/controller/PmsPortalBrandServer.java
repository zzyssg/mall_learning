package com.zzy.malladmin.controller;

import cn.hutool.core.collection.CollUtil;
import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.mbg.model.PmsBrand;
import com.zzy.malladmin.mbg.model.PmsProduct;
import com.zzy.malladmin.service.PmsPortalBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: mall_learning-main
 * @description: 前台商品管理
 * @author: zzy
 * @create: 2023-11-19
 */
@RestController
@RequestMapping("/portal/brand")
@Api(tags = "PmsPortalBrandServer")
@Tag(name = "PmsPortalBrandServer", description = "前台商品管理")
public class PmsPortalBrandServer {

    @Autowired
    PmsPortalBrandService portalBrandService;

    @ApiOperation("获取品牌详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public CommonResult detail(@PathVariable("id") Long id) {
        PmsBrand brand = portalBrandService.detail(id);
        if (brand != null) {
            return CommonResult.success(brand);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("分页获取品牌相关产品")
    @RequestMapping(value = "/productList", method = RequestMethod.GET)
    public CommonResult productList(@RequestParam("brandId") Long brandId,
                                    @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                    @RequestParam(value = "pageSize",required = false,defaultValue = "5")Integer pageSize) {
        CommonPage<PmsProduct> productCommonPage = portalBrandService.productList(brandId, pageNum, pageSize);
        if (productCommonPage != null) {
            return CommonResult.success(productCommonPage);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("分页获取推荐产品")
    @RequestMapping(value = "/recommendList", method = RequestMethod.GET)
    public CommonResult recommendList(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                      @RequestParam(value = "pageSize",required = false,defaultValue = "5")Integer pageSize) {
        List<PmsBrand> pmsBrands = portalBrandService.recommendList(pageNum, pageSize);
        if (!CollUtil.isEmpty(pmsBrands)) {
            return CommonResult.success(pmsBrands);
        } else {
            return CommonResult.failed();
        }
    }
}