/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License")); you may not use this file except in compliance with
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
 * Description:  SingleJedisAdapter
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public class SingleJedisAdapter extends AbstractJedisAdapter<Jedis> {
    private int database;

    /**
     * @param database the database to set
     */
    public void setDatabase(int database) {
        this.database = database;
    }


    /**
     * 装饰器封装获取连接池对象，释放连接
     *
     * @param fun 在数据库上执行的操作
     * @param <T> 操作的返回类型
     * @return 操作的结果
     */
    public <T> T eval(Function<Jedis, T> fun) {
        try (Jedis jedis = pool.getResource()) {
            jedis.select(database);
            return fun.apply(jedis);
        }
    }

    public void exec(Consumer<Jedis> fun) {
        try (Jedis jedis = pool.getResource()) {
            jedis.select(database);
            fun.accept(jedis);
        }
    }


    public Long del(String... keys) {
        return eval(jedis -> jedis.del(keys));
    }

    public List<String> blpop(int timeout, String... keys) {
        return eval(jedis -> jedis.blpop(timeout, keys));
    }

    public List<String> brpop(int timeout, String... keys) {
        return eval(jedis -> jedis.brpop(timeout, keys));
    }

    public List<String> blpop(String... args) {
        return eval(jedis -> jedis.blpop(args));
    }

    public List<String> brpop(String... args) {
        return eval(jedis -> jedis.brpop(args));
    }

    public Set<String> keys(String pattern) {
        return eval(jedis -> jedis.keys(pattern));
    }

    public List<String> mget(String... keys) {
        return eval(jedis -> jedis.mget(keys));
    }

    public String mset(String... keysvalues) {
        return eval(jedis -> jedis.mset(keysvalues));
    }

    public Long msetnx(String... keysvalues) {
        return eval(jedis -> jedis.msetnx(keysvalues));
    }

    public String rename(String oldkey, String newkey) {
        return eval(jedis -> jedis.rename(oldkey, newkey));
    }

    public Long renamenx(String oldkey, String newkey) {
        return eval(jedis -> jedis.renamenx(oldkey, newkey));
    }

    public String rpoplpush(String srckey, String dstkey) {
        return eval(jedis -> jedis.rpoplpush(srckey, dstkey));
    }

    public Set<String> sdiff(String... keys) {
        return eval(jedis -> jedis.sdiff(keys));
    }

    public Long sdiffstore(String dstkey, String... keys) {
        return eval(jedis -> jedis.sdiffstore(dstkey, keys));
    }

    public Set<String> sinter(String... keys) {
        return eval(jedis -> jedis.sinter(keys));
    }

    public Long sinterstore(String dstkey, String... keys) {
        return eval(jedis -> jedis.sinterstore(dstkey, keys));
    }

    public Long smove(String srckey, String dstkey, String member) {
        return eval(jedis -> jedis.smove(srckey, dstkey, member));
    }

    public Long sort(String key, SortingParams sortingParameters, String dstkey) {
        return eval(jedis -> jedis.sort(key, sortingParameters, dstkey));
    }

    public Long sort(String key, String dstkey) {
        return eval(jedis -> jedis.sort(key, dstkey));
    }

    public Set<String> sunion(String... keys) {
        return eval(jedis -> jedis.sunion(keys));
    }

    public Long sunionstore(String dstkey, String... keys) {
        return eval(jedis -> jedis.sunionstore(dstkey, keys));
    }

    public String watch(String... keys) {
        return eval(jedis -> jedis.watch(keys));
    }

    public String unwatch() {
        return eval(jedis -> jedis.unwatch());
    }

    public Long zinterstore(String dstkey, String... sets) {
        return eval(jedis -> jedis.zinterstore(dstkey, sets));
    }

    public Long zinterstore(String dstkey, ZParams params, String... sets) {
        return eval(jedis -> jedis.zinterstore(dstkey, params, sets));
    }

    public Long zunionstore(String dstkey, String... sets) {
        return eval(jedis -> jedis.zunionstore(dstkey, sets));
    }

    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        return eval(jedis -> jedis.zunionstore(dstkey, params, sets));
    }

    public String brpoplpush(String source, String destination, int timeout) {
        return eval(jedis -> jedis.brpoplpush(source, destination, timeout));
    }

    public Long publish(String channel, String message) {
        return eval(jedis -> jedis.publish(channel, message));
    }

    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        exec(jedis -> jedis.subscribe(jedisPubSub, channels));
    }

    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        exec(jedis -> jedis.psubscribe(jedisPubSub, patterns));
    }

    public String randomKey() {
        return eval(jedis -> jedis.randomKey());
    }

    public Long bitop(BitOP op, String destKey, String... srcKeys) {
        return eval(jedis -> jedis.bitop(op, destKey, srcKeys));
    }

    public ScanResult<String> scan(int cursor) {
        return eval(jedis -> jedis.scan(cursor));
    }

    public ScanResult<String> scan(String cursor) {
        return eval(jedis -> jedis.scan(cursor));
    }

    public String pfmerge(String destkeyexecute, String... sourcekeys) {
        return eval(jedis -> jedis.pfmerge(destkeyexecute, sourcekeys));
    }

    public long pfcount(String... keys) {
        return eval(jedis -> jedis.pfcount(keys));
    }


}
