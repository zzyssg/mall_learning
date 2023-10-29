package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.PmsProductParam;
import com.zzy.malladmin.dto.PmsProductQueryParam;
import com.zzy.malladmin.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PmsProduct
 * @Author ZZy
 * @Date 2023/10/26 22:22
 * @Description 商品控制器
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/product")
@Api(tags = "PmsProductController")
@Tag(name = "PmsProductController", description = "商品管理")
public class PmsProductController {


    @Autowired
    PmsProductService pmsProductService;

    /**
     * 新增商品
     *
     * @param pmsProductParam
     * @return
     */
    @ApiOperation("新增商品")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody PmsProductParam pmsProductParam) {
        int count = pmsProductService.create(pmsProductParam);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed("新增商品失败");
        }
    }

    /**
     * 根据商品ID获取【商品编辑信息】
     *
     * @param id
     * @return
     */
    @ApiOperation("根据商品ID更新商品信息")
    @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.GET)
    public CommonResult getUpdateInfo(@PathVariable("id") Long id) {
        return CommonResult.success();
    }

    /**
     * 根据商品ID更新商品
     *
     * @param id
     * @param pmsProductParam
     * @return
     */
    @ApiOperation("根据商品ID更新商品")
    @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.POST)
    public CommonResult updateInfo(@PathVariable("id") Long id,
                                   @RequestBody PmsProductParam pmsProductParam) {
        return CommonResult.success();
    }

    /**
     * 分页查询商品 TODO 修改keyword参数为 QueryParam
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("分页查询商品列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult list(@RequestBody PmsProductQueryParam pmsProductQueryParam,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize) {
        return CommonResult.success();

    }

    /**
     * 根据商品名称或者货号模糊查询
     *
     * @param keyword
     * @return
     */
    @ApiOperation("根据商品名称或者货号模糊查询")
    @RequestMapping(value = "/simpleList", method = RequestMethod.GET)
    public CommonResult simpleList(@RequestParam("keyword") String keyword) {
        return CommonResult.success();
    }


    /**
     * 批量修改删除状态
     *
     * @param ids
     * @param deleteStatuses
     * @return
     */
    @ApiOperation("批量更新删除状态")
    @RequestMapping(value = "/updateDeleteStatus", method = RequestMethod.POST)
    public CommonResult deleteStatus(@RequestParam("ids") List<Long> ids,
                                     @RequestParam("deleteStatuses") List<Integer> deleteStatuses) {
        return CommonResult.success();
    }

    /**
     * 批量修改新品状态
     *
     * @param ids
     * @param newStatuses
     * @return
     */
    @ApiOperation("批量修改新品状态")
    @RequestMapping(value = "/update/newStatus", method = RequestMethod.POST)
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatuses") List<Integer> newStatuses) {
        return CommonResult.success();
    }


    /**
     * 批量修改发布状态
     *
     * @param ids
     * @param publishStatuses
     * @return
     */
    @ApiOperation("批量修改发布状态")
    @RequestMapping(value = "/update/publishStatus", method = RequestMethod.POST)
    public CommonResult publishStatus(@RequestParam("ids") List<Long> ids,
                                      @RequestParam("publistStatuses") List<Integer> publishStatuses) {
        return CommonResult.success();

    }

    /**
     * 批量修改是否推荐
     *
     * @param ids
     * @param recommmendStatuses
     * @return
     */
    @ApiOperation("批量修改推荐状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    public CommonResult recommendStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("recommendStatuses") List<Integer> recommmendStatuses) {
        return CommonResult.success();
    }


    /**
     * 批量修改审核状态
     *
     * @param ids
     * @param verifyStatuses
     * @return
     */
    @ApiOperation("批量修改审核状态")
    @RequestMapping(value = "/update/verify", method = RequestMethod.POST)
    public CommonResult verifyStatus(@RequestParam("ids") List<Long> ids,
                                     @RequestParam("verifyStatuses") List<Integer> verifyStatuses) {
        return CommonResult.success();
    }


}
