package com.zzy.malladmin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.common.CommonResultAssert;
import com.zzy.malladmin.dto.CartProduct;
import com.zzy.malladmin.dto.CartPromotionItem;
import com.zzy.malladmin.mbg.model.OmsCartItem;
import com.zzy.malladmin.service.OmsCartItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-27
 */
@RestController
@RequestMapping("/cart")
@Api(tags = "OmsCartItemController")
@Tag(name = "OmsCartItemController", description = "购物车商品")
public class OmsCartItemController {

    @Autowired
    OmsCartItemService cartItemService;

    @ApiOperation("添加商品到购物车")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult add(@RequestBody OmsCartItem omsCartItem) {
        //以member、sku为基本单位  首先确定商品是否已经被添加进去了，如果已经添加进去了，则仅仅修改数据就行；否则添加进去；
        int count = cartItemService.add(omsCartItem);
        return CommonResultAssert.deal(count);
    }

    //TODO 未在请求参数里设置memberId，而是使用memberService从springSecurity中获取
    @ApiOperation("清空当前会员的购物车")
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public CommonResult clear() {
        //TODO 获取当前操作会员 memberId
        long memberId = 1;
        int count = cartItemService.clear(memberId);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("删除购物车指定商品")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        long memberId = 1;
        int count = cartItemService.delete(memberId, ids);
        return CommonResultAssert.deal(count);
    }


    @ApiOperation("获取购物车中指定商品的规格，用于重选规格")
    @RequestMapping(value = "/getProduct/{productId}", method = RequestMethod.GET)
    public CommonResult<CartProduct> getProduct(@PathVariable("productId") Long productId) {
        CartProduct cartProduct = cartItemService.getProduct(productId);
        if (cartProduct != null) {
            return CommonResult.success(cartProduct);
        } else {
            return CommonResult.failed();
        }

    }

    //TODO 没有传递会员ID，使用memberService查询当前人员
    @ApiOperation("获取当前会员的购物车列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list() {
        long memberId = 1;
        List<OmsCartItem> cartItemList = cartItemService.list(memberId);
        if (!CollUtil.isEmpty(cartItemList)) {
            return CommonResult.success(cartItemList);
        } else {
            return CommonResult.failed();
        }
    }

    //TODO 没有传递memberId，使用cartIds作为参数
    @ApiOperation("获取当前会员的购物车列表，包括促销活动等信息")
    @RequestMapping(value = "/list/promotion/", method = RequestMethod.GET)
    public CommonResult listPromotion(@RequestParam(required = false) List<Long> cartIds) {
        Long memberId = 1L;
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(memberId, cartIds);
        if (CollUtil.isEmpty(cartPromotionItemList)) {
            return CommonResult.failed();
        } else {
            return CommonResult.success(cartPromotionItemList);
        }

    }


    //没有使用memberId、productId、skuId，使用OmsCartItem
    @ApiOperation("修改购物车中物品的属性")
    @RequestMapping(value = "/update/attribute", method = RequestMethod.POST)
    public CommonResult updateAttribute(@RequestBody OmsCartItem cartItem) {
        int count = cartItemService.updateAttribute(cartItem);
        return CommonResultAssert.deal(count);
    }

    //没有使用memberId、productId、skuId，使用id和quantity
    @ApiOperation("修改购物车中物品的数量")
    @RequestMapping(value = "/update/quantity")
    public CommonResult updateQuantity(@RequestParam Long id,
                                       @RequestParam Integer quantity) {
        long memberId = 1;
        int count = cartItemService.updateQuantity(memberId,id,quantity);
        return CommonResultAssert.deal(count);
    }
}