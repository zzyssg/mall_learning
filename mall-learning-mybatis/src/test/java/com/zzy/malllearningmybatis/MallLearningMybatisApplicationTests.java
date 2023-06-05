package com.zzy.malllearningmybatis;

import com.github.pagehelper.PageInfo;
import com.zzy.malllearningmybatis.dao.UmsAdminDao;
import com.zzy.malllearningmybatis.dao.UmsResourceCategoryDao;
import com.zzy.malllearningmybatis.dao.UmsResourceDao;
import com.zzy.malllearningmybatis.domain.UmsResourceCategoryExt;
import com.zzy.malllearningmybatis.domain.UmsResourceExt;
import com.zzy.malllearningmybatis.model.UmsAdmin;
import com.zzy.malllearningmybatis.model.UmsResource;
import com.zzy.malllearningmybatis.service.UmsResourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
class MallLearningMybatisApplicationTests {

    @Autowired
    UmsAdminDao umsAdminDao;

    @Autowired
    UmsResourceDao umsResourceDao;


    @Autowired
    UmsResourceCategoryDao umsResourceCategoryDao;

    @Autowired
    UmsResourceService umsResourceService;

    @Test
    void contextLoads() {
    }

    @Test
    void testMybatis() {
        UmsAdmin umsAdmin = umsAdminDao.selectByIdSimple(1L);
        log.info(umsAdmin.toString());
//        System.out.println(umsAdmin.toString());
    }

    @Test
    void testInsert() {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setUsername("insert001");
        umsAdmin.setPassword("123456");
        umsAdmin.setEmail("123456@qq.com");
        umsAdmin.setNickName("hhh123");
        umsAdmin.setStatus(1);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setNote("note123");
        int id = umsAdminDao.insert(umsAdmin);
        log.info("id:{} \n umsAdmin:{}",id,umsAdmin);
    }

    @Test
    void testInsert2(){
        UmsAdmin admin = new UmsAdmin();
        admin.setUsername("newTest");
        admin.setPassword("123456");
        admin.setEmail("newTest@qq.com");
        admin.setNickName("tester");
        admin.setCreateTime(new Date());
        admin.setStatus(1);
        int result = umsAdminDao.insert2(admin);
        log.info("testInsert id={},result={}",admin.getId(),result);
    }

    @Test
    void testUpdate() {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(20L);
        umsAdmin.setUsername("insert002");
        umsAdmin.setPassword("1");
        umsAdmin.setEmail("1@qq.com");
        umsAdmin.setNickName("1");
        umsAdmin.setStatus(1);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setNote("1");
        int id = umsAdminDao.updateById(umsAdmin);
        log.info("id:{} \n umsAdmin:{}",id,umsAdmin);
    }

    @Test
    void testDelete() {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(19L);
        umsAdmin.setUsername("insert001");
        umsAdmin.setPassword("123456");
        umsAdmin.setEmail("123456@qq.com");
        umsAdmin.setNickName("hhh123");
        umsAdmin.setStatus(1);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setNote("note123");
        int id = umsAdminDao.deleteById(umsAdmin);
        log.info("id:{} \n umsAdmin:{}",id,umsAdmin);
    }

    @Test
    void testSelectByUsernameAndEmailLike() {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(21L);
        umsAdmin.setUsername("ro");
        umsAdmin.setPassword("1");
        umsAdmin.setEmail("com");
        umsAdmin.setNickName("hhh123");
        umsAdmin.setStatus(1);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setNote("note123");
        List<UmsAdmin> list = umsAdminDao.selectByUsernameAndEmailLike(umsAdmin);
//        log.info("results: {}",list);
        for (UmsAdmin umsAdmin1 : list) {
            log.info(umsAdmin1.toString());
            System.out.println();
        }
    }

    @Test
    void testSelectByUsernameAndEmailLike2() {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(21L);
        umsAdmin.setUsername("est");
        umsAdmin.setPassword("1");
        umsAdmin.setEmail("com");
        umsAdmin.setNickName("hhh123");
        umsAdmin.setStatus(1);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setNote("note123");
        List<UmsAdmin> list = umsAdminDao.selectByUsernameAndEmailLike2(umsAdmin);
//        log.info("results: {}",list);
        for (UmsAdmin umsAdmin1 : list) {
            log.info(umsAdmin1.toString());
            System.out.println();
        }
    }

