package com.zzy.malladmin.dao;

import com.zzy.malladmin.dto.PmsProductAttributeInfo;
import com.zzy.malladmin.mbg.model.PmsProductAttribute;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName PmsProductAttributeDao
 * @Author ZZy
 * @Date 2023/11/16 21:12
 * @Description
 * @Version 1.0
 */
@Repository
public interface PmsProductAttributeDao {

    List<PmsProductAttributeInfo> getAttributeInfo(@Param("cid") Long cid);

}
