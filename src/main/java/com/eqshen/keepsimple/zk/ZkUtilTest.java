package com.eqshen.keepsimple.zk;

import com.eqshen.keepsimple.zk.dto.DataInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author eqshen
 * @description
 * @date 2021/2/3
 */
@Slf4j
public class ZkUtilTest {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        ZkUtil zkUtil = new ZkUtil("127.0.0.1:2181");

        zkUtil.showChildren("/user",true);

        zkUtil.createTemp("/user/james","eee");

        zkUtil.showChildren("/user",false);

        zkUtil.createTemp("/user/test","dfe=efe");

        System.out.println("=====================");
        String userPath = "/user";
        DataInfo dataInfo = zkUtil.getData(userPath);
        log.info("before:{}",dataInfo);
        zkUtil.updateData(userPath,"new user data",dataInfo.getVersion());
        log.info("after1:{}",zkUtil.getData(userPath));

        zkUtil.updateData(userPath,"new user data",-1);
        log.info("after2:{}",zkUtil.getData(userPath));

        System.out.println("=====================");
        zkUtil.exist("/eatata");
    }


}
