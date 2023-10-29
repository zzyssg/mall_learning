package com.zzy.malladmin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.zzy.malladmin.dao.PmsMemberPriceDao;
import com.zzy.malladmin.dao.PmsProductDao;
import com.zzy.malladmin.dto.PmsProductParam;
import com.zzy.malladmin.mbg.mapper.PmsProductMapper;
import com.zzy.malladmin.mbg.model.PmsMemberPrice;
import com.zzy.malladmin.mbg.model.PmsProduct;
import com.zzy.malladmin.service.PmsProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName PmsProductServiceImpl
 * @Author ZZy
 * @Date 2023/10/27 0:42
 * @Description
 * @Version 1.0
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {


    @Autowired
    PmsProductDao pmsProductDao;

    @Autowired
    PmsMemberPriceDao pmsMemberPriceDao;

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
        //满减价格

        //处理SKU编码

        //添加sku库存消息

        //添加商品参数、添加自定义商品价格

        //关联专题


        //关联优选

        count = 1;
        return count;


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
