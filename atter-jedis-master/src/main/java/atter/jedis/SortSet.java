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

import java.util.Map;
import java.util.Set;

/**
 * Description:  SortSet define
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public interface SortSet<E> {

    boolean zaddAll(Map<String, Double> element);

    int zcount(E left, E right);

    int zcard();

    Double zincrby(String key, Double increment);

    /**
     * <p>
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     * 其中成员的位置按 score 值递增(从小到大)来排序。
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列。
     * 超出范围的下标并不会引起错误。
     * </p>
     */
    Set<String> zrange(int begin, int end);

    Set<String> zrevrange(int begin, int end);

    int zrank(String key);

    boolean zrem(String... key);

    Double zscore(String key);

    boolean zadd(String key, Double score);


}
