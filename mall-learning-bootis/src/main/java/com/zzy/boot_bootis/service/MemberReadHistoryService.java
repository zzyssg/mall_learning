package com.zzy.boot_bootis.service;

import com.zzy.boot_bootis.nosql.mongodb.document.MemberReadHistory;

import java.util.List;

/**
 * @ClassName MemberReadHistoryService
 * @Author ZZy
 * @Date 2023/9/16 21:01
 * @Description 会员浏览记录管理
 * @Version 1.0
 */
public interface MemberReadHistoryService {

    /**
     * 新增记录
     * @param memberReadHistory
     * @return
     */
    int create(MemberReadHistory memberReadHistory);

    /**
     * 批量删除记录
     * @param ids
     * @return
     */
    int delete(List<String> ids);

    /**
     * 根据人员ID查询浏览记录
     * @param id
     * @return
     */
    List<MemberReadHistory> list(Long id);



}
