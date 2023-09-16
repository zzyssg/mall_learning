package com.zzy.boot_bootis.service.impl;

import com.zzy.boot_bootis.nosql.mongodb.document.MemberReadHistory;
import com.zzy.boot_bootis.nosql.mongodb.repository.MemberReadHistoryRepository;
import com.zzy.boot_bootis.service.MemberReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName MemberReadHistoryServiceImpl
 * @Author ZZy
 * @Date 2023/9/16 21:06
 * @Description
 * @Version 1.0
 */
@Service
public class MemberReadHistoryServiceImpl implements MemberReadHistoryService {
    @Autowired
    MemberReadHistoryRepository memberReadHistoryRepository;

    @Override
    public int create(MemberReadHistory memberReadHistory) {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(new Date());
        memberReadHistoryRepository.save(memberReadHistory);
        return 1;

    }

    @Override
    public int delete(List<String> ids) {
        List<MemberReadHistory> list = new ArrayList<>();
        for (String id : ids) {
            MemberReadHistory memberReadHistory = new MemberReadHistory();
            memberReadHistory.setId(id);
            list.add(memberReadHistory);
        }
        memberReadHistoryRepository.deleteAll(list);
        return ids.size();
    }

    @Override
    public List<MemberReadHistory> list(Long id) {
        return memberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(id);
    }
}
