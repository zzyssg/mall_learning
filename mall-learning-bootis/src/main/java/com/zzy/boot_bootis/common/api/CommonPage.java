package com.zzy.boot_bootis.common.api;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName CommonPage
 * @Author ZZy
 * @Date 2023/9/3 22:51
 * @Description 分页数据分装类
 * @Version 1.0
 */
public class CommonPage<T> {

    private Integer pageNum;

    private Integer pageSize;

    private Integer totalPage;

    private Long total;

    private List<T> list;

    /**
     * 将 PageHelper分页后的list 转为 分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotalPage(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public List<T> getList() {
        return list;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
