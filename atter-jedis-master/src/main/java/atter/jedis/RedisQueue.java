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

/**
 * Description:  RedisQueue define
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public class RedisQueue<E> implements JedisAdapterAware, KeyedData, Queue<E> {

    private JedisAdapter jedisAdapter;
    private String redisKey;

    public RedisQueue(JedisAdapter jedisAdapter, String redisKey) {
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
    public boolean push(E o) {
        long l;
        if (o == null) {
            l = jedisAdapter.rpush(redisKey, null);
        }
        if (o instanceof String) {
            l = jedisAdapter.rpush(redisKey, String.valueOf(o));
        } else {
            l = jedisAdapter.rpush(redisKey, JSON.toJSONString(o));
        }
        return l > 0;
    }

    @Override
    public E lpop() {
        return (E) jedisAdapter.lpop(redisKey);
    }

}
