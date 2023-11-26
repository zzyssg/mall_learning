package com.zzy.malladmin.dao;

import com.zzy.malladmin.dto.PmsProductAttributeCategoryChildren;
import com.zzy.malladmin.mbg.model.PmsProductAttributeCategory;
import org.springframework.stereotype.Repository;

import javax.validation.ReportAsSingleViolation;
import java.util.List;

/**
 * @ClassName PmsProductAttributeCategoryDao
 * @Author ZZy
 * @Date 2023/11/15 22:56
 * @Description
 * @Version 1.0
 */
@Repository
public interface PmsProductAttributeCategoryDao {

    List<PmsProductAttributeCategoryChildren> getAttributeCategoryWithChildren();

    List<PmsProductAttributeCategory> test();

}
