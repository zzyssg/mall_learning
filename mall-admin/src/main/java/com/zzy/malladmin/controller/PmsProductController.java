package com.zzy.malladmin.controller;

import cn.hutool.core.collection.CollUtil;
import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.PmsProductParam;
import com.zzy.malladmin.dto.PmsProductQueryParam;
import com.zzy.malladmin.dto.PmsProductResult;
import com.zzy.malladmin.mbg.model.PmsProduct;
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
        PmsProductResult pmsProductResult = pmsProductService.getUpdateInfo(id);
        if (pmsProductResult != null) {
            return CommonResult.success(pmsProductResult);
        } else {
            return CommonResult.failed();
        }
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
        int count = pmsProductService.updateInfo(id, pmsProductParam);
        if (count == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 分页查询商品 TODO 修改keyword参数为 QueryParam
     *
     * @param pmsProductQueryParam
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("分页查询商品列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult list(@RequestBody PmsProductQueryParam pmsProductQueryParam,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize) {
        //分页查询，返回product列表
        List<PmsProduct> pmsProductParamList = pmsProductService.list(pmsProductQueryParam, pageNum, pageSize);
        if (pmsProductParamList == null) {
            return CommonResult.failed("查询Product列表失败");
        } else {
            return CommonResult.success(pmsProductParamList);
        }
    }

    /**
     * 根据商品名称或者货号模糊查询
     *
     * @param keyword
     * @return
     */
    @ApiOperation("根据商品名称或者货号模糊查询")
    @RequestMapping(value = "/simpleList", method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProduct>> simpleList(@RequestParam("keyword") String keyword) {
        List<PmsProduct> productList = pmsProductService.simpleList(keyword);
        if (productList != null) {
            return CommonResult.success(CommonPage.restPage(productList));
        } else {
            return CommonResult.failed();
        }
    }


    /**
     * 批量修改删除状态
     *
     * @param ids
     * @param deleteStatus
     * @return
     */
    @ApiOperation("批量更新删除状态")
    @RequestMapping(value = "/updateDeleteStatus", method = RequestMethod.POST)
    public CommonResult deleteStatus(@RequestParam("ids") List<Long> ids,
                                     @RequestParam("deleteStatus") Integer deleteStatus) {
        int count = pmsProductService.batchUpdateDeleteStatus(ids, deleteStatus);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 批量修改新品状态
     *
     * @param ids
     * @param newStatus
     * @return
     */
    @ApiOperation("批量修改新品状态")
    @RequestMapping(value = "/update/newStatus", method = RequestMethod.POST)
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus) {
        int count = pmsProductService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return CommonResult.success();

        } else {
            return CommonResult.failed();
        }
    }


    /**
     * 批量修改发布状态
     *
     * @param ids
     * @param publishStatus
     * @return
     */
    @ApiOperation("批量修改发布状态")
    @RequestMapping(value = "/update/publishStatus", method = RequestMethod.POST)
    public CommonResult publishStatus(@RequestParam("ids") List<Long> ids,
                                      @RequestParam("publishStatus") Integer publishStatus) {
        int count = pmsProductService.updatePublishStatus(ids, publishStatus);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }

    }

    /**
     * 批量修改是否推荐
     *
     * @param ids
     * @param recommendStatus
     * @return
     */
    @ApiOperation("批量修改推荐状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    public CommonResult recommendStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = pmsProductService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }


    /**
     * 批量修改审核状态
     *
     * @param ids
     * @param verifyStatus
     * @return
     */
    @ApiOperation("批量修改审核状态")
    @RequestMapping(value = "/update/verifyStatus", method = RequestMethod.POST)
    public CommonResult verifyStatus(@RequestParam("ids") List<Long> ids,
                                     @RequestParam("verifyStatus") Integer verifyStatus) {
        int count = pmsProductService.updateVerifyStatus(ids, verifyStatus);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }


}
