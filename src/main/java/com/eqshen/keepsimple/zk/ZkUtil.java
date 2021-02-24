package com.eqshen.keepsimple.zk;

import com.eqshen.keepsimple.zk.dto.DataInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author eqshen
 * @description
 * @date 2021/2/3
 */
@Slf4j
public class ZkUtil {
    private ZooKeeper zk;

    public ZkUtil(String url){
        zk = this.connect(url);
    }

    @SneakyThrows
    public boolean exist(String path){
        Stat exists = zk.exists(path, false);
        return exists == null;
    }

    @SneakyThrows
    public DataInfo getData(String path){
        Stat stat = new Stat();
        byte[] data = zk.getData(path, false, stat);
        log.info("[getData] new stat:create:{},update:{},version:{}",stat.getCzxid(),stat.getMzxid(),stat.getVersion());
        return new DataInfo(stat.getVersion(),new String(data));
    }

    @SneakyThrows
    public  void updateData(String path,String data,int version){
        try{
            Stat stat = zk.setData(path, data.getBytes(), version);
            log.info("[updateData] new stat:create:{},update:{},version:{}",stat.getCzxid(),stat.getMzxid(),stat.getVersion());
        }catch (Exception e){
            log.error("[updateData] error:{}",e);
        }
    }


    @SneakyThrows
    public  void showChildren(String path,boolean watch){
        List<String> childrens;
        Stat stat = new Stat();
        if(watch){
            childrens = zk.getChildren(path, event -> log.info("=====>{} children changed",event.getPath()),stat);
        }else{
            childrens = zk.getChildren(path,watch,stat);
        }
        log.info("new stat:create:{},update:{},version:{}",stat.getCzxid(),stat.getMzxid(),stat.getVersion());
        //get children
        for (String children : childrens) {
            log.info("child ======== {}",children);
        }
    }

    @SneakyThrows
    public  void delete(String path){
        zk.delete(path,0);
        log.info("delete:{}",path);
    }

    public  void createPersistent(String path,String content){
        try {
            String s = zk.create(path, content.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            log.info("create persistent node success:{}",s);
        } catch (Exception e) {
            log.error("zk path create error",e);
        }
    }

    public  void createTemp(String path,String content){
        try {
            String s = zk.create(path, content.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            log.info("create temp node success:{}",s);
        } catch (Exception e) {
            log.error("zk path create error",e);
        }
    }

    public ZooKeeper connect(String url){
        ZooKeeper zooKeeper = null;
        try{
            zooKeeper = new ZooKeeper(url, 5000, event -> {
                log.info("receive event:{}",event);
            });
            //添加权限认证
            zooKeeper.addAuthInfo("digest","hendy:666".getBytes());

            ZooKeeper finalZooKeeper = zooKeeper;
            Thread thread = new Thread(() -> {
                while (true) {
                    ZooKeeper.States state = finalZooKeeper.getState();
                    log.info("state:{}",state);

                    sleep(1);
                    if(state.equals(ZooKeeper.States.CONNECTED)){
                        log.info("Connected...");
                        break;
                    }
                }
            });
            thread.start();
            thread.join();
        }catch (Exception e){
            log.error("connect error",e);
        }
        return zooKeeper;
    }

    public static void sleep(int sec){
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
