package com.zzy.malladmin.dao;

import com.zzy.malladmin.dto.CartProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-29
 */
@Repository
public interface OmsCartItemDao {

    CartProduct getProduct(@Param("productId") Long productId);


}
