package com.example.dubbo.springbootdubboconsumer.config;

import com.example.springbootdubbo.dict.ParamDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class RabbitMqConfig {

    @Bean
    public Queue testDirectQueue(){
        return new Queue(ParamDict.DIRECTQUEUE,true);
    }

    @Bean
    public Queue logDirectQueue(){
        return new Queue(ParamDict.LOGDIRECTQUEUE,true);
    }

    @Bean
    public Queue lazyLogDirectQueue(){
        Queue queue = new Queue(ParamDict.LOGDELAYDIRECTQUEUE);
        return queue;
    }


    @Bean
    public Queue getTopicQueue(){
        return new Queue(ParamDict.MAN);
    }

    @Bean
    public Queue getTopQueue2(){
        return new Queue(ParamDict.WOMAN);
    }

    @Bean
    public Queue getFadoutA(){
        return new Queue(ParamDict.FADOUTAQUEUE);
    }

    @Bean
    public Queue getFadoutB(){
        return new Queue(ParamDict.FADOUTBQUEUE);
    }

    @Bean
    public Queue getFadoutC(){
        return new Queue(ParamDict.FADOUTCQUEUE);
    }



    @Bean
    public DirectExchange testDirectExchange(){
        return new DirectExchange(ParamDict.DIRECTEXCHANGE,true,false);
    }

    @Bean
    public DirectExchange logDirectExchange(){
        return new DirectExchange(ParamDict.LOGDIRECTQUEUE,true,false);
    }

    @Bean
    public CustomExchange lazylogDirectExchange(){
        //创建一个自定义交换机，可以发送延迟消息
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        //channel.exchangeDeclare("my-exchange", "x-delayed-message", true, false, args);
        CustomExchange directExchange = new CustomExchange(ParamDict.LOGDELAYDIRECTEXCHANGE,"x-delayed-message",false,false,args);
        return directExchange;
    }

    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange(ParamDict.TOPICEXCHANGE);
    }

    @Bean
    public FanoutExchange getFanExchange(){
        return new FanoutExchange(ParamDict.FADOUTEXCHANGE);
    }

    @Bean
    Binding bindingDirect(){
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with(ParamDict.DIRECTROUTING);
    }

    @Bean
    Binding bindingLogDirect(){
        return BindingBuilder.bind(logDirectQueue()).to(logDirectExchange()).with(ParamDict.SYSLOGDIRECTROUTING);
    }

    @Bean
    Binding bindingLazyLogDirect(){
        return BindingBuilder.bind(lazyLogDirectQueue()).to(lazylogDirectExchange())
                .with(ParamDict.SYSLOGDELAYDIRECTROUTING).noargs();
    }

    @Bean
    Binding bindingTopic(){
        return BindingBuilder.bind(getTopicQueue()).to(getTopicExchange()).with(ParamDict.MAN);
    }

    @Bean
    Binding bindingTopic2(){
        return BindingBuilder.bind(getTopQueue2()).to(getTopicExchange()).with(ParamDict.TOPICROUTING);
    }

    @Bean
    Binding bindingFanoutA(){
        return BindingBuilder.bind(getFadoutA()).to(getFanExchange());
    }

    @Bean
    Binding bindingFanoutB(){
        return BindingBuilder.bind(getFadoutB()).to(getFanExchange());
    }

    @Bean
    Binding bindingFanoutC(){
        return BindingBuilder.bind(getFadoutC()).to(getFanExchange());
    }

    @Bean
    public DirectExchange lonelyExchange(){
        return new DirectExchange(ParamDict.ANOTHEREXCHANGE,true,false);
    }




}
