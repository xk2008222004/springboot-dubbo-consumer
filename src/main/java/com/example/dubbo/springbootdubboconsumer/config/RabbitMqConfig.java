package com.example.dubbo.springbootdubboconsumer.config;

import com.example.springbootdubbo.dict.ParamDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class RabbitMqConfig {

    @Bean
    public Queue testDirectQueue(){
        return new Queue(ParamDict.DIRECTQUEUE,true);
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
