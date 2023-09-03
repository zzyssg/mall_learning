package com.zzy.boot_bootis.service;

import com.zzy.boot_bootis.mbg.model.PmsBrand;

import java.util.List;

/**
 * @ClassName PmsBrandService
 * @Author ZZy
 * @Date 2023/9/3 17:14
 * @Version 1.0
 */
public interface PmsBrandService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrand brand);

    int deleteBrand(Long id);

    int updateBrand(Long id, PmsBrand brand);

    List<PmsBrand> listBrand(int pageNum,int pageSize);

    PmsBrand getBrand(Long id);


}
