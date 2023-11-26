package com.zzy.malladmin.service.impl;

import com.zzy.malladmin.dao.PmsSkuStockDao;
import com.zzy.malladmin.mbg.mapper.PmsSkuStockMapper;
import com.zzy.malladmin.mbg.model.PmsSkuStock;
import com.zzy.malladmin.mbg.model.PmsSkuStockExample;
import com.zzy.malladmin.service.PmsSkuStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PmsSkuStockServiceImpl
 * @Author ZZy
 * @Date 2023/11/12 16:05
 * @Description
 * @Version 1.0
 */
@Service
public class PmsSkuStockServiceImpl implements PmsSkuStockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpl.class);

    @Autowired
    PmsSkuStockMapper skuStockMapper;

    @Autowired
    PmsSkuStockDao skuStockDao;

    @Override
    public List<PmsSkuStock> getSkuStockInfo(Long productId) {
        //TODO 优化，抛出异常，并且处理
        if (productId == null) {
            return null;
        }
        //构造条件构造器
        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(productId);
        //使用mapper+example
        List<PmsSkuStock> skuStockList = skuStockMapper.selectByExample(skuStockExample);
        return skuStockList;
    }

    @Override
    public int updateSkuInfo(Long productId, List<PmsSkuStock> skuStockParam) {

        //删除旧
        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(productId);
        skuStockMapper.deleteByExample(skuStockExample);
        //新增
        int count = -1;
        try {
            count = skuStockDao.replaceList(skuStockParam);

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return count;
    }
}
