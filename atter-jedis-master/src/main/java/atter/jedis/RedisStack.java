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

import java.util.Stack;

/**
 * Description:  RedisStack define
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public class RedisStack<E> extends Stack<E> implements JedisAdapterAware, KeyedData {

    private JedisAdapter jedisAdapter;
    private String redisKey;

    public RedisStack(JedisAdapter jedisAdapter, String redisKey) {
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
    public Object push(Object item) {
        long l;
        if (item == null) {
            l = jedisAdapter.rpush(redisKey, null);
        }
        if (item instanceof String) {
            l = jedisAdapter.rpush(redisKey, String.valueOf(item));
        } else {
            l = jedisAdapter.rpush(redisKey, JSON.toJSONString(item));
        }
        return l > 0;
    }

    @Override
    public synchronized E pop() {
        return (E) jedisAdapter.rpop(redisKey);
    }

    @Override
    public boolean empty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized int search(Object o) {
        throw new UnsupportedOperationException();
    }
}
