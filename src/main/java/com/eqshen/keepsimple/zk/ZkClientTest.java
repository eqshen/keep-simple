package com.eqshen.keepsimple.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author eqshen
 * @description
 * @date 2021/2/4
 */
@Slf4j
public class ZkClientTest {

    private volatile static boolean flag = false;

    @SneakyThrows
    public static void main(String[] args) {
        flag = false;
        ZkClient zkClient = new ZkClient("localhost:2181", 5000);
        String path = "/class";
        if(!zkClient.exists(path)){
            zkClient.createPersistent(path,"class data");
        }


        zkClient.subscribeChildChanges(path, (s, list) -> {
            log.info("------> {} 's child changed",s);
            if(list != null){
                for (int i = 0; i < list.size(); i++) {
                    log.info("=======> current child {} is {}",i,list.get(i));
                }
            }
            flag = true;
        });

        String studentPath = zkClient.createEphemeralSequential(path + "/student", "class data");

        String deskPath = zkClient.createEphemeralSequential(path + "/desk", "desk data");

        Thread.sleep(1000);
        System.out.println("============================");
        zkClient.writeData(deskPath,"abcdefg");

        Object data = zkClient.readData(deskPath, true);
        log.info("read data:{}",data);

//        zkClient.deleteRecursive(path);
        barrier();
    }

    @SneakyThrows
    static void barrier(){
        while (!flag){
            Thread.sleep(100);
        }
        Thread.sleep(5000);
    }
}
