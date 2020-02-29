package com.eqshen.keepsimple.java.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: eqshen
 * @Description gc test
 * @Date: 2019/12/30 10:42
 */
@Slf4j
public class GCTest {

    private static List<GCData> cache = new LinkedList<>();

    public static void main(String[] args) throws InterruptedException {
        //CMS收集器，最大堆内存52m,
        //-Xmx52m -Xmn9m -Xss256k -XX:+PrintGC -XX:+UseConcMarkSweepGC
        log.info("GC test will begin...");
        Thread.sleep(5000);
        int count = 0;
        int byteSize = 1024*10;
        long totalSize = 0;
        while (true){
            GCData gcData = new GCData();
            gcData.setMsg("data-" + count);
            gcData.setData(new byte[byteSize]);//1 kb
            cache.add(gcData);
            count++;
            totalSize+=byteSize;
            double sizeKB = totalSize*1.0/1024;
            double sizeMB = sizeKB/1024;
            log.info("创建新对象 {},已占用内存 {} KB( {} MB)",count,sizeKB,sizeMB);
            Thread.sleep(100);

            if(sizeMB > 3){
                cache.clear();
                log.info("清空缓存，倾倒垃圾 {} MB",sizeMB);
                totalSize = 0;
            }
        }
    }

}

class GCData{
    private String msg;
    private byte[] data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
