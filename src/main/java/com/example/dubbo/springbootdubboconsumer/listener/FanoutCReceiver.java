package com.example.dubbo.springbootdubboconsumer.listener;

import com.example.springbootdubbo.dict.ParamDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = ParamDict.FADOUTCQUEUE)
@Slf4j
public class FanoutCReceiver {

    @RabbitHandler
    public void process(Map testMessage){
      log.info("FanoutCReceiver   receive message is =============="+testMessage);
    }
}
