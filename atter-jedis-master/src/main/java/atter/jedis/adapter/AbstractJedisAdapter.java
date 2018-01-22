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
import redis.clients.util.Pool;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Description:  AbstractJedisAdapter
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public abstract class AbstractJedisAdapter<J extends JedisCommands & AutoCloseable> implements JedisAdapter {

    protected Pool<J> pool;

    /**
     * @param pool the pool to set
     */
    public void setPool(Pool<J> pool) {
        this.pool = pool;
    }

    /**
     * 在python中eval代表有返回值的执行，所以改用eval
     *
     * @param fun 执行表达式
     * @param <T> 返回数据泛型
     * @return
     */
    protected abstract <T> T eval(Function<J, T> fun);

    protected abstract void exec(Consumer<J> fun);


    /**
     * @see atter.jedis.adapter.AbstractJedisAdapter#set(java.lang.String, java.lang.String)
     */
    @Override
    public String set(String key, String value) {
        return eval(jedis -> jedis.set(key, value));
    }

    @Override
    public String get(String key) {
        return eval(jedis -> jedis.get(key));
    }


    @Override
    public String setex(String key, int seconds, String value) {
        return eval(jedis -> jedis.setex(key, seconds, value));
    }

    @Override
    public Long setnx(String key, String value) {
        return eval(jedis -> jedis.setnx(key, value));
    }

    public String set(String key, String value, String nxxx, String expx, long time) {
        return eval(jedis -> jedis.set(key, value, nxxx, expx, time));
    }

    public Boolean exists(String key) {
        return eval(jedis -> jedis.exists(key));
    }

    public Long persist(String key) {
        return eval(jedis -> jedis.persist(key));
    }

    public String type(String key) {
        return eval(jedis -> jedis.type(key));
    }

    public Long expire(String key, int seconds) {
        return eval(jedis -> jedis.expire(key, seconds));
    }

    public Long expireAt(String key, long unixTime) {
        return eval(jedis -> jedis.expireAt(key, unixTime));
    }

    public Long ttl(String key) {
        return eval(jedis -> jedis.ttl(key));
    }

    public Boolean setbit(String key, long offset, boolean value) {
        return eval(jedis -> jedis.setbit(key, offset, value));
    }

    public Boolean setbit(String key, long offset, String value) {
        return eval(jedis -> jedis.setbit(key, offset, value));
    }

    public Boolean getbit(String key, long offset) {
        return eval(jedis -> jedis.getbit(key, offset));
    }

    public Long setrange(String key, long offset, String value) {
        return eval(jedis -> jedis.setrange(key, offset, value));
    }

    public String getrange(String key, long startOffset, long endOffset) {
        return eval(jedis -> jedis.getrange(key, startOffset, endOffset));
    }

    public String getSet(String key, String value) {
        return eval(jedis -> jedis.getSet(key, value));
    }

    public Long decrBy(String key, long integer) {
        return eval(jedis -> jedis.decrBy(key, integer));
    }

    public Long decr(String key) {
        return eval(jedis -> jedis.decr(key));
    }

    public Long incrBy(String key, long integer) {
        return eval(jedis -> jedis.incrBy(key, integer));
    }

    public Long incr(String key) {
        return eval(jedis -> jedis.incr(key));
    }

    public Long append(String key, String value) {
        return eval(jedis -> jedis.append(key, value));
    }

    public String substr(String key, int start, int end) {
        return eval(jedis -> jedis.substr(key, start, end));
    }

    public Long hset(String key, String field, String value) {
        return eval(jedis -> jedis.hset(key, field, value));
    }

    public String hget(String key, String field) {
        return eval(jedis -> jedis.hget(key, field));
    }

    public Long hsetnx(String key, String field, String value) {
        return eval(jedis -> jedis.hsetnx(key, field, value));
    }

    public String hmset(String key, Map<String, String> hash) {
        return eval(jedis -> jedis.hmset(key, hash));
    }

    public List<String> hmget(String key, String... fields) {
        return eval(jedis -> jedis.hmget(key, fields));
    }

    public Long hincrBy(String key, String field, long value) {
        return eval(jedis -> jedis.hincrBy(key, field, value));
    }

    public Boolean hexists(String key, String field) {
        return eval(jedis -> jedis.hexists(key, field));
    }

    public Long hdel(String key, String... field) {
        return eval(jedis -> jedis.hdel(key, field));
    }

    public Long hlen(String key) {
        return eval(jedis -> jedis.hlen(key));
    }

    public Set<String> hkeys(String key) {
        return eval(jedis -> jedis.hkeys(key));
    }

    public List<String> hvals(String key) {
        return eval(jedis -> jedis.hvals(key));
    }

    public Map<String, String> hgetAll(String key) {
        return eval(jedis -> jedis.hgetAll(key));
    }

    public Long rpush(String key, String... string) {
        return eval(jedis -> jedis.rpush(key, string));
    }

    public Long lpush(String key, String... string) {
        return eval(jedis -> jedis.lpush(key, string));
    }

    public Long llen(String key) {
        return eval(jedis -> jedis.llen(key));
    }

    public List<String> lrange(String key, long start, long end) {
        return eval(jedis -> jedis.lrange(key, start, end));
    }

    public String ltrim(String key, long start, long end) {
        return eval(jedis -> jedis.ltrim(key, start, end));
    }

    public String lindex(String key, long index) {
        return eval(jedis -> jedis.lindex(key, index));
    }

    public String lset(String key, long index, String value) {
        return eval(jedis -> jedis.lset(key, index, value));
    }

    public Long lrem(String key, long count, String value) {
        return eval(jedis -> jedis.lrem(key, count, value));
    }

    public String lpop(String key) {
        return eval(jedis -> jedis.lpop(key));
    }

    public String rpop(String key) {
        return eval(jedis -> jedis.rpop(key));
    }

    public Long sadd(String key, String... member) {
        return eval(jedis -> jedis.sadd(key, member));
    }

    public Set<String> smembers(String key) {
        return eval(jedis -> jedis.smembers(key));
    }

    public Long srem(String key, String... member) {
        return eval(jedis -> jedis.srem(key, member));
    }

    public String spop(String key) {
        return eval(jedis -> jedis.spop(key));
    }

    public Long scard(String key) {
        return eval(jedis -> jedis.scard(key));
    }

    public Boolean sismember(String key, String member) {
        return eval(jedis -> jedis.sismember(key, member));
    }

    public String srandmember(String key) {
        return eval(jedis -> jedis.srandmember(key));
    }

    public List<String> srandmember(String key, int count) {
        return eval(jedis -> jedis.srandmember(key, count));
    }

    public Long strlen(String key) {
        return eval(jedis -> jedis.strlen(key));
    }

    public Long zadd(String key, double score, String member) {
        return eval(jedis -> jedis.zadd(key, score, member));
    }

    public Long zadd(String key, Map<String, Double> scoreMembers) {
        return eval(jedis -> jedis.zadd(key, scoreMembers));
    }

    public Set<String> zrange(String key, long start, long end) {
        return eval(jedis -> jedis.zrange(key, start, end));
    }

    public Long zrem(String key, String... member) {
        return eval(jedis -> jedis.zrem(key, member));
    }

    public Double zincrby(String key, double score, String member) {
        return eval(jedis -> jedis.zincrby(key, score, member));
    }

    public Long zrank(String key, String member) {
        return eval(jedis -> jedis.zrank(key, member));
    }

    public Long zrevrank(String key, String member) {
        return eval(jedis -> jedis.zrevrank(key, member));
    }

    public Set<String> zrevrange(String key, long start, long end) {
        return eval(jedis -> jedis.zrevrange(key, start, end));
    }

    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return eval(jedis -> jedis.zrangeWithScores(key, start, end));
    }

    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return eval(jedis -> jedis.zrevrangeWithScores(key, start, end));
    }

    public Long zcard(String key) {
        return eval(jedis -> jedis.zcard(key));
    }

    public Double zscore(String key, String member) {
        return eval(jedis -> jedis.zscore(key, member));
    }

    public List<String> sort(String key) {
        return eval(jedis -> jedis.sort(key));
    }

    public List<String> sort(String key, SortingParams sortingParameters) {
        return eval(jedis -> jedis.sort(key, sortingParameters));
    }

    public Long zcount(String key, double min, double max) {
        return eval(jedis -> jedis.zcount(key, min, max));
    }

    public Long zcount(String key, String min, String max) {
        return eval(jedis -> jedis.zcount(key, min, max));
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        return eval(jedis -> jedis.zrangeByScore(key, min, max));
    }

    public Set<String> zrangeByScore(String key, String min, String max) {
        return eval(jedis -> jedis.zrangeByScore(key, min, max));
    }

    public Set<String> zrevrangeByScore(String key, double max, double min) {
        return eval(jedis -> jedis.zrevrangeByScore(key, max, min));
    }

    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return eval(jedis -> jedis.zrangeByScore(key, min, max, offset, count));
    }

    public Set<String> zrevrangeByScore(String key, String max, String min) {
        return eval(jedis -> jedis.zrevrangeByScore(key, max, min));
    }

    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return eval(jedis -> jedis.zrangeByScore(key, min, max, offset, count));
    }

    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return eval(jedis -> jedis.zrevrangeByScore(key, max, min, offset, count));
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return eval(jedis -> jedis.zrangeByScoreWithScores(key, min, max));
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return eval(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min));
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return eval(jedis -> jedis.zrangeByScoreWithScores(key, min, max, offset, count));
    }

    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return eval(jedis -> jedis.zrevrangeByScore(key, max, min, offset, count));
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return eval(jedis -> jedis.zrangeByScoreWithScores(key, min, max));
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return eval(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min));
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return eval(jedis -> jedis.zrangeByScoreWithScores(key, min, max, offset, count));
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return eval(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min, offset, count));
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return eval(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min, offset, count));
    }

    public Long zremrangeByRank(String key, long start, long end) {
        return eval(jedis -> jedis.zremrangeByRank(key, start, end));
    }

    public Long zremrangeByScore(String key, double start, double end) {
        return eval(jedis -> jedis.zremrangeByScore(key, start, end));
    }

    public Long zremrangeByScore(String key, String start, String end) {
        return eval(jedis -> jedis.zremrangeByScore(key, start, end));
    }

    public Long zlexcount(String key, String min, String max) {
        return eval(jedis -> jedis.zlexcount(key, min, max));
    }

    public Set<String> zrangeByLex(String key, String min, String max) {
        return eval(jedis -> jedis.zrangeByLex(key, min, max));
    }

    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return eval(jedis -> jedis.zrangeByLex(key, min, max, offset, count));
    }

    public Long zremrangeByLex(String key, String min, String max) {
        return eval(jedis -> jedis.zremrangeByLex(key, min, max));
    }

    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        return eval(jedis -> jedis.linsert(key, where, pivot, value));
    }

    public Long lpushx(String key, String... string) {
        return eval(jedis -> jedis.lpushx(key, string));
    }

    public Long rpushx(String key, String... string) {
        return eval(jedis -> jedis.rpushx(key, string));
    }

    public List<String> blpop(String arg) {
        return eval(jedis -> jedis.blpop(arg));
    }

    public List<String> blpop(int timeout, String key) {
        return eval(jedis -> jedis.blpop(timeout, key));
    }

    public List<String> brpop(String arg) {
        return eval(jedis -> jedis.brpop(arg));
    }

    public List<String> brpop(int timeout, String key) {
        return eval(jedis -> jedis.brpop(timeout, key));
    }

    public Long del(String key) {
        return eval(jedis -> jedis.del(key));
    }

    public String echo(String string) {
        return eval(jedis -> jedis.echo(string));
    }

    public Long move(String key, int dbIndex) {
        return eval(jedis -> jedis.move(key, dbIndex));
    }

    public Long bitcount(String key) {
        return eval(jedis -> jedis.bitcount(key));
    }

    public Long bitcount(String key, long start, long end) {
        return eval(jedis -> jedis.bitcount(key, start, end));
    }

    public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        return eval(jedis -> jedis.hscan(key, cursor));
    }

    public ScanResult<String> sscan(String key, int cursor) {
        return eval(jedis -> jedis.sscan(key, cursor));
    }

    public ScanResult<Tuple> zscan(String key, int cursor) {
        return eval(jedis -> jedis.zscan(key, cursor));
    }

    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return eval(jedis -> jedis.hscan(key, cursor));
    }

    public ScanResult<String> sscan(String key, String cursor) {
        return eval(jedis -> jedis.sscan(key, cursor));
    }

    public ScanResult<Tuple> zscan(String key, String cursor) {
        return eval(jedis -> jedis.zscan(key, cursor));
    }

    public Long pfadd(String key, String... elements) {
        return eval(jedis -> jedis.pfadd(key, elements));
    }

    public long pfcount(String key) {
        return eval(jedis -> jedis.pfcount(key));
    }

