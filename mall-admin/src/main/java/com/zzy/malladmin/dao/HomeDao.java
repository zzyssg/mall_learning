package com.zzy.malladmin.dao;

import com.zzy.malladmin.mbg.model.PmsBrand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: mall_learning-main
 * @description: 自定义首页Dao
 * @author: zzy
 * @create: 2023-11-19
 */
@Repository
public interface HomeDao {

    List<PmsBrand> getRecommendBrandList(@Param("offset") Integer offset, @Param("limit") Integer limit);



}
