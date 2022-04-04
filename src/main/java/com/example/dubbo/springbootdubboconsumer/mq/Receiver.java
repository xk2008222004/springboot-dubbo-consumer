package com.example.dubbo.springbootdubboconsumer.mq;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Receiver {

    private final static String QUEUE_NAME = "hello";
    private final static String EXCHANGE_NAME = "pub-sub-queue";

    public static void main(String[] args) throws Exception{
        receiveMulti();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    log.info("thread============");
                    Receiver.receiveMulti();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void receiveMulti() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.3");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,EXCHANGE_NAME,"");
        log.info("Thread.name="+Thread.currentThread().getName()+
                " [*] Waiting for messages. To exit press CTRL+C==========queue="+queue);

        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String message = new String(delivery.getBody(),"UTF-8");
            log.info(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queue, true, deliverCallback, consumerTag->{});
    }



    public static void receiveSingle() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.3");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        log.info(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String message = new String(delivery.getBody(),"UTF-8");
            log.info(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag->{});
    }
}
