package com.eqshen.keepsimple.zk.lock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 一个基于Zk的分布式锁
 * 仅仅是按照自己思路实现，练习而已；生产请使用Curator#InterProcessMutex
 * @author eqshen
 * @description
 * @date 2021/2/7
 */
@Slf4j
public class ZkLockUtil {

    private CuratorFramework zkClient;

    private static String X_LOCK_ROOT_PATH = "/x_lock_root_path";

    @SneakyThrows
    public ZkLockUtil(String url, int port,String username,String password){
        if(StringUtils.isBlank(url) || StringUtils.isEmpty(username)|| StringUtils.isEmpty(password)){
            throw new RuntimeException("参数非法");
        }

        zkClient = CuratorFrameworkFactory.builder()
                .connectString(url + ":" + port)
                .authorization("digest",(username+":"+password).getBytes())
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        zkClient.start();
        ZKPaths.mkdirs(zkClient.getZookeeperClient().getZooKeeper(), X_LOCK_ROOT_PATH);
        log.info(">>>>> ZkLockUtil started !!!");
    }


    /**
     * 创建锁，不阻塞
     * @param key
     * @param val
     * @return true = 成功
     */
    @SneakyThrows
    public boolean tryLock(String key,String val){
        Stat stat = zkClient.checkExists().forPath(ZKPaths.makePath(X_LOCK_ROOT_PATH,key));
        if(stat == null){
            try {
                String createdPath = zkClient.create()
                        .withMode(CreateMode.EPHEMERAL)
                        .forPath( ZKPaths.makePath(X_LOCK_ROOT_PATH,key), val.getBytes());
                log.info("Success to acquire lock:{}",key);
                return true;
            }catch (KeeperException.NodeExistsException e){

            }
        }
        return false;
    }

    @SneakyThrows
    public void lockWithBlock(String key,String value){
        String path = ZKPaths.makePath(X_LOCK_ROOT_PATH, key);
        AtomicBoolean tryFlag = new AtomicBoolean(false);

        if(this.tryLock(key,value)){
            log.info("Out-成功获取锁：{}",key);
            return;
        }

        Thread thread = new Thread(() -> {
            while (true) {
                log.info("等待重试获取锁：{}", key);
                if (tryFlag.get()) {
                    log.info("重试获取锁：{}", key);
                    if (tryLock(key, value)) {
                        log.info("In-成功获取锁：{}", key);
                        break;
                    }
                    tryFlag.set(false);
                }
                sleepSomeTime(500);
            }
        });
        thread.start();

        zkClient.watchers()
                .add()
                .withMode(AddWatchMode.PERSISTENT_RECURSIVE)
                .usingWatcher((CuratorWatcher) event -> {
                    if (Objects.equals(Watcher.Event.EventType.NodeDeleted, event.getType())) {
                        String deletedPath = event.getPath();
                        if(deletedPath.equals(path)){
                            //awake 重试获取锁
                            tryFlag.set(true);
                        }

                    }
                }).forPath(X_LOCK_ROOT_PATH);

        thread.join();
    }

    @SneakyThrows
    public boolean release(String key,String val){
        String path = ZKPaths.makePath(X_LOCK_ROOT_PATH, key);
        Stat stat = zkClient.checkExists().forPath(path);
        if(stat != null){
            byte[] bytes = zkClient.getData().forPath(path);
            String oldVal = new String(bytes);
            if(Objects.equals(val, oldVal)){
               zkClient.delete().forPath(path);
               return true;
            }
            return false;
        }
        return false;
    }

    @SneakyThrows
    private static void sleepSomeTime(int millSeconds){
        TimeUnit.MILLISECONDS.sleep(millSeconds);
    }

    @SneakyThrows
    public static void main(String[] args) {
        ZkLockUtil zkLockUtil = new ZkLockUtil("127.0.0.1",2181,"eqshen","123456");
        String key = "tom";
        String val = "123";
        System.out.println(zkLockUtil.tryLock(key, val));
        System.out.println(zkLockUtil.tryLock(key, val));
        System.out.println(zkLockUtil.release(key,val));
        System.out.println(zkLockUtil.release(key,"443r534"));
        Thread.sleep(3000);
        System.out.println("=====================");
        String key2 = "hendy";
        String val2 = "456";

        new Thread(new Runnable() {
            @Override
            public void run() {
                zkLockUtil.tryLock(key2,val2);
                log.info("get lock:{}",key2);
                sleepSomeTime(5000);
                zkLockUtil.release(key2,val2);
                log.info("release lock:{}",key2);
            }
        }).start();
        sleepSomeTime(1000);
        log.info("start ------->");
        zkLockUtil.lockWithBlock(key2,val2);
        log.info("end --------<");
    }
}
