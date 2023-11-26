package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.PmsProductParam;
import com.zzy.malladmin.dto.PmsProductQueryParam;
import com.zzy.malladmin.dto.PmsProductResult;
import com.zzy.malladmin.mbg.model.PmsProduct;

import java.util.List;

/**
 * @ClassName ProductService
 * @Author ZZy
 * @Date 2023/10/27 0:39
 * @Description
 * @Version 1.0
 */
public interface PmsProductService {


    int create(PmsProductParam pmsProductParam);

    List<PmsProduct> list(PmsProductQueryParam pmsProductQueryParam, Integer pageNum, Integer pageSize);


    /**
     * 根据关键词简单查询商品展示列表
     * @param keyword
     * @return
     */
    List<PmsProduct> simpleList(String keyword);

    Integer batchUpdateDeleteStatus(List<Long> ids, Integer deleteStatus);


    int updateNewStatus(List<Long> ids, Integer newStatus);

    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    int updateRecommendStatus(List<Long> ids,Integer recommendStatus);

    int updateVerifyStatus(List<Long> ids, Integer verifyStatus);

    /**
     * 编辑前，先获得原来的信息 + 父类ID
     * @param id
     * @return
     */
    PmsProductResult getUpdateInfo(Long id);

    int updateInfo(Long id, PmsProductParam pmsProductParam);
}
