package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.UmsMenuNode;
import com.zzy.malladmin.mbg.model.UmsMenu;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @ClassName UmsMenuService
 * @Author ZZy
 * @Date 2023/10/15 15:14
 * @Description
 * @Version 1.0
 */
public interface UmsMenuService {

    int create(UmsMenu umsMenu);

    int delete(Long menuId);

    int update(Long menuId, UmsMenu umsMenu);

    int updateHidden(Long menuId, Integer hidden);

    UmsMenu getMenu(Long id);

    List<UmsMenuNode> treeList();

    List<UmsMenu> list(Long parentId, Integer pageNum, Integer pageSize);
}
