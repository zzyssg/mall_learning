package com.zzy.boot_bootis.service;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName RedisService
 * @Author ZZy
 * @Date 2023/9/9 17:06
 * @Description Redis操作接口
 * @Version 1.0
 */
public interface RedisService {

    /**
     * 设置属性
     * @Param time 时间为S
     */
    void set(String key, Object value, long time);

    void set(String key, Object value);

    Boolean expire(String key, long time);

    /**
     * 获取属性
     */
    Object get(String key);

    Long getExpire(String key);

    /**
     * 删除属性
     */
    Boolean del(String key);

    Long del(List<String> keys);

    /**
     * 判断是否有改属性
     */
    Boolean hasKey(String key);


    /**
     * delta递增
     */
    Long incr(String key, long delta);

    /**
     * delta递减
     */
    Long decr(String key, long delta);


    /**
     *Hash 结构
     */

    /**
     * 获取Hash结构中的属性
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     */
    Boolean hSetAll(String key, Map<String,Object> value, long time);

    /**
     * 直接设置整个Hash结构
     */
    void hSetAll(String key, Map<String, Object> value);

    /**
     * 删除Hash结构中的属性
     */
    void hDel(String key, Object... hashKeys);

    /**
     * 判断Hash结构中是否有该属性
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     */
    Long hDecr(String key, String hashKey, Long delta);


    /**
     * 获取Set结构
     */
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     */
    Boolean isSMembers(String key ,Object value);

    /**
     * 获取Set结构的长度
     */
    Long sSize(String key);


    /**
     * 删除Set结构中的属性
     */
    Long sRemove(String key,Object... values);


    /**
     * 获取List结构中的属性
     */
    List<Object> lRange(String key,long startIndex,long endIndex);

    /**
     * 获取List结构的长度
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     */
    Object lIndex(String key,long lIndex);

    /**
     * 向List结构中添加属性
     */
    Long lPush(String key,Object value);

    /**
     * 向List结构中添加属性
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     */
    Long lPushAll(String key,  Long time,Object... values);

    /**
     * 从List结构中移除属性
     */
    Long lRemove(String key, long count,Object value);

}
