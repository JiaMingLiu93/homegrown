package com.demo.test;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author jam
 * @date 2020/4/13 8:32 PM
 */
public class ApiTest {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("localhost:2183", 3000, null);
//        String s = zk.create("/apiTest", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println(s);
        byte[] data = zk.getData("/apiTest", false, null);
        System.out.println(new String(data));
    }
}
