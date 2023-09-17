package com.zzy.boot_bootis.controller;

import com.zzy.boot_bootis.common.api.CommonResult;
import com.zzy.boot_bootis.dto.OrderParam;
import com.zzy.boot_bootis.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OmsPortalOrderController
 * @Author ZZy
 * @Date 2023/9/18 0:14
 * @Description
 * @Version 1.0
 */
@Api(value = "OmsPortalOrderController")
@Tag(name = "OmsPortalOrderController",description = "订单取消")
@RestController
@RequestMapping( "/order")
public class OmsPortalOrderController {

    @Autowired
    OmsPortalOrderService orderService;

    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult createOrder(@RequestBody OrderParam orderParam) {
        CommonResult commonResult = orderService.generateOrder(orderParam);
        return commonResult;
    }

}
