package com.zzy.malladmin.service.impl;

import com.zzy.malladmin.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisServiceImpl
 * @Author ZZy
 * @Date 2023/10/25 21:36
 * @Description
 * @Version 1.0
 */
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object val, Long time) {
        redisTemplate.opsForValue().set(key, val, time, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long del(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    //Attention  单位：秒
    @Override
    public Boolean expire(String key, Long expire) {
//        Object object = redisTemplate.opsForValue().get(key);
//        redisTemplate.opsForValue().set(key, object, expire);
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public Long getExpire(String key) {
//        return redisTemplate.getExpire(key);
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    //TODO attention
    @Override
    public Boolean hasKey(String key) {
//        redisTemplate.opsForHash().get
//        return redisTemplate.opsForHash().hasKey(key);
        return redisTemplate.hasKey(key);
    }

    @Override
    public Long incr(String key, int delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long decr(String key, Long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }


    @Override
    public Boolean hSet(String key, String hashKey, Object val, Long time) {
//        return redisTemplate.opsForHash()
        redisTemplate.opsForHash().put(key, hashKey, val);
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    @Override
    public Boolean hSetAll(String key, Map<String, Object> map, Long time) {
//        return redisTemplate.opsForHash().put(key,map,);
        redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }

    @Override
    public void hSetAll(String key, Map<String, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    //Attention 直接取entries()
    @Override
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public void del(String key, Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    @Override
    public Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    @Override
    public Long hIncr(String key,String hashKey, Integer delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    @Override
    public Long hDecr(String key, String hashKey,Integer delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    @Override
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Long sAdd(String key, Long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        expire(key, time);
        return count;
    }

    @Override
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    @Override
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    @Override
    public Long lPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    //返回index
    @Override
    public Long lPush(String key, Object value, long time) {
        Long index = redisTemplate.opsForList().rightPush(key, value);
        expire(key, time);
        return index;
    }

    //返回count
    @Override
    public Long lPushAll(String key, long time, Object... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        expire(key, time);
        return count;
    }

    @Override
    public Long lPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public Long lRemove(String key, int count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }
}
