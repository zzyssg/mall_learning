package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.common.CommonResultAssert;
import com.zzy.malladmin.dto.OmsOrderReturnReasonParam;
import com.zzy.malladmin.mbg.model.OmsOrderReturnApply;
import com.zzy.malladmin.mbg.model.OmsOrderReturnReason;
import com.zzy.malladmin.service.OmsOrderReturnReasonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: mall_learning
 * @description: 退款理由
 * @author: zzy
 * @create: 2023-11-27
 */
@RestController
@RequestMapping("/returnReason")
@Api(tags = "OmsOrderReturnReasonController")
@Tag(name = " OmsOrderReturnReasonController", description = "退货原因")
public class OmsOrderReturnReasonController {

    @Autowired
    OmsOrderReturnReasonService returnReasonService;

    @ApiOperation("新增退货原因")
    @RequestMapping("/create")
    public CommonResult create(@RequestBody OmsOrderReturnReasonParam returnReasonParam) {
        int count = returnReasonService.add(returnReasonParam);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("查看某个退货原因")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult query(@PathVariable("id") Long id) {
        OmsOrderReturnReason returnReason = returnReasonService.getItem(id);
        if (returnReason != null) {
            return CommonResult.success(returnReason);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("查询退货原因")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<OmsOrderReturnReason> returnReasonList = returnReasonService.list(pageNum, pageSize);
        if (returnReasonList != null) {
            return CommonResult.success(CommonPage.restPage(returnReasonList));
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量删除原因")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = returnReasonService.delete(ids);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("修改某项退货原因")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable("id") Long id,
                               @RequestBody OmsOrderReturnReason returnReason) {
        int count = returnReasonService.update(id, returnReason);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("批量修改启用状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.GET)
    public CommonResult updateSatatus(@RequestParam("ids") List<Long> ids,
                                      @RequestParam("status") Integer status) {
        int count = returnReasonService.updateStatus(ids, status);
        return CommonResultAssert.deal(count);
    }


}