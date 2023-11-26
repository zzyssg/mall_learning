package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.common.CommonResultAssert;
import com.zzy.malladmin.dto.*;
import com.zzy.malladmin.mbg.model.OmsOrder;
import com.zzy.malladmin.service.OmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: mall_learning
 * @description: 订单管理：注意操作时，记录操作日志OrderOperateHistory
 * @author: zzy
 * @create: 2023-11-26
 */
@RestController
@RequestMapping("/order")
@Api(tags = "OmsOrderController")
@Tag(name = "OmsOrderController",description = "订单管理")
public class OmsOrderController {

    @Autowired
    OmsOrderService orderService;

    @ApiOperation("查询某个订单")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CommonResult detail(@PathVariable("id") Long id) {
        OmsOrderDetail orderDetail = orderService.detail(id);
        if (orderDetail != null) {
            return CommonResult.success(orderDetail);
        } else {
            return CommonResult.failed("未查询到数据");
        }
    }

    @ApiOperation("创建新订单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody OmsOrderParam omsOrderParam) {
        int count = orderService.insert(omsOrderParam);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("删除订单")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable("ids") List<Long> ids) {
        int count = orderService.delete(ids);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("更新订单")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable("id") Long id, @RequestBody OrderUpdateParam orderUpdateParam) {
        int count = orderService.update(id, orderUpdateParam);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("批量关闭订单")
    @RequestMapping(value = "/update/close",method = RequestMethod.GET)
    public CommonResult updateClose(@RequestParam("ids") List<Long> ids,
                              @RequestParam("note") String note) {
        int count = orderService.updateClose(ids, note);
        return CommonResultAssert.deal(count);
    }


    //TODO 注意 批量在sql中foreach的写法，注意拼接
    @ApiOperation("批量发货")
    @RequestMapping(value = "/update/delivery", method = RequestMethod.POST)
    public CommonResult delivery(@RequestBody List<OrderDeliveryParam> orderDeliveryParamList) {
        int count = orderService.delivery(orderDeliveryParamList);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("修改订单费用信息")
    @RequestMapping(value = "value = /update/moneyInfo", method = RequestMethod.POST)
    public CommonResult updateMoneyInfo(@RequestBody OrderMoneyInfoParam orderMoneyInfoParam) {
        int num = orderService.updateMoneyInfo(orderMoneyInfoParam);
        return CommonResultAssert.deal(num);
    }

    @ApiOperation("备注订单")
    @RequestMapping(value = "/update/note", method = RequestMethod.GET)
    public CommonResult updateNode(@RequestParam("id") Long id,
                                   @RequestParam("note") String note,
                                   @RequestParam("status") Integer status) {
        int count = orderService.updateNote(id, note, status);
        return CommonResultAssert.deal(count);
    }


    @ApiOperation("修改收货人信息")
    @RequestMapping(value = "/update/receiverInfo", method = RequestMethod.POST)
    public CommonResult updateReceiverInfo(@RequestBody OrderReceiverInfoParam orderReceiverInfoParam) {
        int count = orderService.updateReceiverInfo(orderReceiverInfoParam);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("查询订单")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public CommonResult<CommonPage<OmsOrder>> search(@RequestBody OrderSearchParam orderSearchParam,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        List<OmsOrder> omsOrderList = orderService.search(orderSearchParam, pageNum, pageSize);
        if (omsOrderList != null) {
            return CommonResult.success(CommonPage.restPage(omsOrderList));
        } else {
            return CommonResult.failed();
        }
    }

//    @ApiOperation("创建新订单")
//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//

}