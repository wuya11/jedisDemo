/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package atter.jedis;

import atter.jedis.adapter.JedisAdapter;
import atter.jedis.cache.CacheTemplate;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.function.Supplier;

/**
 * Description:  RedisCacheTemplate define
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public class RedisCacheTemplate implements CacheTemplate {

    private JedisAdapter jedisAdapter;

    private int lockTimeout;  //ip锁，默认超时10秒

    /**
     * @param jedisAdapter the jedisAdapter to set
     */
    public void setJedisAdapter(JedisAdapter jedisAdapter) {
        this.jedisAdapter = jedisAdapter;

    }

    /**
     * 缓存勾搭数据源查询，混合使用方法
     * 如果缓存为null，则查询数据源，缓存内容，并返回内容
     * 如果缓存不为null，则返回内容
     *
     * @param key      缓存key
     * @param clazz    返回类型
     * @param supplier 数据源处理表达式
     * @param <T>
     * @return
     */
    @Override
    public <T> T cacheQuery(String key, Class<T> clazz, Supplier<T> supplier) {
        return cacheQuery(key, clazz, supplier, -1);
    }

    /**
     * 缓存勾搭数据源查询，混合使用方法
     * 如果缓存为null，则查询数据源，缓存内容设置超时时间，并返回内容
     * 如果缓存不为null，则返回内容
     *
     * @param key           缓存key
     * @param clazz         返回类型
     * @param expireSeconds 超时时间
     * @param supplier      数据源处理表达式
     * @return
     */
    @Override
    public <T> T cacheQuery(String key, Class<T> clazz, Supplier<T> supplier, int expireSeconds) {
        T t = JSON.parseObject(get(key), clazz);
        if (t == null) {
            t = supplier.get();
            if (expireSeconds == -1) {
                set(key, JSON.toJSONString(t));
            } else {
                setex(key, JSON.toJSONString(t), expireSeconds);
            }
        }
        return t;
    }

    /**
     * 缓存勾搭数据源查询，混合使用方法
     * 如果缓存为null，则查询数据源，缓存内容设置超时时间，并返回内容
     * 如果缓存不为null，则返回内容
     *
     * @param key      缓存key
     * @param clazz    返回类型
     * @param supplier 数据源处理表达式
     * @return
     */
    @Override
    public List cacheQueryList(String key, Class clazz, Supplier<List> supplier) {
        return cacheQueryList(key, clazz, supplier, -1);
    }

    /**
     * 缓存勾搭数据源查询，混合使用方法
     * 如果缓存为null，则查询数据源，缓存内容设置超时时间，并返回内容
     * 如果缓存不为null，则返回内容
     *
     * @param key           缓存key
     * @param clazz         返回类型
     * @param supplier      数据源处理表达式
     * @param expireSeconds 超时时间
     * @return
     */
    @Override
    public List cacheQueryList(String key, Class clazz, Supplier<List> supplier, int expireSeconds) {
        String str = get(key);
        List t = JSON.parseArray(str, clazz);
        if (t == null) {
            t = supplier.get();
            if (expireSeconds == -1) {
                set(key, JSON.toJSONString(t));
            } else {
                setex(key, JSON.toJSONString(t), expireSeconds);
            }
        }
        return t;
    }

    /**
     * @see atter.jedis.cache.CacheTemplate#getList(java.lang.String)
     */
    @Override
    public <T> RedisList<T> getList(String key) {
        RedisList<T> redisList = new RedisList<>(jedisAdapter, key);
        return redisList;
    }

    @Override
    public Long incr(String key) {
        return jedisAdapter.incr(key);
    }

    @Override
    public Long decr(String key) {
        return jedisAdapter.decr(key);
    }

    @Override
    public void mset(String... keys) {
        jedisAdapter.mset(keys);
    }

    @Override
    public List<String> mget(String... keys) {
        return jedisAdapter.mget(keys);
    }

    @Override
    public String get(String key) {
        return jedisAdapter.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return JSON.parseObject(get(key), clazz);
    }

    @Override
    public <T> void setnx(String key, T value) {
        if (value == null)
            jedisAdapter.setnx(key, null);
        if (value instanceof String) {
            jedisAdapter.setnx(key, String.valueOf(value));
        } else {
            jedisAdapter.setnx(key, JSON.toJSONString(value));
        }
    }

    @Override
    public <T> void setex(String key, T value, int seconds) {
        if (value == null)
            jedisAdapter.setex(key, seconds, null);
        if (value instanceof String) {
            jedisAdapter.setex(key, seconds, String.valueOf(value));
        } else {
            jedisAdapter.setex(key, seconds, JSON.toJSONString(value));
        }
    }

    @Override
    public <T> void set(String key, T value) {
        if (value == null)
            jedisAdapter.set(key, null);
        if (value instanceof String) {
            jedisAdapter.set(key, String.valueOf(value));
        } else {
            jedisAdapter.set(key, JSON.toJSONString(value));
        }
    }

    @Override
    public RedisMap getMap(String key) {
        RedisMap redisMap = new RedisMap(jedisAdapter, key);
        return redisMap;
    }

    @Override
    public Boolean lock(String business, String actionName, String message) {

        String key = business + actionName + message;

        String value = business + "|" + actionName + "|" + actionName;

        if (jedisAdapter.setnx(key, value) != 0) {
            jedisAdapter.expire(key, lockTimeout);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean lock(String business, String actionName, String message, int second) {

        boolean isExist = false;

        String key = business + actionName + message;

        String value = business + "|" + actionName + "|" + actionName;

        lockTimeout = second;

        isExist = jedisAdapter.setnx(key, value) == 0;
        if (!isExist) {
            jedisAdapter.expire(key, lockTimeout);
        }
        return isExist;

    }

    @Override
    public Boolean lockEx(String business, String actionName, String message) {

        String key = business + actionName + message;

        if (jedisAdapter.exists(key)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean unLock(String business, String actionName, String message) {

        String key = business + actionName + message;

        if (jedisAdapter.del(key) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public RedisQueue getQueue(String key) {
        RedisQueue redisQueue = new RedisQueue(jedisAdapter, key);
        return redisQueue;
    }

    @Override
    public RedisStack<?> getStack(String key) {
        // TODO Auto-generated method stub
        RedisStack redisStack = new RedisStack(jedisAdapter, key);
        return redisStack;
    }

    @Override
    public RedisSet getSortSet(String key) {
        RedisSet redisSet = new RedisSet(jedisAdapter, key);
        return redisSet;
    }

    @Override
    public Boolean begin() {
        return false;
    }

    @Override
    public Boolean commit() {
        return false;
    }

    @Override
    public Boolean rollback() {
        return false;
    }

    @Override
    public Boolean del(String key) {
        return jedisAdapter.del(key) > 0;
    }

    @Override
    public Long expire(String key, int seconds) {
        return jedisAdapter.expire(key, seconds);
    }

    @Override
    public String type(String key) {
        return jedisAdapter.type(key);
    }

    @Override
    public Boolean exists(String key) {
        return jedisAdapter.exists(key);
    }

    public int getLockTimeout() {
        return lockTimeout;
    }

    public void setLockTimeout(int lockTimeout) {
        this.lockTimeout = lockTimeout;
    }
}
