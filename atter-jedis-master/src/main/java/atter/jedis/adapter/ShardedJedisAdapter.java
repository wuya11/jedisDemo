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
package atter.jedis.adapter;

import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Description:  ShardedJedisAdapter
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public class ShardedJedisAdapter extends AbstractJedisAdapter<ShardedJedis> {

    public <T> T eval(Function<ShardedJedis, T> fun) {
        try (ShardedJedis jedis = pool.getResource()) {
            return fun.apply(jedis);
        }
    }

    public void exec(Consumer<ShardedJedis> fun) {
        try (ShardedJedis jedis = pool.getResource()) {
            fun.accept(jedis);
        }
    }


    //////////////////


    public Long del(String... keys) {
        throw new UnsupportedOperationException();
    }

    public List<String> blpop(int timeout, String... keys) {
        throw new UnsupportedOperationException();
    }

    public List<String> brpop(int timeout, String... keys) {
        throw new UnsupportedOperationException();
    }

    public List<String> blpop(String... args) {
        throw new UnsupportedOperationException();
    }

    public List<String> brpop(String... args) {
        throw new UnsupportedOperationException();
    }

    public Set<String> keys(String pattern) {
        throw new UnsupportedOperationException();
    }

    public List<String> mget(String... keys) {
        throw new UnsupportedOperationException();
    }

    public String mset(String... keysvalues) {
        throw new UnsupportedOperationException();
    }

    public Long msetnx(String... keysvalues) {
        throw new UnsupportedOperationException();
    }

    public String rename(String oldkey, String newkey) {
        throw new UnsupportedOperationException();
    }

    public Long renamenx(String oldkey, String newkey) {
        throw new UnsupportedOperationException();
    }

    public String rpoplpush(String srckey, String dstkey) {
        throw new UnsupportedOperationException();
    }

    public Set<String> sdiff(String... keys) {
        throw new UnsupportedOperationException();
    }

    public Long sdiffstore(String dstkey, String... keys) {
        throw new UnsupportedOperationException();
    }

    public Set<String> sinter(String... keys) {
        throw new UnsupportedOperationException();
    }

    public Long sinterstore(String dstkey, String... keys) {
        throw new UnsupportedOperationException();
    }

    public Long smove(String srckey, String dstkey, String member) {
        throw new UnsupportedOperationException();
    }

    public Long sort(String key, SortingParams sortingParameters, String dstkey) {
        throw new UnsupportedOperationException();
    }

    public Long sort(String key, String dstkey) {
        throw new UnsupportedOperationException();
    }

    public Set<String> sunion(String... keys) {
        throw new UnsupportedOperationException();
    }

    public Long sunionstore(String dstkey, String... keys) {
        throw new UnsupportedOperationException();
    }

    public String watch(String... keys) {
        throw new UnsupportedOperationException();
    }

    public String unwatch() {
        throw new UnsupportedOperationException();
    }

    public Long zinterstore(String dstkey, String... sets) {
        throw new UnsupportedOperationException();
    }

    public Long zinterstore(String dstkey, ZParams params, String... sets) {
        throw new UnsupportedOperationException();
    }

    public Long zunionstore(String dstkey, String... sets) {
        throw new UnsupportedOperationException();
    }

    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        throw new UnsupportedOperationException();
    }

    public String brpoplpush(String source, String destination, int timeout) {
        throw new UnsupportedOperationException();
    }

    public Long publish(String channel, String message) {
        throw new UnsupportedOperationException();
    }

    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        throw new UnsupportedOperationException();
    }

    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        throw new UnsupportedOperationException();
    }

    public String randomKey() {
        throw new UnsupportedOperationException();
    }

    public Long bitop(BitOP op, String destKey, String... srcKeys) {
        throw new UnsupportedOperationException();
    }

    public ScanResult<String> scan(int cursor) {
        throw new UnsupportedOperationException();
    }

    public ScanResult<String> scan(String cursor) {
        throw new UnsupportedOperationException();
    }

    public String pfmerge(String destkey, String... sourcekeys) {
        throw new UnsupportedOperationException();
    }

    public long pfcount(String... keys) {
        throw new UnsupportedOperationException();
    }

}
