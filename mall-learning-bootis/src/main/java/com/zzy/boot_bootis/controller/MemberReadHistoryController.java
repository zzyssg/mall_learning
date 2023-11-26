package com.zzy.boot_bootis.controller;

import com.zzy.boot_bootis.common.api.CommonResult;
import com.zzy.boot_bootis.nosql.mongodb.document.MemberReadHistory;
import com.zzy.boot_bootis.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName MemeberReadHistoryController
 * @Author ZZy
 * @Date 2023/9/16 21:25
 * @Description
 * @Version 1.0
 */
@RestController
@Api(tags = "MemberReadHistoryController")
@Tag(name = "MemberReadHistoryController", description = "会员阅读记录")
@RequestMapping("/member")
public class MemberReadHistoryController {

    @Autowired
    MemberReadHistoryService memberReadHistoryService;

    @ApiOperation(value = "新增阅读记录")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody MemberReadHistory memberReadHistory) {
        int i = memberReadHistoryService.create(memberReadHistory);
        if (i == 1) {
            return CommonResult.success(i);
        } else {
            return CommonResult.failed("新增阅读记录失败");
        }
    }

    @ApiOperation(value = "批量删除阅读记录")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public CommonResult deleteBatch(@RequestParam("ids") List<String> ids){
        int count = memberReadHistoryService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "展示浏览记录")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(Long memberId) {

        List<MemberReadHistory> memberReadHistoryList = memberReadHistoryService.list(memberId);
        return CommonResult.success(memberReadHistoryList);
    }

}