//*********************************************

    public abstract Long del(String... keys);

    public abstract List<String> blpop(int timeout, String... keys);

    public abstract List<String> brpop(int timeout, String... keys);

    public abstract List<String> blpop(String... args);

    public abstract List<String> brpop(String... args);

    public abstract Set<String> keys(String pattern);

    public abstract List<String> mget(String... keys);

    public abstract String mset(String... keysvalues);

    public abstract Long msetnx(String... keysvalues);

    public abstract String rename(String oldkey, String newkey);

    public abstract Long renamenx(String oldkey, String newkey);

    public abstract String rpoplpush(String srckey, String dstkey);

    public abstract Set<String> sdiff(String... keys);

    public abstract Long sdiffstore(String dstkey, String... keys);

    public abstract Set<String> sinter(String... keys);

    public abstract Long sinterstore(String dstkey, String... keys);

    public abstract Long smove(String srckey, String dstkey, String member);

    public abstract Long sort(String key, SortingParams sortingParameters, String dstkey);

    public abstract Long sort(String key, String dstkey);

    public abstract Set<String> sunion(String... keys);

    public abstract Long sunionstore(String dstkey, String... keys);

    public abstract String watch(String... keys);

    public abstract String unwatch();

    public abstract Long zinterstore(String dstkey, String... sets);

    public abstract Long zinterstore(String dstkey, ZParams params, String... sets);

    public abstract Long zunionstore(String dstkey, String... sets);

    public abstract Long zunionstore(String dstkey, ZParams params, String... sets);

    public abstract String brpoplpush(String source, String destination, int timeout);

    public abstract Long publish(String channel, String message);

    public abstract void subscribe(JedisPubSub jedisPubSub, String... channels);

    public abstract void psubscribe(JedisPubSub jedisPubSub, String... patterns);

    public abstract String randomKey();

    public abstract Long bitop(BitOP op, String destKey, String... srcKeys);

    public abstract ScanResult<String> scan(int cursor);

    public abstract ScanResult<String> scan(String cursor);

    public abstract String pfmerge(String destkeyexecute, String... sourcekeys);

    public abstract long pfcount(String... keys);
}
