package com.eqshen.keepsimple.redisson;

import com.eqshen.keepsimple.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedissonTest extends BaseTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testMap() throws InterruptedException {
        final RMap<String, String> rmap = redissonClient.getMap("test-map");
        rmap.put("eqshen","666");
        rmap.put("tom","777");

        LocalCachedMapOptions options = LocalCachedMapOptions.defaults()
                // 用于淘汰清除本地缓存内的元素
                // 共有以下几种选择:
                // LFU - 统计元素的使用频率，淘汰用得最少（最不常用）的。
                // LRU - 按元素使用时间排序比较，淘汰最早（最久远）的。
                // SOFT - 元素用Java的WeakReference来保存，缓存元素通过GC过程清除。
                // WEAK - 元素用Java的SoftReference来保存, 缓存元素通过GC过程清除。
                // NONE - 永不淘汰清除缓存元素。
                .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.NONE)
                // 如果缓存容量值为0表示不限制本地缓存容量大小
                .cacheSize(1000)
                // 以下选项适用于断线原因造成了未收到本地缓存更新消息的情况。
                // 断线重连的策略有以下几种：
                // CLEAR - 如果断线一段时间以后则在重新建立连接以后清空本地缓存
                // LOAD - 在服务端保存一份10分钟的作废日志
                //        如果10分钟内重新建立连接，则按照作废日志内的记录清空本地缓存的元素
                //        如果断线时间超过了这个时间，则将清空本地缓存中所有的内容
                // NONE - 默认值。断线重连时不做处理。
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE)
                // 以下选项适用于不同本地缓存之间相互保持同步的情况
                // 缓存同步策略有以下几种：
                // INVALIDATE - 默认值。当本地缓存映射的某条元素发生变动时，同时驱逐所有相同本地缓存映射内的该元素
                // UPDATE - 当本地缓存映射的某条元素发生变动时，同时更新所有相同本地缓存映射内的该元素
                // NONE - 不做任何同步处理
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.INVALIDATE)
                // 或者
                .timeToLive(10, TimeUnit.SECONDS)
                // 或者
                .maxIdle(10, TimeUnit.SECONDS);

        String localCacheKey = "local-cache-map-1";
        final RLocalCachedMap localCachedMap = redissonClient.getLocalCachedMap(localCacheKey, options);
        final RLocalCachedMap localCachedMap2 = redissonClient.getLocalCachedMap(localCacheKey, options);

        String strKey = "username";

        localCachedMap.put(strKey,"eqshen");

        log.info("map1 :{}",localCachedMap.get(strKey));
        log.info("map2 :{}",localCachedMap2.get(strKey));

        log.info("map2 change strKey from:{} to {}","eqshen","tom");
        localCachedMap2.put(strKey,"tom");

        log.info("map1 real:{},should:{}",localCachedMap.get(strKey),"tom");
        log.info("map2 real:{},should:{}",localCachedMap2.get(strKey),"tom");

        //Thread.sleep(1000*6);
        System.out.println("========1s later");
        log.info("map1 real:{},should:{}",localCachedMap.get(strKey),"tom");
        log.info("map2 real:{},should:{}",localCachedMap2.get(strKey),"tom");

    }
}
