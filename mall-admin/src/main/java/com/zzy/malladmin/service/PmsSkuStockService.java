package com.zzy.malladmin.service;

import com.zzy.malladmin.mbg.model.PmsSkuStock;

import java.util.List;

/**
 * @ClassName PmsSkuStockService
 * @Author ZZy
 * @Date 2023/11/12 16:04
 * @Description
 * @Version 1.0
 */
public interface PmsSkuStockService {

    List<PmsSkuStock> getSkuStockInfo(Long productId);


    int updateSkuInfo(Long productId, List<PmsSkuStock> skuStockParam);

}
