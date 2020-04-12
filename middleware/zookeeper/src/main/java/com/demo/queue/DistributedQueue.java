package com.demo.queue;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author jam
 * @date 2020/4/12 9:50 AM
 */
public class DistributedQueue {

    private static final Logger LOG = LoggerFactory.getLogger(DistributedQueue.class);

    private final String dir;

    private ZooKeeper zookeeper;
    private List<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;

    private final String prefix = "qn-";

    public DistributedQueue(ZooKeeper zookeeper, String dir, List<ACL> acl){
        this.dir = dir;

        if(acl != null){
            this.acl = acl;
        }
        this.zookeeper = zookeeper;

    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String hostPort = "localhost:2183";
        String dir = "/testOffer1";
        String testString = "Hello World";

        ZooKeeper zk = new ZooKeeper(hostPort, 3000, null);
        DistributedQueue distributedQueue = new DistributedQueue(zk, dir, null);
        distributedQueue.offer(testString.getBytes());
        System.out.println("offer: "+testString);
        System.out.println("remove: "+new String(distributedQueue.remove()));
    }

    /**
     * Inserts data into queue.
     * @param data
     * @return true if data was successfully added
     */
    public boolean offer(byte[] data) throws KeeperException, InterruptedException{
        for(;;){
            try{
                zookeeper.create(dir+"/"+prefix, data, acl, CreateMode.PERSISTENT_SEQUENTIAL);
                return true;
            }catch(KeeperException.NoNodeException e){
                zookeeper.create(dir, new byte[0], acl, CreateMode.PERSISTENT);
            }
        }

    }

    /**
     * Return the head of the queue without modifying the queue.
     * @return the data at the head of the queue.
     * @throws NoSuchElementException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] element() throws NoSuchElementException, KeeperException, InterruptedException {
        TreeMap<Long,String> orderedChildren;

        // element, take, and remove follow the same pattern.
        // We want to return the child node with the smallest sequence number.
        // Since other clients are remove()ing and take()ing nodes concurrently,
        // the child with the smallest sequence number in orderedChildren might be gone by the time we check.
        // We don't call getChildren again until we have tried the rest of the nodes in sequence order.
        while(true){
            try{
                orderedChildren = orderedChildren(null);
            }catch(KeeperException.NoNodeException e){
                throw new NoSuchElementException();
            }
            if(orderedChildren.size() == 0 ) throw new NoSuchElementException();

            for(String headNode : orderedChildren.values()){
                if(headNode != null){
                    try{
                        return zookeeper.getData(dir+"/"+headNode, false, null);
                    }catch(KeeperException.NoNodeException e){
                        //Another client removed the node first, try next
                    }
                }
            }

        }
    }

    /**
     * Returns a Map of the children, ordered by id.
     * @param watcher optional watcher on getChildren() operation.
     * @return map from id to child name for all children
     */
    private TreeMap<Long,String> orderedChildren(Watcher watcher) throws KeeperException, InterruptedException {
        TreeMap<Long,String> orderedChildren = new TreeMap<Long,String>();

        List<String> childNames = null;
        try{
            childNames = zookeeper.getChildren(dir, watcher);
        }catch (KeeperException.NoNodeException e){
            throw e;
        }

        for(String childName : childNames){
            try{
                //Check format
                if(!childName.regionMatches(0, prefix, 0, prefix.length())){
                    LOG.warn("Found child node with improper name: " + childName);
                    continue;
                }
                String suffix = childName.substring(prefix.length());
                Long childId = Long.parseLong(suffix);
                orderedChildren.put(childId,childName);
            }catch(NumberFormatException e){
                LOG.warn("Found child node with improper format : " + childName + " " + e,e);
            }
        }

        return orderedChildren;
    }

    /**
     * Attempts to remove the head of the queue and return it.
     * @return The former head of the queue
     * @throws NoSuchElementException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] remove() throws NoSuchElementException, KeeperException, InterruptedException {
        TreeMap<Long,String> orderedChildren;
        // Same as for element.  Should refactor this.
        while(true){
            try{
                orderedChildren = orderedChildren(null);
            }catch(KeeperException.NoNodeException e){
                throw new NoSuchElementException();
            }
            if(orderedChildren.size() == 0) throw new NoSuchElementException();

            for(String headNode : orderedChildren.values()){
                String path = dir +"/"+headNode;
                try{
                    byte[] data = zookeeper.getData(path, false, null);
                    zookeeper.delete(path, -1);
                    return data;
                }catch(KeeperException.NoNodeException e){
                    // Another client deleted the node first.
                }
            }

        }
    }

    /**
     * Removes the head of the queue and returns it, blocks until it succeeds.
     * @return The former head of the queue
     * @throws NoSuchElementException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] take() throws KeeperException, InterruptedException {
        TreeMap<Long,String> orderedChildren;
        // Same as for element.  Should refactor this.
        while(true){
            LatchChildWatcher childWatcher = new LatchChildWatcher();
            try{
                orderedChildren = orderedChildren(childWatcher);
            }catch(KeeperException.NoNodeException e){
                zookeeper.create(dir, new byte[0], acl, CreateMode.PERSISTENT);
                continue;
            }
            if(orderedChildren.size() == 0){
                childWatcher.await();
                continue;
            }

            for(String headNode : orderedChildren.values()){
                String path = dir +"/"+headNode;
                try{
                    byte[] data = zookeeper.getData(path, false, null);
                    zookeeper.delete(path, -1);
                    return data;
                }catch(KeeperException.NoNodeException e){
                    // Another client deleted the node first.
                }
            }
        }
    }

    /**
     * Returns the data at the first element of the queue, or null if the queue is empty.
     * @return data at the first element of the queue, or null.
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] peek() throws KeeperException, InterruptedException{
        try{
            return element();
        }catch(NoSuchElementException e){
            return null;
        }
    }

    /**
     * Attempts to remove the head of the queue and return it. Returns null if the queue is empty.
     * @return Head of the queue or null.
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] poll() throws KeeperException, InterruptedException {
        try{
            return remove();
        }catch(NoSuchElementException e){
            return null;
        }
    }

    private static class LatchChildWatcher implements Watcher {

        CountDownLatch latch;

        public LatchChildWatcher(){
            latch = new CountDownLatch(1);
        }

        @Override
        public void process(WatchedEvent event){
            LOG.debug("Watcher fired on path: " + event.getPath() + " state: " +
                    event.getState() + " type " + event.getType());
            latch.countDown();
        }
        public void await() throws InterruptedException {
            latch.await();
        }
    }
}
