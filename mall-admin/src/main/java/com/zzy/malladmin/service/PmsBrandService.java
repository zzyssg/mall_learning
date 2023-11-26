package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.PmsBrandParam;
import com.zzy.malladmin.mbg.model.PmsBrand;

import java.util.List;

public interface PmsBrandService {

    int create(PmsBrandParam brandParam);

    int delete(Long id);

    int update(Long id, PmsBrandParam brandParam);

    List<PmsBrand> list(String keyword, Integer showStatus,Integer pageNum, Integer pageSize);

    List<PmsBrand> listAll();

    PmsBrand getItem(Long id);


    int deleteBatch(List<Long> ids);

    int updateFactoryStatus(List<Long> ids, Integer status);

    int updateShowStatus(List<Long> ids, Integer status);
}
