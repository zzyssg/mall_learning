package com.zzy.malladmin.common;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @ClassName CommonPage
 * @Author ZZy
 * @Date 2023/9/29 12:29
 * @Description 分页数据封装类
 * @Version 1.0
 */
@Data
public class CommonPage<T> {

    private int pageSize;
    private int pageNum;

    private int totalPage;
    private int totalSize;
    private List<T> list;

    /**
     * 借助PageHelper分页的list
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageSize(pageInfo.getPageSize());
        result.setPageNum(pageInfo.getPageNum());
        result.setTotalSize(pageInfo.getSize());
        result.setList(pageInfo.getList());
        return result;
    }

    public static <T> CommonPage<T> restPage(PageInfo pageInfo) {
        CommonPage<T> result = new CommonPage<>();
        result.setTotalSize(pageInfo.getSize());
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setList(pageInfo.getList());
        return result;
    }

}
