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
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 /**
 * Description:  RedisList define
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public class RedisMap implements CacheMap, JedisAdapterAware, KeyedData {

    private JedisAdapter jedisAdapter;
    private String redisKey;

    public RedisMap(JedisAdapter jedisAdapter, String redisKey) {
        this.jedisAdapter = jedisAdapter;
        this.redisKey = redisKey;
    }

    @Override
    public void setJedisAdapter(JedisAdapter jedisAdapter) {
        this.jedisAdapter = jedisAdapter;
    }

    @Override
    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    @Override
    public String getRedisKey() {
        return redisKey;
    }


    @Override
    public String get(String key) {
        return jedisAdapter.hget(redisKey, key);
    }

    @Override
    public Object put(String field, Object value) {
        Long l;
        if (value == null) {
            l = jedisAdapter.hset(redisKey, field, null);
        }
        if (value instanceof String) {
            l = jedisAdapter.hset(redisKey, field, String.valueOf(value));
        } else {
            l = jedisAdapter.hset(redisKey, field, JSON.toJSONString(value));
        }
        return l > 0 ? value : null;

    }

    @Override
    public boolean delete(String key) {
        Long result = jedisAdapter.hdel(redisKey, key);

        return result > 0;
    }

    public boolean deleteBatch(String... fields) {
        Long result = jedisAdapter.hdel(redisKey, fields);
        return result > 0;
    }

    @Override
    public void putAll(Map map) {
        jedisAdapter.hmset(redisKey, map);
    }

    public List<String> getAll(String... fields) {
        return jedisAdapter.hmget(redisKey, fields);
    }

    /**
     * 缓存勾搭数据源查询，混合使用方法
     * 如果缓存为null，则查询数据源，缓存内容，并返回内容
     * 如果缓存不为null，则返回内容
     *
     * @param key      缓存key
     * @param supplier 数据源处理表达式
     * @return
     */
    @Override
    public <T> T cacheQuery(String key, Class<T> clazz, Supplier<T> supplier) {
        T t = JSON.parseObject(get(key), clazz);
        if (t == null) {
            t = supplier.get();
            put(key, JSON.toJSONString(t));
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
        String str = get(key);
        List t = JSON.parseArray(str, clazz);
        if (t == null) {
            t = supplier.get();
            put(key, JSON.toJSONString(t));
        }
        return t;
    }


}
