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

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Description:  CacheMap define
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public interface CacheMap {
    String get(String key);

    Object put(String field, Object value);

    boolean delete(String key);

    boolean deleteBatch(String... fields);

    void putAll(Map map);

    List<String> getAll(String... fields);

    /**
     * 缓存勾搭数据源查询，混合使用方法
     * 如果缓存为null，则查询数据源，缓存内容，并返回内容
     * 如果缓存不为null，则返回内容
     *
     * @param key      缓存key
     * @param clazz    返回类型
     * @param supplier 数据源处理表达式
     * @param <T>
     * @return
     */
    <T> T cacheQuery(String key, Class<T> clazz, Supplier<T> supplier);

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
    List cacheQueryList(String key, Class clazz, Supplier<List> supplier);
}
