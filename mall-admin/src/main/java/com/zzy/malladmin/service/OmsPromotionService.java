package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.CartPromotionItem;
import com.zzy.malladmin.mbg.model.OmsCartItem;

import java.util.List;

/**
 * @program: mall_learning
 * @description: 购物车促销
 * @author: zzy
 * @create: 2023-12-16
 */
public interface OmsPromotionService {

    List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList);

}
