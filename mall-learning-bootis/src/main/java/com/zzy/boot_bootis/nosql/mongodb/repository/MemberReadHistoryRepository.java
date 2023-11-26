package com.zzy.boot_bootis.nosql.mongodb.repository;

import com.zzy.boot_bootis.nosql.mongodb.document.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName MemberReadHistoryRepository
 * @Author ZZy
 * @Date 2023/9/16 20:44
 * @Description
 * @Version 1.0
 */
@Repository
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory,String > {


    /**
     * 根据id获取记录，根据时间倒序排列
     * @param id
     * @return 阅读列表
     */
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long id);

}
