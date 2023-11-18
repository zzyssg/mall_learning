package com.zzy.malladmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.dto.PmsProductCategoryWithChildren;
import com.zzy.malladmin.mbg.mapper.PmsProductCategoryMapper;
import com.zzy.malladmin.mbg.mapper.PmsProductMapper;
import com.zzy.malladmin.mbg.model.PmsProduct;
import com.zzy.malladmin.mbg.model.PmsProductCategory;
import com.zzy.malladmin.mbg.model.PmsProductCategoryExample;
import com.zzy.malladmin.mbg.model.PmsProductExample;
import com.zzy.malladmin.service.PmsProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName PmsProductCategoryServiceImpl
 * @Author ZZy
 * @Date 2023/11/12 20:18
 * @Description
 * @Version 1.0
 */
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Autowired
    PmsProductCategoryMapper productCategoryMapper;

    @Autowired
    PmsProductMapper pmsProductMapper;

    @Override
    public int create(PmsProductCategory productCategory) {


        //相关信息、上下级信息level、parentId
        setLevel(productCategory);
        int count = productCategoryMapper.insert(productCategory);

        //TODO 属性关联关系 attribute

        return count;
    }

    private void setLevel(PmsProductCategory productCategory) {
        //是否为顶级
        if (productCategory.getParentId() == null || productCategory.getParentId() == 0) {
            //0:level1
            productCategory.setLevel(0);
        } else {
            //TODO 上级被删除后，parentCategory == null 的情况
            PmsProductCategory parentCategory = productCategoryMapper.selectByPrimaryKey(productCategory.getParentId());
            if (productCategory == null) {
                productCategory.setLevel(0);

            } else {
                productCategory.setLevel(parentCategory.getLevel() + 1);
            }
        }
        //不是顶级，设置父级的level+1
    }

    @Override
    public int update(Long id, PmsProductCategory pmsProductCategory) {
        //TODO 能不能这样使用？设置了之后，会影响到别处吗？
        pmsProductCategory.setId(id);
        //tb_product: product_category_name
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setProductCategoryName(pmsProductCategory.getName());
        PmsProductExample productExample = new PmsProductExample();
        productExample.createCriteria().andProductCategoryIdEqualTo(id);
        pmsProductMapper.updateByExampleSelective(pmsProduct, productExample);
        //更新属性attribute的list
        int count = productCategoryMapper.updateByPrimaryKeySelective(pmsProductCategory);
        return count;
    }

    @Override
    public List<PmsProductCategory> listByParentId(Long parentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //条件构造器查询
        PmsProductCategoryExample productCategoryExample = new PmsProductCategoryExample();
        //无需写order by几个字，降序排列
        productCategoryExample.setOrderByClause("sort desc");
        productCategoryExample.createCriteria().andParentIdEqualTo(parentId);
        List<PmsProductCategory> productCategoryList = productCategoryMapper.selectByExample(productCategoryExample);
        return productCategoryList;

    }

    @Override
    public PmsProductCategory getInfo(Long id) {
        return productCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return productCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsProductCategoryWithChildren> listWithChildren() {
        //查询出所有的类型数据
        PmsProductCategoryExample productCategoryExample = new PmsProductCategoryExample();
        List<PmsProductCategory> productCategoryList = productCategoryMapper.selectByExample(productCategoryExample);
        //对数据进行递归处理，得到List<PmsProductCategoryWithChildren>
        List<PmsProductCategoryWithChildren> list = productCategoryList
                .stream().filter(category -> category.getParentId() == 0L)
                .map(category -> convertToCategoryWithChildren(productCategoryList, category)
                ).collect(Collectors.toList());
        return list;
    }

    private PmsProductCategoryWithChildren convertToCategoryWithChildren(List<PmsProductCategory> productCategoryList,
                                                                         PmsProductCategory category) {
        PmsProductCategoryWithChildren productCategoryWithChildren = new PmsProductCategoryWithChildren();
        BeanUtils.copyProperties(category, productCategoryWithChildren);
        List<PmsProductCategoryWithChildren> children = productCategoryList.stream().filter(
                curCategory -> curCategory.getParentId() == category.getId()
        ).map(
                curCategory -> convertToCategoryWithChildren(productCategoryList, curCategory)
        ).collect(Collectors.toList());
        productCategoryWithChildren.setChildren(children);
        return productCategoryWithChildren;

    }

    //将列表转换为树形结构，顶级节点为level = 0

}
