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

import java.util.AbstractList;
import java.util.List;

/**
 * Description:  RedisList define
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public class RedisList<E> extends AbstractList<E> implements JedisAdapterAware, KeyedData {

    private String key;
    private JedisAdapter jedisAdapter;


    public RedisList(JedisAdapter jedisAdapter, String redisKey) {
        this.jedisAdapter = jedisAdapter;
        this.key = redisKey;
    }

    /* (non-Javadoc)
     * @see atter.jedis.JedisAdapterAware#setJedisAdapter(atter.jedis.adapter.JedisAdapter)
     */
    @Override
    public void setJedisAdapter(JedisAdapter jedisAdapter) {
        this.jedisAdapter = jedisAdapter;
    }

    @Override
    public void setRedisKey(String redisKey) {
        this.key = redisKey;
    }

    /**
     * @see KeyedData#getRedisKey()
     */
    @Override
    public String getRedisKey() {
        return key;
    }

    /**
     * 添加元素置尾端
     *
     * @see java.util.AbstractList#add(java.lang.Object)
     */
    @Override
    public boolean add(E e) {
        long l;
        if (e == null) {
            l = jedisAdapter.rpush(key, null);
        }
        if (e instanceof String) {
            l = jedisAdapter.rpush(key, String.valueOf(e));
        } else {
            l = jedisAdapter.rpush(key, JSON.toJSONString(e));
        }
        return l > 0;
    }

    /**
     * 获取列表指定位置的值
     *
     * @see java.util.AbstractList#get(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        return (E) jedisAdapter.lindex(key, index);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#set(int, java.lang.Object)
     */
    @Override
    public E set(int index, E element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#subList(int, int)
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return (List<E>) jedisAdapter.lrange(key, fromIndex, toIndex);
    }

    public List<String> lrange(int fromIndex, int toIndex) {
        return (List<String>) subList(fromIndex, toIndex);
    }


    /* (non-Javadoc)
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public int size() {
        // TODO Auto-generated method stub
        return jedisAdapter.llen(key).intValue();
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

}
