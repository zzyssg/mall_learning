package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.PmsProductAttributeInfo;
import com.zzy.malladmin.dto.PmsProductAttributeParam;
import com.zzy.malladmin.mbg.model.PmsProductAttribute;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName PmsProductAttributeService
 * @Author ZZy
 * @Date 2023/11/16 20:59
 * @Description
 * @Version 1.0
 */
@Repository
public interface PmsProductAttributeService {

    int add(PmsProductAttributeParam attributeParam);

    List<PmsProductAttributeInfo> getByAttributeId(Long id);

    List<PmsProductAttribute> getAttributeListByCategoryId(Long id,Integer type,Integer pageNum,Integer pageSize);

    int delete(List<Long> ids);

    int update(Long id, PmsProductAttributeParam attributeParam);
}
