package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.common.CommonResultAssert;
import com.zzy.malladmin.dto.OmsOrderReturnApplyQueryParam;
import com.zzy.malladmin.dto.OmsUpdateStatusParam;
import com.zzy.malladmin.mbg.model.OmsOrderReturnApply;
import com.zzy.malladmin.service.OmsOrderReturnApplyService;
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
 * @create: 2023-11-26
 */
@RestController
@RequestMapping("/returnApply")
@Api(tags = "OmsOrderReturnApplyController")
@Tag(name = "OmsOrderReturnApplyController", description = "退货申请")
public class OmsOrderReturnApplyController {

    @Autowired
    OmsOrderReturnApplyService returnApplyService;

    @ApiOperation("获取退货申请详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult detail(@PathVariable("id") Long id) {
        OmsOrderReturnApply omsOrderReturnApply = returnApplyService.getItem(id);
        if (omsOrderReturnApply != null) {
            return CommonResult.success(omsOrderReturnApply);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("退货申请列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult list(@RequestBody OmsOrderReturnApplyQueryParam orderReturnApplyQueryParam,
                             @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize) {
        List<OmsOrderReturnApply> orderReturnApplyList = returnApplyService.list(orderReturnApplyQueryParam, pageNum, pageSize);
        if (orderReturnApplyList != null) {
            return CommonResult.success(CommonPage.restPage(orderReturnApplyList));
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量删除退货申请")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = returnApplyService.delete(ids);
        return CommonResultAssert.deal(count);
    }

    @ApiOperation("修改退货申请状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable("id") Long id,
                               @RequestBody OmsUpdateStatusParam updateStatusParam) {
        int count = returnApplyService.updateStatus(id, updateStatusParam);
        return CommonResultAssert.deal(count);
    }
}