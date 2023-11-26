package com.zzy.malladmin.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName PmsProductFullReductionDao
 * @Author ZZy
 * @Date 2023/11/1 21:50
 * @Description
 * @Version 1.0
 */
@Repository
public interface PmsProductFullReductionDao {

    void insertList(List list);

}
