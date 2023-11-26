package com.zzy.malladmin.service.impl;

import com.zzy.malladmin.dao.PmsProductAttributeDao;
import com.zzy.malladmin.dto.PmsProductAttributeInfo;
import com.zzy.malladmin.dto.PmsProductAttributeParam;
import com.zzy.malladmin.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.zzy.malladmin.mbg.mapper.PmsProductAttributeMapper;
import com.zzy.malladmin.mbg.model.PmsProductAttribute;
import com.zzy.malladmin.mbg.model.PmsProductAttributeCategory;
import com.zzy.malladmin.mbg.model.PmsProductAttributeExample;
import com.zzy.malladmin.service.PmsProductAttributeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PmsProductAttributeServiceImpl
 * @Author ZZy
 * @Date 2023/11/16 21:07
 * @Description
 * @Version 1.0
 */
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {

    @Autowired
    PmsProductAttributeMapper attributeMapper;

    @Autowired
    PmsProductAttributeCategoryMapper attributeCategoryMapper;

    @Autowired
    PmsProductAttributeDao attributeDao;

    @Override
    public int add(PmsProductAttributeParam attributeParam) {

        PmsProductAttribute record = new PmsProductAttribute();
        BeanUtils.copyProperties(attributeParam, record);
        //TODO 所属属性类型的数量+1
        int count = attributeMapper.insertSelective(record);
        PmsProductAttributeCategory pmsProductAttributeCategory = attributeCategoryMapper.selectByPrimaryKey(attributeParam.getProductAttributeCategoryId());
        if (attributeParam.getType() == 1) {
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() + 1);
        } else {
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() + 1);
        }

        return count;
    }

    @Override
    public List<PmsProductAttributeInfo> getByAttributeId(Long productCategoryId) {
        return attributeDao.getAttributeInfo(productCategoryId);
    }

    @Override
    public List<PmsProductAttribute> getAttributeListByCategoryId(Long cid,Integer type,Integer pageNum,Integer pageSize) {
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type);
        List<PmsProductAttribute> pmsProductAttributes = attributeMapper.selectByExample(example);
        return pmsProductAttributes;
    }

    @Override
    public int delete(List<Long> ids) {
        //TODO 减小所属属性类型的数量 注意type类型
        PmsProductAttribute attribute = attributeMapper.selectByPrimaryKey(ids.get(0));
        int type = attribute.getType();
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andIdIn(ids);
        int count = attributeMapper.deleteByExample(example);
        PmsProductAttributeCategory pmsProductAttributeCategory = attributeCategoryMapper.selectByPrimaryKey(attribute.getProductAttributeCategoryId());
        if (type == 1) {
            //param
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() - count);
        } else {
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() - count);
        }
        attributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);

        return count;
    }

    @Override
    public int update(Long id, PmsProductAttributeParam attributeParam) {
        PmsProductAttribute record = new PmsProductAttribute();
        BeanUtils.copyProperties(attributeParam, record);
        record.setId(id);
        int count = attributeMapper.updateByPrimaryKey(record);
        return count;
    }

}
