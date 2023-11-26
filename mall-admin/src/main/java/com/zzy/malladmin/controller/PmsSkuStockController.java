package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.mbg.model.PmsSkuStock;
import com.zzy.malladmin.service.PmsSkuStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PmsSkuStockController
 * @Author ZZy
 * @Date 2023/11/12 15:58
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/sku")
@Api(tags = "PmsSkuStockController")
@Tag(name = "PmsSkuStockController",description = "Sku模块管理")
public class PmsSkuStockController {

    @Autowired
    PmsSkuStockService pmsSkuStockService;

    /**
     * 根据商品ID查询库存
     *
     * @param productId
     * @return
     */
    @ApiOperation(value = "根据商品ID查询Sku库存")
    @RequestMapping(value = "/sku/{pid}",method = RequestMethod.GET)
    public CommonResult getSkuInfo(@PathVariable("pid") Long productId) {
        List<PmsSkuStock> skuStockInfo = pmsSkuStockService.getSkuStockInfo(productId);
        if (skuStockInfo != null) {
            return CommonResult.success(skuStockInfo);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 根据商品ID和库存信息，更新库存
     *
     * @param productId
     * @param skuStockParam
     * @return
     */
    @ApiOperation(value = "更新库存信息")
    @RequestMapping(value = "/update/{pid}", method = RequestMethod.POST)
    public CommonResult updateSkuInfo(@PathVariable("pid") Long productId,
                                      @RequestBody List<PmsSkuStock> skuStockParam) {
        int count = pmsSkuStockService.updateSkuInfo(productId, skuStockParam);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }


}
