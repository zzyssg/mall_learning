package com.zzy.malladmin.dao;

import com.zzy.malladmin.mbg.model.UmsMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UmsMenuDao
 * @Author ZZy
 * @Date 2023/10/15 15:35
 * @Description
 * @Version 1.0
 */
@Repository
public interface UmsMenuDao {


    List<UmsMenu> selectAllMenu();

    List<UmsMenu> selectDownByParentId(Long parentId);
}
