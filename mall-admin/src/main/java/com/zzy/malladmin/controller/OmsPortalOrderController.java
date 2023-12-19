package com.zzy.malladmin.controller;

import cn.hutool.core.collection.CollUtil;
import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.common.CommonResultAssert;
import com.zzy.malladmin.dto.ConfirmOrderResult;
import com.zzy.malladmin.dto.OmsOrderDetail;
import com.zzy.malladmin.dto.OmsOrderParam;
import com.zzy.malladmin.mbg.model.OmsOrder;
import com.zzy.malladmin.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: mall_learning
 * @description: 订单管理
 * @author: zzy
 * @create: 2023-12-09
 */
@RestController
@RequestMapping("/order")
@Api(tags = "OmsPortalOrderController")
@Tag(name = "OmsPortalOrderController", description = "订单管理：下订单、根据购物车生成确认信息等")
public class OmsPortalOrderController {

    @Autowired
    OmsPortalOrderService portalOrderService;

    @ApiOperation("取消超时订单")
    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET)
    public CommonResult cancel(@PathVariable("id") Long id) {
        int count = portalOrderService.cancel(id);
        return CommonResultAssert.deal(count);
    }

    //TODO 这种没有参数的请求，使用GET和使用POST的区别是什么？
    @ApiOperation("自动取消超时订单")
    @RequestMapping(value = "/cancelTimeOut", method = RequestMethod.POST)
    public CommonResult cancelTimeOut() {
        portalOrderService.cancelTimeOut();
        return CommonResult.success();
    }

    @ApiOperation("用户取消订单")
    @RequestMapping(value = "/cancel/userDo", method = RequestMethod.POST)
    public CommonResult cancelUserDo(@RequestParam("orderId") Long orderId) {
        portalOrderService.cancelUserDo(orderId);
        return CommonResult.success();
    }


    @ApiOperation("用户确认收货")
    @RequestMapping(value = "/confirmReceiveOrder", method = RequestMethod.POST)
    public CommonResult confirmReceiveOrder(@RequestParam("orderId") Long orderId) {
        int count = portalOrderService.confirm(orderId);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("用户删除订单")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("orderId") Long orderId) {
        int count = portalOrderService.delete(orderId);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("根据ID获取订单详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult detail(@PathVariable("id") Long id) {
        OmsOrderDetail orderDetail = portalOrderService.detail(id);
        if (orderDetail != null) {
            return CommonResult.success(orderDetail);
        } else {
            return CommonResult.failed("未查到此订单");
        }
    }


    @ApiOperation("根据购物车信息生成确认单")
    @RequestMapping(value = "/generateConfirmOrder", method = RequestMethod.POST)
    public CommonResult<ConfirmOrderResult> generateConfirmOrder(@RequestBody List<Long> cartIds) {
        ConfirmOrderResult confirmOrderResult = portalOrderService.generateConfirmOrder(cartIds);
        if (confirmOrderResult != null) {
            return CommonResult.success(confirmOrderResult);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    public CommonResult generateOrder(@RequestBody OmsOrderParam omsOrderParam) {
        Map<String, Object> result = portalOrderService.generateOrder(omsOrderParam);
        if (result != null) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed("下单失败！");
        }

    }

    @ApiOperation("按状态分页获取用户订单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "status") Integer status) {
        List<OmsOrder> orderList = portalOrderService.list(pageNum, pageSize, status);
        if (CollUtil.isEmpty(orderList)) {
            return CommonResult.failed("订单为空");
        } else {
            return CommonResult.success(CommonPage.restPage(orderList));
        }
    }

    @ApiOperation("用户支付成功的回调")
    @RequestMapping(value = "/paySuccessCallback", method = RequestMethod.POST)
    public CommonResult paySuccessCallback(@RequestParam("orderId") Long orderId, @RequestParam("payType") Integer payType) {
        Integer result = portalOrderService.paySuccessCallback(orderId, payType);
        if (result > 0) {
            return CommonResult.success("支付成功");
        } else {
            return CommonResult.failed("支付失败");
        }
    }
}