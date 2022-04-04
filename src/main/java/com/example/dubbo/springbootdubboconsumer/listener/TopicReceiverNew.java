package com.example.dubbo.springbootdubboconsumer.listener;

import com.example.springbootdubbo.dict.ParamDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = ParamDict.MAN)
@Slf4j
public class TopicReceiverNew {

    @RabbitHandler
    public void process(Map testMessage){
      log.info(Thread.currentThread().getClass().getName()+"'s class receive message is =============="+testMessage);
    }
}
