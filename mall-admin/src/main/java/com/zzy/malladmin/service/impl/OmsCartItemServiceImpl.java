package com.zzy.malladmin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.zzy.malladmin.dao.OmsCartItemDao;
import com.zzy.malladmin.dto.CartProduct;
import com.zzy.malladmin.dto.CartPromotionItem;
import com.zzy.malladmin.mbg.mapper.OmsCartItemMapper;
import com.zzy.malladmin.mbg.model.OmsCartItem;
import com.zzy.malladmin.mbg.model.OmsCartItemExample;
import com.zzy.malladmin.service.OmsCartItemService;
import com.zzy.malladmin.service.OmsPromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-29
 */
@Service
public class OmsCartItemServiceImpl implements OmsCartItemService {

    @Autowired
    OmsCartItemDao cartItemDao;

    @Autowired
    OmsCartItemMapper cartItemMapper;

    @Autowired
    OmsPromotionService promotionService;

    @Override
    public int delete(long memberId, List<Long> ids) {
        OmsCartItem record = new OmsCartItem();
        record.setDeleteStatus(1);
        record.setModifyDate(new Date());
        OmsCartItemExample cartItemExample = new OmsCartItemExample();
        //TODO 这里的id指的是商品的id还是 cartItem表的ID？如果是表的ID，应该是不需要用户
        cartItemExample.createCriteria().andMemberIdEqualTo(memberId).andIdIn(ids);
        int count = cartItemMapper.updateByExample(record, cartItemExample);
        return count;
    }

    @Override
    public int clear(long memberId) {
//        //获取当前会员的购物车
//        OmsCartItemExample cartItemExample = new OmsCartItemExample();
//        cartItemExample.createCriteria().andMemberIdEqualTo(memberId).andDeleteStatusEqualTo(0);
//        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(cartItemExample);
        //清空购物车
//        List<Long> ids = cartItemList.stream().map(OmsCartItem::getId).collect(Collectors.toList());
        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setDeleteStatus(1);
        cartItem.setModifyDate(new Date());
        OmsCartItemExample deleteExample = new OmsCartItemExample();
        deleteExample.createCriteria().andMemberIdEqualTo(memberId);
        int count = cartItemMapper.updateByExample(cartItem, deleteExample);
        return count;
    }

    @Override
    public CartProduct getProduct(Long productId) {
        CartProduct cartProduct = cartItemDao.getProduct(productId);
        return cartProduct;
    }

    @Override
    public int add(OmsCartItem omsCartItem) {
        //商品没有的话，需要先插入；已存在的话，+1即可
        //1 从购物车中，根据memberId、productId、deleteStatus=0
        int count;
        Long productId = omsCartItem.getProductId();
        //TODO 修改memberId的来源
        Long memberId = 1L;
        omsCartItem.setMemberId(memberId);
        OmsCartItem existCartItem = getCartItem(omsCartItem);
        if (existCartItem != null) {
            //已经存在，更新
            existCartItem.setModifyDate(new Date());
            existCartItem.setQuantity(omsCartItem.getQuantity());
            count= cartItemMapper.updateByPrimaryKeySelective(existCartItem);
        } else {
            //不存在，插入
            omsCartItem.setCreateDate(new Date());
            omsCartItem.setModifyDate(new Date());
            count = cartItemMapper.insert(omsCartItem);
        }
        return count;
    }

    private OmsCartItem getCartItem(OmsCartItem omsCartItem) {
        OmsCartItemExample cartItemExample = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = cartItemExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0).andMemberIdEqualTo(omsCartItem.getMemberId())
                .andProductIdEqualTo(omsCartItem.getProductId());
        if (omsCartItem.getProductSkuId() != null) {
            criteria.andProductSkuIdEqualTo(omsCartItem.getProductSkuId());
        }
        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(cartItemExample);
        if (CollUtil.isEmpty(cartItemList)) {
            return null;
        } else {
            return cartItemList.get(0);
        }
    }

    @Override
    public int updateAttribute(OmsCartItem cartItem) {
        //删除原来的购物车属性
        OmsCartItem updateCart = new OmsCartItem();
        updateCart.setModifyDate(new Date());
        updateCart.setDeleteStatus(1);
        updateCart.setId(cartItem.getId());
        cartItemMapper.updateByPrimaryKeySelective(updateCart);
        //重新add
        OmsCartItem addCart = new OmsCartItem();
        BeanUtils.copyProperties(cartItem, addCart);
        addCart.setCreateDate(new Date());
        addCart.setModifyDate(new Date());
        addCart.setDeleteStatus(0);
        addCart.setId(null);
        add(addCart);
        return 1;
    }

    //id为cartItem的id
    @Override
    public int updateQuantity(long memberId, Long id, Integer quantity) {
        OmsCartItem quantityCartItem = new OmsCartItem();
        quantityCartItem.setQuantity(quantity);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId).andIdEqualTo(id).andDeleteStatusEqualTo(0);
        int count = cartItemMapper.updateByExampleSelective(quantityCartItem, example);

        return count;
    }

    @Override
    public List<OmsCartItem> list(long memberId) {
        OmsCartItemExample cartItemExample = new OmsCartItemExample();
        cartItemExample.createCriteria().andMemberIdEqualTo(memberId).andDeleteStatusEqualTo(0);
        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(cartItemExample);
        return cartItemList;
    }

    @Override
    public List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds) {
        //根据memberId获取购物车列表
        OmsCartItemExample cartItemExample = new OmsCartItemExample();
        cartItemExample.createCriteria().andMemberIdEqualTo(memberId);
        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(cartItemExample);
        //过滤出促销的item
        cartItemList = cartItemList.stream().filter(cartItem -> cartIds.contains(cartItem.getId())).collect(Collectors.toList());
        //将cartItem转为cartPromotionItem
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        cartPromotionItemList = promotionService.calcCartPromotion(cartItemList);
        return cartPromotionItemList;

    }
}