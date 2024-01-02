package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.CartProduct;
import com.zzy.malladmin.dto.CartPromotionItem;
import com.zzy.malladmin.mbg.model.OmsCartItem;


import java.util.List;

/**
* @author zhangzheyuan
* @description 针对表【oms_cart_item(购物车表)】的数据库操作Service
* @createDate 2023-11-26 10:20:38
*/
public interface OmsCartItemService {

    int delete(long memberId, List<Long> ids);

    int clear(long memberId);

    CartProduct getProduct(Long productId);

    int add(OmsCartItem omsCartItem);

    int updateAttribute(OmsCartItem cartItem);

    int updateQuantity(long memberId, Long id, Integer quantity);

    List<OmsCartItem> list(long memberId);

    List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds);
}
