package com.demo.middleware.canal.connection;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;

import java.net.InetSocketAddress;

/**
 * @author youyu
 * @date 2020/6/10 10:49 AM
 */
public class ConnectTest {
    public static void main(String[] args) throws InterruptedException {
        CanalConnector canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",11111), "test", "", "");
        canalConnector.connect();
        canalConnector.subscribe("parana-item.parana_item");
        canalConnector.rollback();
        while (true){
            Message msg = canalConnector.getWithoutAck(100);
            System.out.println(msg);
            Thread.sleep(3000);
        }
    }
}
