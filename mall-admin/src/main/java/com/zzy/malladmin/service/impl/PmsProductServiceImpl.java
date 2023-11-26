package com.zzy.malladmin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.dao.*;
import com.zzy.malladmin.dto.PmsProductParam;
import com.zzy.malladmin.dto.PmsProductQueryParam;
import com.zzy.malladmin.dto.PmsProductResult;
import com.zzy.malladmin.mbg.mapper.*;
import com.zzy.malladmin.mbg.model.*;
import com.zzy.malladmin.service.PmsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName PmsProductServiceImpl
 * @Author ZZy
 * @Date 2023/10/27 0:42
 * @Description
 * @Version 1.0
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpl.class);

    @Autowired
    PmsProductDao pmsProductDao;

    @Autowired
    PmsProductLadderDao productLadderDao;

    @Autowired
    PmsProductLadderMapper ladderMapper;

    @Autowired
    PmsSkuStockDao pmsSkuStockDao;

    @Autowired
    PmsSkuStockMapper skuStockMapper;

    @Autowired
    PmsMemberPriceDao pmsMemberPriceDao;

    @Autowired
    PmsMemberPriceMapper memberPriceMapper;

    @Autowired
    PmsProductFullReductionDao productFullReductionDao;

    @Autowired
    PmsProductFullReductionMapper fullReductionMapper;

    @Autowired
    PmsProductAttributeValueDao pmsProductAttributeValueDao;


    @Autowired
    PmsProductMapper pmsProductMapper;

    @Override
    public int create(PmsProductParam pmsProductParam) {
        int count;
        //创建商品
        PmsProduct product = pmsProductParam;
        product.setId(null);
        //TODO 这里会把插入参数product的主键id返回？
        pmsProductMapper.insertSelective(product);
        Long productId = product.getId();
        //根据促销类型设置加个：会员价格、阶梯价格、递减价格
        //会员价格
        relateAndInsertList(pmsMemberPriceDao, pmsProductParam.getMemberPriceList(), productId);
        //阶梯价格
        relateAndInsertList(productLadderDao, pmsProductParam.getProductLadderList(), productId);
        //满减价格
        relateAndInsertList(productFullReductionDao, pmsProductParam.getProductFullReductionList(), productId);
        //处理SKU编码
        handleSkuStockCode(pmsProductParam.getSkuStockList(), productId);
        //添加sku库存消息
        relateAndInsertList(pmsSkuStockDao, pmsProductParam.getSkuStockList(), productId);
        //添加商品参数、添加自定义商品价格
        relateAndInsertList(pmsProductAttributeValueDao, pmsProductParam.getProductAttributeValueList(), productId);
        //关联专题

        //关联优选

        count = 1;
        return count;


    }


    public List<PmsProductParam> list1(PmsProductQueryParam pmsProductQueryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //example条件查询product列表 List<Product> list1
        PmsProductExample pmsProductExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = pmsProductExample.createCriteria();
        //TODO 需要在外边、数据库条件查询之前做处理么？  看数据量？
        if (!StrUtil.isEmpty(pmsProductQueryParam.getKeyword())) {
            criteria.andNameLike(pmsProductQueryParam.getKeyword());
        }
        if (!StrUtil.isEmpty(pmsProductQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(pmsProductQueryParam.getProductSn());
        }
        if (pmsProductQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(pmsProductQueryParam.getProductCategoryId());
        }
        if (pmsProductQueryParam.getProductBrandId() != null) {
            criteria.andProductCategoryIdEqualTo(pmsProductQueryParam.getProductBrandId());
        }
        if (pmsProductQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(pmsProductQueryParam.getVerifyStatus());
        }
        if (pmsProductQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(pmsProductQueryParam.getPublishStatus());
        }
        List<PmsProduct> productListByExample = pmsProductMapper.selectByExample(pmsProductExample);
        List<PmsProductParam> result = new ArrayList<>();
        //对list1进行处理，包装成  List<ProductParam> list2
        for (PmsProduct product : productListByExample) {
            PmsProductParam pmsProductParam = new PmsProductParam();
            //添加skustocklist
            List<PmsSkuStock> pmsSkuStockList = pmsProductDao.selectSkuListProductId(product.getId());
            pmsProductParam.setSkuStockList(pmsSkuStockList);
            //添加阶梯价格
            List<PmsProductLadder> pmsProductLadderList = pmsProductDao.selectLadderListByProductId(product.getId());
            pmsProductParam.setProductLadderList(pmsProductLadderList);
            //添加满减
            List<PmsProductFullReduction> pmsProductFullReductionList = pmsProductDao.selectProductFullReductionListByProductId(product.getId());
            pmsProductParam.setProductFullReductionList(pmsProductFullReductionList);
            //添加会员列表
            List<PmsMemberPrice> pmsMemberPriceList = pmsProductDao.selectProductMemberPriceListByProductId(product.getId());
            pmsProductParam.setMemberPriceList(pmsMemberPriceList);
            //TODO 添加属性 如何使用几张属性表
//            List<>
            result.add(pmsProductParam);
        }

        return result;
    }

    @Override
    public List<PmsProduct> list(PmsProductQueryParam pmsProductQueryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //使用example查询PmsProduct列表
        PmsProductExample pmsProductExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = pmsProductExample.createCriteria();
        if (!StrUtil.isEmpty(pmsProductQueryParam.getKeyword())) {
            criteria.andNameLike("%" + pmsProductQueryParam.getKeyword() + "%");
        }
        if (pmsProductQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(pmsProductQueryParam.getPublishStatus());
        }
        if (pmsProductQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(pmsProductQueryParam.getVerifyStatus());
        }
        if (pmsProductQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(pmsProductQueryParam.getProductCategoryId());
        }
        if (pmsProductQueryParam.getProductBrandId() != null) {
            criteria.andBrandIdEqualTo(pmsProductQueryParam.getProductBrandId());
        }
        if (!StrUtil.isEmpty(pmsProductQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(pmsProductQueryParam.getProductSn());
        }
        List<PmsProduct> pmsProductList = pmsProductMapper.selectByExample(pmsProductExample);
        return pmsProductList;
    }

    @Override
    public List<PmsProduct> simpleList(String keyword) {
        ;
        PmsProductExample pmsProductExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = pmsProductExample.createCriteria();
        //名称、描述模糊查询
        if (StrUtil.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
            pmsProductExample.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }
        List<PmsProduct> productList = pmsProductMapper.selectByExample(pmsProductExample);
        return productList;
    }

    @Override
    public Integer batchUpdateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        //遍历数组，将对应product的id的状态改为 deleteStatus的状态
        PmsProduct product = new PmsProduct();
        product.setDeleteStatus(deleteStatus);
        PmsProductExample pmsProductExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = pmsProductExample.createCriteria();
        criteria.andIdIn(ids);
        int count = pmsProductMapper.updateByExampleSelective(product, pmsProductExample);
        return count;
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        //构造条件构造器
        //创造selective对象
        PmsProductExample pmsProductExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = pmsProductExample.createCriteria();
        criteria.andIdIn(ids);
        PmsProduct record = new PmsProduct();
        record.setNewStatus(newStatus);
        //调mapper
        int count = pmsProductMapper.updateByExampleSelective(record, pmsProductExample);
        return count;
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        //构造条件构造器
        PmsProductExample pmsProductExample = new PmsProductExample();
        pmsProductExample.createCriteria().andIdIn(ids);
        //设置record
        PmsProduct record = new PmsProduct();
        record.setPublishStatus(publishStatus);
        //调用mapper
        int count = pmsProductMapper.updateByExampleSelective(record, pmsProductExample);
        return count;
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProductExample pmsProductExample = new PmsProductExample();
        pmsProductExample.createCriteria().andIdIn(ids);
        PmsProduct record = new PmsProduct();
        record.setRecommandStatus(recommendStatus);
        int count = pmsProductMapper.updateByExampleSelective(record, pmsProductExample);
        return count;
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus) {
        PmsProduct record = new PmsProduct();
        record.setVerifyStatus(verifyStatus);
        PmsProductExample pmsProductExample = new PmsProductExample();
        pmsProductExample.createCriteria().andIdIn(ids);
        int count = pmsProductMapper.updateByExampleSelective(record, pmsProductExample);
        //TODO 修改完审核状态后插入审核记录 PmsProductVertifyRecord
        return count;

    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        PmsProductResult pmsProductResult = null;
        try {
            pmsProductResult = pmsProductDao.selectUpdateInfo(id);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return pmsProductResult;
    }

    @Override
    public int updateInfo(Long productId, PmsProductParam pmsProductParam) {
        //更新create时的信息
        //涉及到的表有：pms_product pms_member_price pms_product_ladder pms_sku_stock pms_product_full_reduction
        //1 pms_product
        PmsProduct product = new PmsProduct();
        BeanUtils.copyProperties(pmsProductParam, product);
        PmsProductExample productExample = new PmsProductExample();
        productExample.createCriteria().andIdEqualTo(pmsProductParam.getId());
        pmsProductMapper.updateByExampleSelective(product, productExample);
        //2 pms_member_price 根据productId批量更新memberPrice会员价格  删除+新增  productId   memberPriceList
        PmsMemberPriceExample memberPriceExample = new PmsMemberPriceExample();
        memberPriceExample.createCriteria().andProductIdEqualTo(productId);
        memberPriceMapper.deleteByExample(memberPriceExample);
        pmsMemberPriceDao.insertList(pmsProductParam.getMemberPriceList());
        //3 pms_product_ladder 更新阶梯价格
        PmsProductLadderExample ladderExample = new PmsProductLadderExample();
        ladderExample.createCriteria().andProductIdEqualTo(productId);
        ladderMapper.deleteByExample(ladderExample);
        productLadderDao.insertList(pmsProductParam.getProductLadderList());
        //4 pms_sku_stock 更新库存信息
        handleUpdateSkuStockList(productId,pmsProductParam);
//        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
//        skuStockExample.createCriteria().andProductIdEqualTo(productId);
//        skuStockMapper.deleteByExample(skuStockExample);
//        pmsSkuStockDao.insertList(pmsProductParam.getSkuStockList());
        //5 pms_product_full_reduction 更新满减价格
        PmsProductFullReductionExample fullReductionExample = new PmsProductFullReductionExample();
        fullReductionExample.createCriteria().andProductIdEqualTo(productId);
        fullReductionMapper.deleteByExample(fullReductionExample);
        productFullReductionDao.insertList(pmsProductParam.getProductFullReductionList());
        int count = 1;
        return count;
    }

    //对传来的sku做处理：1 originSkuStockList  2curSkuStockList  3insertList 4deleteList 5 updateList
    private void handleUpdateSkuStockList(Long productId, PmsProductParam pmsProductParam) {
        List<PmsSkuStock> curSkuStockList = pmsProductParam.getSkuStockList();
        //根据
        if (CollUtil.isEmpty(curSkuStockList)) {
            return;
        }
        //original list
        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(productId);
        List<PmsSkuStock> originalSkuStockList = skuStockMapper.selectByExample(skuStockExample);
        //new List
        List<PmsSkuStock> insertList = curSkuStockList.stream().filter(skeStock -> skeStock.getId() == null).collect(Collectors.toList());
        //update list
        List<PmsSkuStock> updateList = curSkuStockList.stream().filter(skuStock -> skuStock.getId() != null).collect(Collectors.toList());
        List<Long> updateIdList = updateList.stream().map(stock -> stock.getId()).collect(Collectors.toList());
        //delete list
        List<PmsSkuStock> deleteList = originalSkuStockList.stream().filter(skuStock -> !updateIdList.contains(skuStock.getId())).collect(Collectors.toList());
        //deal sku_code
        handleSkuStockCode(updateList, productId);
        handleSkuStockCode(insertList, productId);

        //新增 lists
        if (!CollUtil.isEmpty(insertList)) {
            relateAndInsertList(pmsSkuStockDao, insertList, productId);
        }
        //更新 list
        if (!CollUtil.isEmpty(updateList)) {
            for (PmsSkuStock skuStock : updateList) {
                skuStockMapper.updateByPrimaryKeySelective(skuStock);
            }
        }
        //删除 list
        if (!CollUtil.isEmpty(deleteList)) {
            for (PmsSkuStock skuStock : deleteList) {
                skuStockMapper.deleteByPrimaryKey(skuStock.getId());
            }
        }



    }

    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if(CollectionUtils.isEmpty(skuStockList))return;
        for(int i=0;i<skuStockList.size();i++){
            PmsSkuStock skuStock = skuStockList.get(i);
            if(StrUtil.isEmpty(skuStock.getSkuCode())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", productId));
                //三位索引id
                sb.append(String.format("%03d", i+1));
                skuStock.setSkuCode(sb.toString());
            }
        }

    }


    private void relateAndInsertList(Object dao, List relationList, Long productId) {
        if (CollUtil.isEmpty(relationList)) {
            return;
        }
        try {
            for (Object item : relationList) {
                //采用反射将id设置为空
                Method setId = null;
                setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null);
                //采用反射将productId设置为productId
                Method setProductId = null;
                setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            //批量调用dao层方法批量插入关联表
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            insertList.invoke(dao, relationList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
