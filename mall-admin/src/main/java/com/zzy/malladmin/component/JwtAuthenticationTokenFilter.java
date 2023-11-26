package com.zzy.malladmin.component;

import com.zzy.malladmin.common.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Author ZZy
 * @Date 2023/10/19 21:36
 * @Description 用户名和密码校验前添加的过滤器
 * @Version 1.0
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            String token = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUsername(token);
            LOGGER.info("doFilterInternal check username: {}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //TODO
                //根据账号从数据库中获取用户detail
                UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
                //jwtTokenUtil校验token和userDetails
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    //通过：建一个springToken 设置到securityContext中
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info("authenticated user:{}", userDetails.getUsername());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }

        }
        filterChain.doFilter(request, response);

    }
}
