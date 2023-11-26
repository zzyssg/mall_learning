package com.zzy.malladmin.common;

import com.zzy.malladmin.mbg.model.UmsAdmin;
import com.zzy.malladmin.mbg.model.UmsResource;
import lombok.Builder;
import lombok.Data;
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
@Data
@Builder
public class AdminUserDetails implements UserDetails {

    //用户
    private final UmsAdmin umsAdmin;
    //资源列表
    private final List<UmsResource> umsResourceList;


    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsResource> umsResourceList) {
        this.umsAdmin = umsAdmin;
        this.umsResourceList = umsResourceList;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return umsResourceList.stream()
                .map(resource -> new SimpleGrantedAuthority(resource.getId() + ":" + resource.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }


    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "AdminUserDetails{" +
                "umsAdmin=" + umsAdmin +
                ", umsResourceList=" + umsResourceList +
                '}';
    }
}
