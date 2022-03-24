package com.example.dubbo.springbootdubboconsumer;

public class TestFun {
    public static  void main(String[] args){
        new Thread(()->{
            for(int i=0;i<100;i++){
                System.out.println(i+"==========");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<100;i++){
                    System.out.println(i+"-----------");
                }
            }
        }).start();
    }
}
