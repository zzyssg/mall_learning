package com.zzy.malladmin.controller;

import cn.hutool.core.collection.CollUtil;
import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.PmsProductCategoryWithChildren;
import com.zzy.malladmin.dto.PmsProductDetail;
import com.zzy.malladmin.mbg.model.PmsProduct;
import com.zzy.malladmin.service.PmsPortalProductService;
import com.zzy.malladmin.service.PmsProductCategoryService;
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
@RequestMapping("/portal/product")
@Api(tags = "PmsPortalProductController")
@Tag(name = "PmsPortalProductController", description = "前台商品管理")
public class PmsPortalProductController {

    @Autowired
    PmsProductCategoryService productCategoryService;

    @Autowired
    PmsPortalProductService portalProductService;

    @ApiOperation("属性结构展示所有商品")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public CommonResult treeList() {
        List<PmsProductCategoryWithChildren> pmsProductCategoryWithChildren = productCategoryService.listWithChildren();
        if (!CollUtil.isEmpty(pmsProductCategoryWithChildren)) {
            return CommonResult.success(pmsProductCategoryWithChildren);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取前台商品详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public CommonResult detail(@PathVariable("id") Long id) {
        PmsProductDetail productDetail = portalProductService.getDetail(id);
        if (productDetail != null) {
            return CommonResult.success(productDetail);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("综合搜索、排序")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public CommonResult search(@RequestParam("keyword") String keyword,
                               @RequestParam("brandId") Long brandId,
                               @RequestParam("productCategoryId") Long productCategoryId,
                               @RequestParam("sort")Long sort,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        CommonPage<PmsProduct> productCommonPage = portalProductService.search(keyword, brandId, productCategoryId, sort, pageNum, pageSize);
        return CommonResult.success(productCommonPage);
    }
}