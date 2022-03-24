package com.example.dubbo.springbootdubboconsumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Set;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubboConfiguration
@ServletComponentScan(basePackages = "com.example.dubbo.springbootdubboconsumer.listener")
public class SpringbootDubboConsumerApplication {

    private static Logger logger = LoggerFactory.getLogger(SpringbootDubboConsumerApplication.class);

    public static void main(String[] args) {
        /*new Banner(){

            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
                out.println("this is a 自定义 banner");

            }
        }.*/
        /*SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(SpringbootDubboConsumerApplication.class).banner(new Banner(){

            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
                out.println("this is a program banner");

            }
        });*/
        SpringApplication springApplication = new SpringApplication(SpringbootDubboConsumerApplication.class);
        /*springApplication.addListeners(new ApplicationListener<ApplicationStartingEvent>() {
            @Override
            public void onApplicationEvent(ApplicationStartingEvent event) {
                logger.info("=============before start application event is trigger when call the run method==========");
            }
        });*/
        springApplication.setAdditionalProfiles("pro");
        springApplication.setBanner(new Banner(){
            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
                out.println("this is a program banner");
            }
        });
        springApplication.run(args);


    }

}
