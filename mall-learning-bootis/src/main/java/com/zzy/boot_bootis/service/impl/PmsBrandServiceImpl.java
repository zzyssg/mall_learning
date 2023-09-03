package com.zzy.boot_bootis.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzy.boot_bootis.mbg.mapper.PmsBrandMapper;
import com.zzy.boot_bootis.mbg.model.PmsBrand;
import com.zzy.boot_bootis.mbg.model.PmsBrandExample;
import com.zzy.boot_bootis.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PmsBrandServiceImpl
 * @Author ZZy
 * @Date 2023/9/3 17:17
 * @Version 1.0
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {
    @Autowired
    PmsBrandMapper pmsBrandMapper;

    @Override
    public List<PmsBrand> listAllBrand() {
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int createBrand(PmsBrand brand) {
        return pmsBrandMapper.insertSelective(brand);
    }

    @Override
    public int deleteBrand(Long id) {
        return pmsBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateBrand(Long id, PmsBrand brand) {
        brand.setId(id);
        return pmsBrandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public List<PmsBrand> listBrand(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public PmsBrand getBrand(Long id) {
        return pmsBrandMapper.selectByPrimaryKey(id);
    }
}
