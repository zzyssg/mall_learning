package com.zzy.boot_bootis.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName AdminUserDetails
 * @Author ZZy
 * @Date 2023/9/4 23:37
 * @Description
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class AdminUserDetails
//        implements UserDetails
{

    private String username;
    private String password;
    private List<String> authorityList;

//    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

//    @Override
    public String getPassword() {
        return this.password;
    }

//    @Override
    public String getUsername() {
        return this.username;
    }

//    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

//    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

//    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

//    @Override
    public boolean isEnabled() {
        return false;
    }
}
