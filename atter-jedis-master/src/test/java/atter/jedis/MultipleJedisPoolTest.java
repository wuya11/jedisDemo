package atter.jedis;

import redis.clients.jedis.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description:  多机分布式+连接池方式：
 * Copyright:  2018 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author wangling
 * @version 1.0
 */
public class MultipleJedisPoolTest {

    static JedisPoolConfig config;
    static ShardedJedisPool sharedJedisPool;

    static {
        // 生成多机连接List
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add( new JedisShardInfo("127.0.0.1", 6379) );
        shards.add( new JedisShardInfo("192.168.210.128", 6379) );

        // 初始化连接池配置对象
        config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(30);
        config.setMaxWaitMillis(2*1000);
        // 实例化连接池
        sharedJedisPool = new ShardedJedisPool(config, shards);

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 从连接池获取Jedis连接
        ShardedJedis shardedJedisConn = sharedJedisPool.getResource();
        shardedJedisConn.set("Lemon", "Hello,my name is Lemon");
        System.out.println(shardedJedisConn.get("Lemon"));
        // 释放连接
        close(shardedJedisConn, sharedJedisPool);

}

    private static void close(ShardedJedis shardedJedis,ShardedJedisPool sharedJedisPool){
        if(shardedJedis!=null &&sharedJedisPool!=null){
            sharedJedisPool.returnResource(shardedJedis);
        }
        if(sharedJedisPool!=null){
            sharedJedisPool.destroy();
        }
    }
}
