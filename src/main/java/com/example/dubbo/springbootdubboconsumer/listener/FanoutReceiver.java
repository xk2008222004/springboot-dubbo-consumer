package com.example.dubbo.springbootdubboconsumer.listener;

import com.example.springbootdubbo.dict.ParamDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = ParamDict.FADOUTAQUEUE)
@Slf4j
public class FanoutReceiver {

    @RabbitHandler
    public void process(Map testMessage){
      log.info("FanoutReceiver   receive message is =============="+testMessage);
    }
}
