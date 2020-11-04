package com.demo.watcher;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.*;

/**
 * @author jam
 * @date 2020/4/11 7:53 PM
 */
public class Executor implements Runnable, Watcher,DataMonitor.DataMonitorListener {
    private DataMonitor dm;
    private Process child;
    private String pathname;
    private String exec[];
    private ZooKeeper zk;

    public Executor(String hostPort, String znode, String filename, String exec[])
            throws KeeperException, IOException {
        this.pathname = filename;
        this.exec = exec;
        zk = new ZooKeeper(hostPort, 3000, this);
        dm = new DataMonitor(zk, znode, this);
    }

    public static void main(String[] args) {
//        if (args.length < 4) {
//            System.err.println("USAGE: Executor hostPort znode pathname program [args ...]");
//            System.exit(2);
//        }
//        String hostPort = args[0];
        String hostPort = "localhost:2183";
//        String znode = args[1];
        String znode = "/watch";
//        String filename = args[2];
        String filename = "/Users/jam/company/code/homegrown/middleware/zookeeper/data/znode-data";
//        String exec[] = new String[args.length - 3];
        String exec[] = new String[1];
        exec[0] = "/Users/jam/company/code/homegrown/middleware/zookeeper/seq.sh";
//        System.arraycopy(args, 3, exec, 0, exec.length);
        try {
            new Executor(hostPort, znode, filename, exec).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (!dm.dead) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        dm.handle(watchedEvent);
    }

    @Override
    public void exists(byte[] data) {
        if (data == null) {
            if (child != null) {
                System.out.println("Killing handle");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                }
            }
            child = null;
        } else {
            if (child != null) {
                System.out.println("Stopping child");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream fos = new FileOutputStream(new File(pathname));
                fos.write(data);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Starting child");
                child = Runtime.getRuntime().exec(exec);
                new StreamWriter(child.getInputStream(), System.out);
                new StreamWriter(child.getErrorStream(), System.err);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }

    static class StreamWriter extends Thread {
        OutputStream os;

        InputStream is;

        StreamWriter(InputStream is, OutputStream os) {
            this.is = is;
            this.os = os;
            start();
        }

        public void run() {
            byte b[] = new byte[80];
            int rc;
            try {
                while ((rc = is.read(b)) > 0) {
                    os.write(b, 0, rc);
                }
            } catch (IOException e) {
            }
        }
    }
}
