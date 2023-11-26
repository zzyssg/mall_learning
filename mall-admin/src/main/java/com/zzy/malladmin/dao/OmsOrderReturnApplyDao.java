package com.zzy.malladmin.dao;

import com.zzy.malladmin.dto.OmsOrderReturnApplyQueryParam;
import com.zzy.malladmin.mbg.model.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-26
 */
public interface OmsOrderReturnApplyDao {

    List<OmsOrderReturnApply> getList(@Param("pageQueryParam") OmsOrderReturnApplyQueryParam pageQueryParam);

}