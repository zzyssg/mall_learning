package com.zzy.boot_bootis.nosql.elasticsearch.repository;

import com.zzy.boot_bootis.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName EsProductRepository
 * @Author ZZy
 * @Date 2023/9/14 14:59
 * @Description 商品Es操作repo
 * @Version 1.0
 */
@Repository
public interface EsProductRepository extends ElasticsearchRepository<EsProduct,Long> {

    /**
     * 搜索查询
     *
     * @Param name 商品名称
     * @Param subTitle 商品标题
     * #@Param keywords 商品关键字
     * @Param page 分页信息
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable pageable);

}
