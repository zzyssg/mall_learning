package com.zzy.malladmin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.common.CommonPage;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.PmsProductCategoryWithChildren;
import com.zzy.malladmin.dto.PmsProductDetail;
import com.zzy.malladmin.mbg.mapper.*;
import com.zzy.malladmin.mbg.model.*;
import com.zzy.malladmin.service.PmsPortalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mall_learning-main
 * @description: 前台商品管理service
 * @author: zzy
 * @create: 2023-11-19
 */
@Service
public class PmsPortalProductServiceImpl implements PmsPortalProductService {
    @Autowired
    PmsProductMapper productMapper;

    @Autowired
    PmsBrandMapper brandMapper;

    @Autowired
    PmsProductAttributeMapper attributeMapper;

    @Autowired
    PmsProductAttributeValueMapper attributeValueMapper;

    @Autowired
    PmsSkuStockMapper skuStockMapper;

    @Autowired
    PmsProductLadderMapper ladderMapper;

    @Autowired
    PmsProductFullReductionMapper fullReductionMapper;


    @Override
    public List<PmsProductCategoryWithChildren> categoryTreeList() {
        return null;
    }

    @Override
    public PmsProductDetail getDetail(Long productId) {
        PmsProductDetail productDetail = new PmsProductDetail();
        //需要得到编辑或者查看的详细数据
        //product brand List<PmsProductAttribute> PmsProductAttributeValueList LadderList FullReductionList
        //1 product
        PmsProduct product = productMapper.selectByPrimaryKey(productId);
        productDetail.setPmsProduct(product);
        //2 brand
        PmsBrand brand = brandMapper.selectByPrimaryKey(product.getBrandId());
        productDetail.setPmsBrand(brand);
        //3 attributeList  表中的数据信息部分重复：attribute的表中有attribute_category_id  但是attribute_value中有product_id
        PmsProductAttributeExample attributeExample = new PmsProductAttributeExample();
        attributeExample.createCriteria().andProductAttributeCategoryIdEqualTo(product.getProductAttributeCategoryId());
        List<PmsProductAttribute> pmsProductAttributeList = attributeMapper.selectByExample(attributeExample);
        productDetail.setProductAttributeList(pmsProductAttributeList);
        //4 attributeValueList
        if (!CollUtil.isEmpty(pmsProductAttributeList)) {
            List<Long> attributeIdList = pmsProductAttributeList.stream().map(PmsProductAttribute::getId).collect(Collectors.toList());
            PmsProductAttributeValueExample attributeValueExample = new PmsProductAttributeValueExample();
            attributeExample.createCriteria().andProductAttributeCategoryIdIn(attributeIdList);
            List<PmsProductAttributeValue> pmsProductAttributeValueList = attributeValueMapper.selectByExample(attributeValueExample);
            productDetail.setProductAttributeValueList(pmsProductAttributeValueList);
        }
        //5 stockList
        PmsSkuStockExample stockExample = new PmsSkuStockExample();
        stockExample.createCriteria().andProductIdEqualTo(productId);
        List<PmsSkuStock> pmsSkuStockList = skuStockMapper.selectByExample(stockExample);
        productDetail.setStockList(pmsSkuStockList);
        //6 fullReductionList
        PmsProductFullReductionExample fullReductionExample = new PmsProductFullReductionExample();
        fullReductionExample.createCriteria().andProductIdEqualTo(productId);
        List<PmsProductFullReduction> pmsProductFullReductionList = fullReductionMapper.selectByExample(fullReductionExample);
        productDetail.setFullReductionList(pmsProductFullReductionList);
        //7 ladderList
        PmsProductLadderExample ladderExample = new PmsProductLadderExample();
        ladderExample.createCriteria().andProductIdEqualTo(productId);
        List<PmsProductLadder> pmsProductLadderList = ladderMapper.selectByExample(ladderExample);
        productDetail.setLadderList(pmsProductLadderList);
        return productDetail;
    }

    @Override
    public CommonPage<PmsProduct> search(String keyword, Long brandId,Long productCategoryId,
                                         Long sort,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        if (!StrUtil.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        if (brandId != null) {
            criteria.andBrandIdEqualTo(brandId);
        }
        if (productCategoryId != null) {
            criteria.andProductCategoryIdEqualTo(productCategoryId);
        }
        //排序方式 sort ： 1：新品 2：销量 3：价格从低到高 4：价格从高到低
        if (sort == 1) {
            productExample.setOrderByClause("id desc");
        } else if (sort == 2) {
            productExample.setOrderByClause("sale desc");
        } else if (sort == 3) {
            productExample.setOrderByClause("price asc");
        } else {
            productExample.setOrderByClause("price desc");
        }
        List<PmsProduct> pmsProducts = productMapper.selectByExample(productExample);
        return CommonPage.restPage(pmsProducts);
    }
}