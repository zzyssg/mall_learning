package com.zzy.malladmin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.dao.UmsMenuDao;
import com.zzy.malladmin.dto.UmsMenuNode;
import com.zzy.malladmin.mbg.mapper.UmsMenuMapper;
import com.zzy.malladmin.mbg.model.UmsMenu;
import com.zzy.malladmin.mbg.model.UmsMenuExample;
import com.zzy.malladmin.service.UmsMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName UmsMenuService
 * @Author ZZy
 * @Date 2023/10/15 15:15
 * @Description
 * @Version 1.0
 */
@Service
public class UmsMenuServiceImpl implements UmsMenuService {

    @Autowired
    UmsMenuMapper umsMenuMapper;

    @Autowired
    UmsMenuDao umsMenuDao;



    /**
     * umsMenu有层级关系，需要将层级关系、层架相关的信息一并设置好
     *
     * @param umsMenu
     * @return
     */
    @Override
    public int create(UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        updateLevel(umsMenu);
        return umsMenuMapper.insert(umsMenu);
    }

    /**
     * 层级关系，需要先删除下级菜单吗？？ TODO
     * @param menuId
     * @return
     */
    @Override
    public int delete(Long menuId) {
        //删除此菜单
        return umsMenuMapper.deleteByPrimaryKey(menuId);
    }

    /**
     * 根据菜单Id更新菜单
     * @param menuId
     * @param umsMenu
     * @return
     */
    @Override
    public int update(Long menuId, UmsMenu umsMenu) {
        umsMenu.setId(menuId);
        //有可能将上级菜单改了
        updateLevel(umsMenu);
        int count = umsMenuMapper.updateByPrimaryKeySelective(umsMenu);
        return count;
    }

    /**
     * 将menu列表以树的形式返回
     * @return
     */
    @Override
    public List<UmsMenuNode> treeList() {
        //查询所有的菜单 menuList
        List<UmsMenu> menuList = umsMenuMapper.selectByExample(new UmsMenuExample());
        //将menuList转为UmsMenuNode,顶层只有4个node
        List<UmsMenuNode> umsMenuNodeList = menuList.stream().filter(umsMenu -> 0 == umsMenu.getId())
                .map(menu -> convertMenuToNode(menu, menuList))
                .collect(Collectors.toList());
        return umsMenuNodeList;
    }

    @Override
    public List<UmsMenu> list(Long parentId, Integer  pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsMenuExample umsMenuExample = new UmsMenuExample();
        umsMenuExample.createCriteria().andParentIdEqualTo(parentId);
        List<UmsMenu> menuList = umsMenuMapper.selectByExample(umsMenuExample);
        return menuList;
    }

    private UmsMenuNode convertMenuToNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode umsMenuNode = new UmsMenuNode();
        BeanUtils.copyProperties(menu, umsMenuNode);
        List<UmsMenuNode> children = menuList.stream().filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> convertMenuToNode(subMenu, menuList))
                .collect(Collectors.toList());
        umsMenuNode.setChildren(children);
        return umsMenuNode;
    }

    @Override
    public int updateHidden(Long menuId, Integer hidden) {
        UmsMenu umsMenu = new UmsMenu();
        umsMenu.setId(menuId);
        umsMenu.setHidden(hidden);
        return update(menuId, umsMenu);
    }

    @Override
    public UmsMenu getMenu(Long id) {
        return umsMenuMapper.selectByPrimaryKey(id);
    }

    private void updateLevel(UmsMenu umsMenu) {
        //判断是否是顶级菜单  是：将level设为0
        if (umsMenu.getId() == 0) {
            umsMenu.setLevel(0);
        } else {
            //不是顶级菜单，将level设为父级菜单的level+1
            UmsMenu parentMenu = umsMenuMapper.selectByPrimaryKey(umsMenu.getParentId());
            if (parentMenu == null) {
                umsMenu.setLevel(0);
            } else {
                umsMenu.setLevel(parentMenu.getLevel() + 1);
            }
        }
    }
}
