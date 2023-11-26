package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.common.CommonResultAssert;
import com.zzy.malladmin.dto.OrderSettingParam;
import com.zzy.malladmin.mbg.model.OmsOrderSetting;
import com.zzy.malladmin.service.OmsOrderSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: mall_learning
 * @description: 订单设置管理-超时时间
 * @author: zzy
 * @create: 2023-11-26
 */
@RestController
@RequestMapping("/orderSetting")
@Api(tags = "OmsOrderSettingController")
@Tag(name = "OmsOrderSettingController", description = "超时时间—订单设置管理")
public class OmsOrderSettingController {

    @Autowired
    OmsOrderSettingService orderSettingService;

    @ApiOperation("查询订单设置")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult detail(@PathVariable("id") Long id) {
        OmsOrderSetting item = orderSettingService.getItem(id);
        if (item != null) {
            return CommonResult.success(item);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新订单设置")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult updateSetting(@PathVariable("id") Long id,
                                      @RequestBody OrderSettingParam orderSettingParam) {
        int count = orderSettingService.update(id, orderSettingParam);
        return CommonResultAssert.deal(count);
    }
}