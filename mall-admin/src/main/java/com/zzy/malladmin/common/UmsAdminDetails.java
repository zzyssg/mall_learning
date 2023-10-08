package com.zzy.malladmin.common;

import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.mbg.model.UmsResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName AdminUserDetails
 * @Author ZZy
 * @Date 2023/10/6 17:41
 * @Description
 * @Version 1.0
 */
public class UmsAdminDetails implements UserDetails {

    //用户
    private final UmsAdmin umsAdmin;
    //资源列表
    private final List<UmsResource> umsResourceList;

    public UmsAdminDetails(UmsAdmin umsAdmin, List<UmsResource> umsResourceList) {
        this.umsAdmin = umsAdmin;
        this.umsResourceList = umsResourceList;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return umsResourceList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getId() + ":" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
