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

import atter.jedis.cache.CacheTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.*;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Description:  redis测试模式
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public class RedisTestDemo {
    @Test
    public void redisBaseTest() {
        Jedis jedis = new Jedis("192.168.210.128", 6379);
        jedis.set("singleJedis", "hello jedis!");
        System.out.println(jedis.get("singleJedis"));
        jedis.close();
    }
    @Test
    public void jedisTrans() {
        Jedis jedis = new Jedis("192.168.210.128", 6379);
        long start = System.currentTimeMillis();
        Transaction tx = jedis.multi();
        for (int i = 0; i < 1000; i++) {
            tx.set("t" + i, "t" + i);
        }
        List<Object> results = tx.exec();
        long end = System.currentTimeMillis();
        System.out.println("Transaction SET: " + ((end - start)/1000.0) + " seconds");
        jedis.disconnect();
    }
    @Test
    public void jedisPipelined() {
        Jedis jedis = new Jedis("192.168.210.128", 6379);
        Pipeline pipeline = jedis.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            pipeline.set("p" + i, "p" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined SET: " + ((end - start)/1000.0) + " seconds");
        jedis.disconnect();
    }

    @Test
    public void jedisShardPipelinedPool() {
        List<JedisShardInfo> shards = Arrays.asList(
                new JedisShardInfo("192.168.210.128",6379),
                new JedisShardInfo("192.168.210.128",6380));
        ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(), shards);
        ShardedJedis one = pool.getResource();
        ShardedJedisPipeline pipeline = one.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            pipeline.set("sppn" + i, "n" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Pipelined@Pool SET: " + ((end - start)/1000.0) + " seconds");
        pool.destroy();
    }
}
