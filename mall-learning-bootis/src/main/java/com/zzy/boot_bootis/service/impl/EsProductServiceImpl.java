package com.zzy.boot_bootis.service.impl;

import com.zzy.boot_bootis.dao.EsProductDao;
import com.zzy.boot_bootis.nosql.elasticsearch.document.EsProduct;
import com.zzy.boot_bootis.nosql.elasticsearch.repository.EsProductRepository;
import com.zzy.boot_bootis.service.EsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @ClassName EsProductServiceImpl
 * @Author ZZy
 * @Date 2023/9/14 15:12
 * @Description
 * @Version 1.0
 */
@Service
public class EsProductServiceImpl implements EsProductService {

    @Autowired
    EsProductRepository esProductRepository;

    @Autowired
    EsProductDao esProductDao;


    @Override
    public int importAll() {
        List<EsProduct> esProductList = esProductDao.getAllEsProductList(null);
        Iterable<EsProduct> esProductIterable =  esProductRepository.saveAll(esProductList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }

    @Override
    public void delete(Long id) {
        esProductRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        List<EsProduct> esProducts = esProductDao.getAllEsProductList(id);
        EsProduct esProduct = null;
        if (esProducts.size() > 0) {
            esProduct = esProductRepository.save(esProducts.get(0));
        }
        return esProduct;
    }

    @Override
    public void delete(List<Long> ids) {
        esProductRepository.deleteAllById(ids);
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable page = PageRequest.of(pageNum,pageSize);
        Page<EsProduct> result = esProductRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, page);
        return result;
    }
}
