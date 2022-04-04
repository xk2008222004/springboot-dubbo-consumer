package com.example.dubbo.springbootdubboconsumer.listener;

import com.example.springbootdubbo.dict.ParamDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = ParamDict.DIRECTQUEUE)
@Slf4j
public class DirectReceiverNew {

    @RabbitHandler
    public void process(Map testMessage){
      log.info(this.getClass().getName().contains("New")?"第二个":"第一个"+" receive message is =============="+testMessage);
    }
}
