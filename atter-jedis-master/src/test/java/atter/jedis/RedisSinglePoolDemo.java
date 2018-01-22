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
 * Description:  reidis单机连接池模式
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
/** 声明spring主配置文件位置，注意：以当前测试类的位置为基准,有多个配置文件以字符数组声明 **/
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class RedisSinglePoolDemo {

    @Autowired
    private CacheTemplate cacheTemplate;

    public void setCacheTemplate(CacheTemplate cacheTemplate) {
        this.cacheTemplate = cacheTemplate;
    }

    private String orderId = "order001";
    private String orderId1 = "order002";
    private String orderId2 = "order003";
    private String orderId3 = "order004";
    private String key = "orderSet";

//    @After
//    public void afterTest() {
//        cacheTemplate.del(orderId);
//        cacheTemplate.del(orderId1);
//        cacheTemplate.del(orderId2);
//        cacheTemplate.del(orderId3);
//        cacheTemplate.del(key);
//    }
    @Test
    public void testGetList() {
        String order = "orderABC";
        cacheTemplate.getList(orderId).add(order);
        List<String> savedOrder = cacheTemplate.getList(orderId).lrange(0, -1);

        assertNotNull(savedOrder);
        //断言
        assertEquals("保存的订单", order, savedOrder.get(0));

    }

    @Test
    public void testGet() {

        String order = "orderABC";
        cacheTemplate.set(orderId, order);
        String savedOrder = (String) cacheTemplate.get(orderId);
        //断言
        assertEquals("保存的订单", order, savedOrder);
    }

    @Test
    public void testPutAll() {
        String order = "orderABC";
        String order1 = "orderABCD";
        cacheTemplate.mset(orderId, order, orderId1, order1);
        List<String> savedOrder = cacheTemplate.mget(orderId, orderId1);

        for (int i = 0, len = savedOrder.size(); i < len; i++) {
            assertEquals("保存的订单", order, savedOrder.get(0));
            assertEquals("保存的订单", order1, savedOrder.get(1));

        }
        //断言
    }

    @Test
    public void testSetnx() {


        String order = "orderABC";
        cacheTemplate.setnx(orderId, order);
        String savedOrder = (String) cacheTemplate.get(orderId);

        //断言
        assertEquals("保存的订单", order, savedOrder);
        cacheTemplate.del(orderId);
    }

    @Test
    public void testSetex() {

        String order = "orderABC";
        cacheTemplate.setex(orderId, order, 1);
        String savedOrder = (String) cacheTemplate.get(orderId);

        //断言
        assertEquals("保存的订单", order, savedOrder);
        cacheTemplate.del(orderId);
    }

    @Test
    public void testEpire() {

        Long k = cacheTemplate.expire(orderId, 1);

        //断言
        assertNotNull(k);
    }

    @Test
    public void testExist() {

        cacheTemplate.set(orderId, "1");
        Boolean isSuccess = cacheTemplate.exists(orderId);
        //断言
        assertTrue(isSuccess);
    }

    @Test
    public void testDel() {

        cacheTemplate.set(orderId, "1");
        Boolean isSuccess = cacheTemplate.del(orderId);
        //断言
        assertTrue(isSuccess);
    }

    @Test
    public void testType() {

        String order = "order001";
        cacheTemplate.set(orderId, order);
        String type = cacheTemplate.type(orderId);
        //断言
        assertTrue(type instanceof String);
    }

    @Test
    public void testIncr() {

        String order = "1";
        cacheTemplate.set(orderId, order);
        cacheTemplate.incr(orderId);
        String savedOrder = (String) cacheTemplate.get(orderId);

        //断言
        assertEquals("保存的订单", "2", savedOrder);
    }

    @Test
    public void testDecr() {

        String order = "3";
        cacheTemplate.set(orderId, order);
        cacheTemplate.decr(orderId);
        String savedOrder = (String) cacheTemplate.get(orderId);

        //断言
        assertEquals("保存的订单", "2", savedOrder);
        cacheTemplate.del(orderId);
    }


    @Test
    public void testGetMap() {

        String order = "orderABC";
        String field = "haha";
        RedisMap map = cacheTemplate.getMap(orderId);
        map.put(field, order);
        String savedOrder = map.get(field);

        //断言
        assertEquals("保存的订单", order, savedOrder);
    }

    @Test
    public void testGetAll() {

        String order = "orderABC";
        Map<String, String> map = new HashMap<>();
        map.put("orderMap2", "orderABC");
        map.put("orderMap3", "orderABCD");
        map.put("orderMap4", "orderABCDE");
        RedisMap redisMap = (RedisMap) cacheTemplate.getMap(orderId);
        redisMap.putAll(map);
        List<String> savedOrder = redisMap.getAll("orderMap2", "orderMap3", "orderMap4");

        //断言
        for (int i = 0, len = savedOrder.size(); i < len; i++) {
            assertEquals("保存的订单", order, savedOrder.get(0));
            assertEquals("保存的订单", "orderABCD", savedOrder.get(1));
            assertEquals("保存的订单", "orderABCDE", savedOrder.get(2));
        }
    }

    @Test
    public void testQueue() {

        String key = "orderQueue";
        String orderId = "orderQueue1";
        String order = "orderABC";
        String orderId1 = "orderQueue2";
        String order1 = "orderABC";
        String orderId2 = "orderQueue3";
        String order2 = "orderABC";
        RedisQueue queue = cacheTemplate.getQueue(key);
        queue.push(order);
        queue.push(order1);
        queue.push(order2);
        queue.push(orderId);
        queue.push(orderId1);
        queue.push(orderId2);
        for (int i = 0; i < 6; i++) {
            String savedOrder = (String) queue.lpop();
            //断言
            switch (i) {
                case 0:
                    assertEquals("保存的订单", order, savedOrder);
                    break;
                case 1:
                    assertEquals("保存的订单", order1, savedOrder);
                    break;
                case 2:
                    assertEquals("保存的订单", order2, savedOrder);
                    break;
                case 3:
                    assertEquals("保存的订单", orderId, savedOrder);
                    break;
                case 4:
                    assertEquals("保存的订单", orderId1, savedOrder);
                    break;
                case 5:
                    assertEquals("保存的订单", orderId2, savedOrder);
                    break;
            }
        }

    }

    @Test
    public void testStack() {

        String key = "orderStack";
        String orderId = "orderStack1";
        String order = "orderABC";
        String orderId1 = "orderStack2";
        String order1 = "orderABC";
        String orderId2 = "orderStack3";
        String order2 = "orderABC";
        Stack stack = cacheTemplate.getStack(key);
        stack.push(order);
        stack.push(order1);
        stack.push(order2);
        stack.push(orderId);
        stack.push(orderId1);
        stack.push(orderId2);
        for (int i = 0; i < 6; i++) {
            String savedOrder = (String) stack.pop();
            //断言
            switch (i) {
                case 0:
                    assertEquals("保存的订单", orderId2, savedOrder);
                    break;
                case 1:
                    assertEquals("保存的订单", orderId1, savedOrder);
                    break;
                case 2:
                    assertEquals("保存的订单", orderId, savedOrder);
                    break;
                case 3:
                    assertEquals("保存的订单", order2, savedOrder);
                    break;
                case 4:
                    assertEquals("保存的订单", order1, savedOrder);
                    break;
                case 5:
                    assertEquals("保存的订单", order, savedOrder);
                    break;
            }
        }

    }

    @Test
    public void testSet() {

        Map<String, Double> map = new HashMap<>();

        String order = "orderABC";
        String order1 = "orderABC";
        String order2 = "orderABC";
        map.put(orderId, 11d);
        map.put(orderId1, 12d);
        map.put(orderId2, 13d);
        map.put(order, 14d);
        map.put(order1, 15d);
        map.put(order2, 16d);
        RedisSet set = cacheTemplate.getSortSet(key);
        boolean isSuccess = set.zaddAll(map);
        assertEquals("插入集合失败", true, isSuccess);
        boolean isSuccessAdd = set.zadd(orderId3, 17d);
        assertEquals("插入集合失败", true, isSuccessAdd);
        int size = set.zcard();
        assertNotNull(size);
        assertEquals("获取长度失败", 5, size);
        Set<String> set1 = set.zrange(0, -1);
        assertNotNull("获取set失败", set1);
        int countBetween = set.zcount(10d, 18d);
        assertEquals("获取区域长度失败", countBetween, size);
        Double result = set.zincrby(order, 1d);
        assertEquals("增加数值失败", result, 17d);
        int index = set.zrank(order);
        assertEquals("获取位置失败", index, 4);
        boolean isremove = set.zrem(order1, order2);
        assertEquals("删除失败", true, isremove);
        Set<String> set2 = set.zrevrange(0, -1);
        assertNotNull("获取set失败", set2);

    }

    @Test
    public void testLock() {
        String ip = "127.0.0.1";
        boolean respModel = cacheTemplate.lock("haha", "hehe", "xixi");
        assertEquals("上锁不成功", true, respModel);
        boolean respModel1 = cacheTemplate.lockEx("haha", "hehe", "xixi");
        assertEquals("上锁不成功", true, respModel1);
        boolean respModel2 = cacheTemplate.unLock("haha", "hehe", "xixi");
        assertEquals("解锁不成功", true, respModel2);
    }

    @Test
    public void cacheSelectWithNullInit() {
        cacheTemplate.cacheQuery(orderId, String.class, () -> cacheTemplate.get(orderId1));
    }

    @Test
    public void cacheSelectWithInit() {
        cacheTemplate.set(orderId1, "2323");
        cacheTemplate.cacheQuery(orderId, String.class, () -> cacheTemplate.get(orderId1));
    }
}
