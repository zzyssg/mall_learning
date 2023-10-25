package com.zzy.malladmin.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName RedisService
 * @Author ZZy
 * @Date 2023/10/25 16:32
 * @Description
 * @Version 1.0
 */
public interface RedisService {

    //保存属性
    void set(String key, Object val, Long time);

    void set(String key, Object val);

    //获取属性
    Object get(String key);

    //删除属性
    Boolean del(String key);
    //批量删除
    Long del(List<String> keys);

    //过期时间
    Boolean expire(String key, Long expire);

    Long getExpire(String key);

    //是否有属性
    Boolean hasKey(String key);

    //按delta
    Long incr(String key, Long delta);

    Long decr(String key, Long delta);

    //Hash结构
    Boolean hSet(String key, String hashKey, Object val, Long time);

    Boolean hSetAll(String key, Map<String, Object> map, Long time);

    void hSetAll(String key, Map<String, ?> map);

    Object hGet(String key,String hashKey);

    Map<Object, Object> hGetAll(String key);

    void del(String key, Object... hashKey);

    Boolean hHasKey(String key, String hashKey);

    //hash结构中，属性递增
    Long hIncr(String key,String hashKey, Integer delta);

    Long hDecr(String key,String hashKey, Integer delta);

    //set结构
    Set<Object> sMembers(String key);

    Long sAdd(String key, Object... values);

    Long sAdd(String key, Long time, Object... values);

    //是否为set中属性
    Boolean sIsMember(String key, Object value);

    //获取set结构的长度
    Long sSize(String key);

    //删除set结构中的属性
    Long sRemove(String key, Object... values);

    //获取list结构中的属性
    List<Object> lRange(String key, long start, long end);

    //获取list结构中的size
    Long lSize(String key);

    Object lIndex(String key, long  index);

    Long lPush(String key, Object value);

    Long lPush(String key, Object value, long time);

    Long lPushAll(String key, long time, Object... values);

    Long lPushAll(String key, Object... values);

    Long lRemove(String key, int count, Object value);





















}
