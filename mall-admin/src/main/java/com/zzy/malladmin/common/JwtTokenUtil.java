package com.zzy.malladmin.common;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.DateParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JwtTokenUtils
 * @Author ZZy
 * @Date 2023/10/6 18:51
 * @Description
 * @Version 1.0
 */
@Component
public class JwtTokenUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";


    @Value("${jwt.tokenHead}")
    private  String head;

    @Value("${jwt.secret}")
    private  String secret;

    @Value("${jwt.expiration}")
    private  Long expiration;

    public  String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    private  Date generateDate() {
//        Date date = new Date();
        //TODO DateUtil.millisecond() 和 System.currentTimeMillis 的区别
//        int milli1 = DateUtil.millisecond(date);
        long mill2 = System.currentTimeMillis();
        //TODO 报错：jwt过期 at 19770-01-08
        return new Date(System.currentTimeMillis() + expiration * 1000);

    }

    public  String generateToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 替代service层实现刷新token的功能
     * 判断token的合法性
     * 判断是否刚刚刷新过，如果30min内刷过，则不用再刷了
     * 调用generate(Claims)方法，重新生成token
     *
     * @param oldToken
     * @return
     */
    public  String refreshHeadToken(String oldToken) {
        //判断合法性
        if (oldToken == null) {
            return null;
        }
        //判断是否过期
        if (isTokenExpired(oldToken)) {
            return null;
        }
        //从token中获取负载
        Claims claims = getClaimsFromToken(oldToken);
        if (claims == null) {
            return null;
        }
        if (isTokenRefreshJustBefore(oldToken, 30 * 60)) {
            //是否在30min内刷新过
            return oldToken;
        } else {
            //重新生成token
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }

    }

    //create   <   cur   <  create +  已创建时间  : createTime在30min内生成
    private  boolean isTokenRefreshJustBefore(String token, int seconds) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date curDate = new Date();
        //created  expiration   create + seconds
        if (created.before(curDate) && curDate.before(DateUtil.offsetSecond(created, seconds))) {
            return true;
        }
        return false;

    }

    private  Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {

            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        } catch (Exception e) {
            e.getMessage();
            LOGGER.info("解析token出现错误");
        }
        return claims;
    }

    //expiration
    private  boolean isTokenExpired(String oldToken) {
        Date expiration = getExpiration(oldToken);
        boolean isExpired =  expiration.before(new Date());
        return isExpired;
    }

    private  Date getExpiration(String token) {
        Claims claim = getClaimsFromToken(token);
        return claim.getExpiration();
    }


    public String getUsername(String token) {
        String username = null;
        Claims claimsFromToken = getClaimsFromToken(token);
        if (claimsFromToken != null) {
            username = claimsFromToken.getSubject();
        }
        return username;
    }

    /**
     *
     * @param token 请求传入的token
     * @param userDetails 数据库中查询的user
     * @return
     */
    //校验 传入的token 和 账户是否合法
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }
}
