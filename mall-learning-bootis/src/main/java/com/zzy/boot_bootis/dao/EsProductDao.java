package com.zzy.boot_bootis.dao;

import com.zzy.boot_bootis.nosql.elasticsearch.document.EsProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName EsProductDao
 * @Author ZZy
 * @Date 2023/9/14 15:21
 * @Description
 * @Version 1.0
 */
@Repository
public interface EsProductDao {

    List<EsProduct> getAllEsProductList(@Param("id")Long id);

}
