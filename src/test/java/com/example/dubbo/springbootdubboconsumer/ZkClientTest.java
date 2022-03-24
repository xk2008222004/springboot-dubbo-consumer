package com.example.dubbo.springbootdubboconsumer;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

public class ZkClientTest {

    public static void main(String[] args) throws InterruptedException{
        //subchild();
        changedata();
    }

    public static void subchild() throws InterruptedException {
        ZkClient zkClient = new ZkClient("192.168.0.101:2181", 5000);

        String path = "/mipush";

        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(parentPath + "'s child changed, currentChilds: " +currentChilds);
            }
        });

        if(zkClient.exists(path)) {
            zkClient.deleteRecursive(path);
            // zkClient.delete(path);
            System.out.println("delete " + path + " successful");
        }
        zkClient.createPersistent(path);
        Thread.sleep(1000);
        System.out.println("now child path is: " + zkClient.getChildren(path));
        zkClient.createPersistent(path + "/p1");
        Thread.sleep(1000);
        System.out.println("now child path is: " + zkClient.getChildren(path));
        zkClient.createPersistent(path + "/p2", "abc");
        Thread.sleep(1000);
        System.out.println("now child path is: " + zkClient.getChildren(path));
    }

    public static void changedata() throws InterruptedException {
        ZkClient zkClient = new ZkClient("192.168.0.101:2181", 5000);
        String path = "/mipush";
        if(zkClient.exists(path)) {
            zkClient.deleteRecursive(path);
            // zkClient.delete(path);
            System.out.println("delete " + path + " successful");
        }
        zkClient.createPersistent(path, "abc");

        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Node " + dataPath + " changed, new data: " + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Node " + dataPath + " deleted.");
            }
        });

        // 读取节点数据
        System.out.println("now data is: " + zkClient.readData(path).toString());
        zkClient.writeData(path, "def");
        Thread.sleep(1000);
        System.out.println("Node exists: " + zkClient.exists(path));
        zkClient.delete(path);
        System.out.println("Node exists: " + zkClient.exists(path));
    }


}
