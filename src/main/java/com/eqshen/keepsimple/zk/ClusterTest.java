package com.eqshen.keepsimple.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.test.InstanceSpec;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;

/**
 * @author eqshen
 * @description
 * @date 2021/2/19
 */
@Slf4j
public class ClusterTest {
    @SneakyThrows
    public static void main(String[] args) {
        TestingCluster cluster = new TestingCluster(3);
        cluster.start();
        Thread.sleep(2000);
        TestingZooKeeperServer leader = null;
        for (TestingZooKeeperServer server : cluster.getServers()) {
            InstanceSpec instanceSpec = server.getInstanceSpec();
            log.info("serverId:{},port:{},serverState:{}", instanceSpec.getServerId(),instanceSpec.getPort(),server.getQuorumPeer().getServerState());
            log.info("dataPath:{}",instanceSpec.getDataDirectory().getAbsolutePath());
            log.info("================");
            if(server.getQuorumPeer().getServerState().equals("leading")){
                leader = server;
            }
        }

        leader.kill();
        log.info("========after leader killed");
        for (TestingZooKeeperServer server : cluster.getServers()) {
            InstanceSpec instanceSpec = server.getInstanceSpec();
            log.info("serverId:{},port:{},serverState:{}", instanceSpec.getServerId(),instanceSpec.getPort(),server.getQuorumPeer().getServerState());
            log.info("dataPath:{}",instanceSpec.getDataDirectory().getAbsolutePath());
            log.info("================");
            if(server.getQuorumPeer().getServerState().equals("leading")){
                leader = server;
            }
        }
        cluster.stop();

    }
}
