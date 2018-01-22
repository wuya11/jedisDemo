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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description:  RedisSet define
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public class RedisSet<E> implements SortSet<E>, JedisAdapterAware, KeyedData {

    private JedisAdapter jedisAdapter;
    private String redisKey;

    public RedisSet(JedisAdapter jedisAdapter, String redisKey) {
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
    public int zcard() {
        return jedisAdapter.zcard(redisKey).intValue();
    }

    @Override
    public Double zincrby(String key, Double increment) {
        return jedisAdapter.zincrby(redisKey, increment, key);
    }

    @Override
    public Set<String> zrange(int begin, int end) {
        return jedisAdapter.zrange(redisKey, begin, end);
    }

    @Override
    public Set<String> zrevrange(int begin, int end) {
        return jedisAdapter.zrevrange(redisKey, begin, end);
    }

    @Override
    public int zrank(String key) {
        return jedisAdapter.zrank(redisKey, key).intValue();
    }

    @Override
    public boolean zrem(String... key) {
        return jedisAdapter.zrem(redisKey, key) > 0;
    }

    @Override
    public Double zscore(String key) {
        return jedisAdapter.zscore(redisKey, key);
    }

    @Override
    public boolean zadd(String key, Double score) {
        Map<String, Double> scoreMembers = new HashMap<>();
        scoreMembers.put(key, score);
        return jedisAdapter.zadd(redisKey, scoreMembers) > 0;
    }

    @Override
    public boolean zaddAll(Map<String, Double> scoreMembers) {
        return jedisAdapter.zadd(redisKey, scoreMembers) > 0;
    }

    @Override
    public int zcount(E left, E right) {
        int returnCount = 0;

        if (left instanceof String && right instanceof String) {
            returnCount = jedisAdapter.zcount(redisKey, (String) left, (String) right).intValue();
        }

        if (left instanceof Double && right instanceof Double) {
            returnCount = jedisAdapter.zcount(redisKey, (Double) left, (Double) right).intValue();
        }

        return returnCount;
    }


}
