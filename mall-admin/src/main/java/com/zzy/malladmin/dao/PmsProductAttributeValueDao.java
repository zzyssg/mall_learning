package com.zzy.malladmin.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName PmsProductAttributeValueDao
 * @Author ZZy
 * @Date 2023/11/2 11:18
 * @Description
 * @Version 1.0
 */
@Repository
public interface PmsProductAttributeValueDao {

    void insertList(List list);


}
