package com.zzy.malladmin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.common.JwtTokenUtil;
import com.zzy.malladmin.common.UmsAdminDetails;
import com.zzy.malladmin.dao.UmsAdminRoleRelationDao;
import com.zzy.malladmin.dto.UmsAdminLoginParam;
import com.zzy.malladmin.exception.Assert;
import com.zzy.malladmin.mbg.mapper.UmsAdminMapper;
import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.mbg.model.UmsAdminExample;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.service.UmsAdminService;
import com.zzy.malladmin.service.UmsRoleService;
import org.apache.tomcat.util.json.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName UmsAdminServiceImpl
 * @Author ZZy
 * @Date 2023/9/29 14:21
 * @Description
 * @Version 1.0
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UmsAdminMapper umsAdminMapper;

    @Autowired
    UmsRoleService umsRoleService;

    @Autowired
    UmsAdminRoleRelationDao adminRoleRelationDao;

    @Override
    public List<UmsAdmin> list(String keywords, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = umsAdminExample.createCriteria();
        if (!StrUtil.isEmpty(keywords)) {
            criteria.andUsernameLike("%" + keywords + "%");
            //caution   or
            umsAdminExample.or(umsAdminExample.createCriteria().andNickNameLike("%" + keywords + "%"));
        }
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(umsAdminExample);
        return umsAdmins;
    }

    @Override
    public UmsAdmin getItem(Long id) {
        //查询
        return umsAdminMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer add(UmsAdmin umsAdmin) {
        umsAdmin.setCreateTime(new Date());
        int count;
        try {
            count = umsAdminMapper.insert(umsAdmin);
        } catch (Exception e) {
            LOGGER.error("添加失败用户失败：{}", e.getMessage());
            count = -1;
        }
        return count;
    }

    @Override
    public Integer update(UmsAdmin umsAdmin) {
        int count;
        try {
            count = umsAdminMapper.updateByPrimaryKeySelective(umsAdmin);
        } catch (Exception e) {
            LOGGER.error("更新用户失败：{}", e.getMessage());
            count = -1;
        }
        return count;
    }

    @Override
    public Integer deleteById(Long id) {
        int count;
        try {
            count = umsAdminMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("删除用户信息失败：{}", e.getMessage());
            count = -1;
        }
        return count;
    }

    /**
     * 通过账号查看是否已经注册，若已经注册，返回约定的信息-1
     * 若未注册，设置加密密码、时间、状态值等需要初始化的内容
     * 调用mapper方法，向库中新增数据
     * @param umsAdmin
     * @return
     */
    @Override
    public int register(UmsAdmin umsAdmin) {
        int count = -1;
        //创建条件构造器
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = umsAdminExample.createCriteria();
        criteria.andUsernameLike(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(umsAdminExample);
        //查询用户是否存在 根据username
        //-1 : 改username已经存在
        if (umsAdminList != null && umsAdminList.size() > 0) {
            return -1;
        }
        umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
        umsAdmin.setCreateTime(new Date());
        try {
            count = umsAdminMapper.insert(umsAdmin);
        } catch (Exception e) {
            e.getMessage();
            LOGGER.error("注册失败：{}", umsAdmin);
        }
        //新增用户
        return count;
    }

    /**
     * 通过类中方法使用账号从数据库中获取加密的密码，同时调用passEncoder的matches(加密前，加密后)，判断密码是否正确
     * 设置上下文:将账号密码authentication放入到到上下文中
     * 调用JwtTokenUtil的generate方法，生成token（
     *                          需要：过期时间、claims、secret
     *                          生成token需要调用Jwts.builder传入claims：账号、创建时间，过期时间-使用有效期和当前时间、以及secret-配置文件、加密方法。）
     * 返回token
     * @param umsAdminLoginParam
     * @return
     */
    @Override
    public String login(UmsAdminLoginParam umsAdminLoginParam) {
        String token = null;
        try {
            //判断账号密码
            UserDetails userDetails = loadUserDetailByUsername(umsAdminLoginParam.getUsername());
            if (!passwordEncoder.matches(umsAdminLoginParam.getPassword(), userDetails.getPassword())) {
                Assert.fail("密码错误");
            }
            if (!userDetails.isEnabled()) {
                Assert.fail("账号失效");
            }
            //设置上下文
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            //创建token
            token = JwtTokenUtil.generateToken(userDetails);
            //写入登录日志 TODO
            //将token放置在缓存中 TODO
        } catch (Exception e) {
            e.getMessage();
            LOGGER.info(e.getMessage());
        }

        return token;

    }

    @Override
    public UmsAdmin geUserByUsername(String username) {
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = umsAdminExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(umsAdminExample);
        if (umsAdminList != null) {
            return umsAdminList.get(0);
        }
        return null;

    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        //先从缓存中获取 TODO

        //缓存中没有，从库中获取  adminRoleRelationDao adminId --> roleId --> resourceList
        List<UmsResource> resourceList = adminRoleRelationDao.getResourceList(adminId);
        //设置在缓存里 TODO
        return resourceList;
    }

    /**
     * 通过旧token刷新新的token：只有在旧token存在的情况下才可刷新token
     * 在service层仅简单调用JwtTokenUtil，由util实现token相关内容
     *
     * 从token中获取账号，调用generate覆盖新token
     * @param token
     * @return
     */
    @Override
    public String refreshToken(String token) {
        return JwtTokenUtil.refreshHeadToken(token);
    }

    private UserDetails loadUserDetailByUsername(String username) {
        //尝试从缓存中读取 TODO

        //根据username获取userAdmin
        UmsAdmin umsAdmin = geUserByUsername(username);
        if (umsAdmin != null) {
            List<UmsResource> resourceList = getResourceList(umsAdmin.getId());
            return new UmsAdminDetails(umsAdmin, resourceList);

        }
        throw new UsernameNotFoundException("用户名或者密码错误");
    }
}
