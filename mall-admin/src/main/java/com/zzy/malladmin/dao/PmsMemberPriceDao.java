package com.zzy.malladmin.dao;

import com.zzy.malladmin.mbg.model.PmsMemberPrice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName PmsMemberPriceDao
 * @Author ZZy
 * @Date 2023/10/29 22:15
 * @Description
 * @Version 1.0
 */
@Repository
public interface PmsMemberPriceDao {

    int insertList(List<PmsMemberPrice> pmsMemberPriceList);

}