    @Test
    void testSelectByUsernameAndEmailLike3() {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(21L);
//        umsAdmin.setUsername("est");
        umsAdmin.setPassword("1");
//        umsAdmin.setEmail("com1");
        umsAdmin.setNickName("hhh123");
        umsAdmin.setStatus(1);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setNote("note123");
        List<UmsAdmin> list = umsAdminDao.selectByUsernameAndEmailLike3(umsAdmin);
//        log.info("results: {}",list);
        for (UmsAdmin umsAdmin1 : list) {
            log.info(umsAdmin1.toString());
            System.out.println();
        }
    }

    @Test
    void testUpdateByIdSelective() {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(21L);
        umsAdmin.setUsername("21");
        umsAdmin.setPassword("21");
        umsAdmin.setEmail("21com");
        umsAdmin.setNickName("21");
        umsAdmin.setStatus(1);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setNote("note123");
        int id = umsAdminDao.updateByIdSelective(umsAdmin);
        log.info("id :{}",id);
    }

    @Test
    void testInsertBatch() {
        List<UmsAdmin> umsAdmins = new ArrayList<>();
        UmsAdmin umsAdmin1 = new UmsAdmin();
        UmsAdmin umsAdmin2 = new UmsAdmin();
        UmsAdmin umsAdmin3 = new UmsAdmin();
        umsAdmin1.setUsername("user1");
        umsAdmin2.setUsername("user2");
        umsAdmin3.setUsername("user3");
        umsAdmins.add(umsAdmin1);
        umsAdmins.add(umsAdmin2);
        umsAdmins.add(umsAdmin3);
        int num = umsAdminDao.insertBatch(umsAdmins);
        log.info("num : {}", num);
    }

    @Test
    void testSelectByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(3L);
        ids.add(7L);
        ids.add(11L);
        ids.add(12L);
        System.out.println(ids);
        List<UmsAdmin> umsAdmins = umsAdminDao.selectByIds(ids);
        for (UmsAdmin umsAdmin : umsAdmins) {
            System.out.println(umsAdmin.toString());
        }
    }

    /*测试umsResoucrceDao*/
    @Test
    void testSelectUmsResourceExtByResourceId() {
        Long resourceId = 1L;
        UmsResourceExt umsResourceExt = umsResourceDao.selectUmsResourceExtByResourceId(resourceId);
        log.info("umsResourceExt: {}",umsResourceExt);
        System.out.println(umsResourceExt.toString());
    }

    @Test
    void testSelectUmsResourceExtByResourceId2() {
        Long resourceId = 8L;
        UmsResourceExt umsResourceExt = umsResourceDao.selectUmsResourceExtByResourceId2(resourceId);
        log.info("umsResourceExt: {}",umsResourceExt);
        System.out.println(umsResourceExt.toString());
    }

    @Test
    void testSelectUmsResourceExtByCategoryId() {
        Long categoryId = 5L;
        UmsResourceCategoryExt umsResourceCategoryExt = umsResourceCategoryDao.selectUmsResourceCategoryExtByCategoryId(categoryId);
        log.info("umsResourceCategoryExt: {}",umsResourceCategoryExt);
        System.out.println(umsResourceCategoryExt.toString());
    }

    @Test
    void testSelectUmsResourceExtByCategoryId2() {
        Long categoryId = 4L;
        UmsResourceCategoryExt umsResourceCategoryExt = umsResourceCategoryDao.selectUmsResourceCategoryExtByCategoryId2(categoryId);
        log.info("umsResourceCategoryExt: {}",umsResourceCategoryExt);
        System.out.println(umsResourceCategoryExt.toString());
    }

    /*分页查询*/
    @Test
    void testPageSearch() {
        int pageNum = 2;
        int pageSize = 2;
        int categoryId = 3;
        PageInfo<UmsResource> pageInfo = umsResourceService.pageSearch(pageNum, pageSize, categoryId);
        log.info("testPage total={},pages={},resourceList={}", pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());

    }


}
