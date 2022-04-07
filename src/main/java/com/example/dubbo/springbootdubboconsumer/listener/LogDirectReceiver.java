package com.example.dubbo.springbootdubboconsumer.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.container.logback.LogbackContainer;
import com.example.springbootdubbo.annotation.SysLogHandler;
import com.example.springbootdubbo.dict.ParamDict;
import com.example.springbootdubbo.po.SysLog;
import com.example.springbootdubbo.service.SysLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.FileAppender;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
@RabbitListener(queues = ParamDict.LOGDELAYDIRECTQUEUE)
@Slf4j
public class LogDirectReceiver {

    @Reference(interfaceClass = SysLogService.class,version = "1.0.0")
    private SysLogService sysLogService;

    @RabbitHandler
    public void onLazyMessage(Map testMessage) throws IOException{
        log.info("LogDirectReceiver receive message is =============="+testMessage);
        SysLog sysLog = (SysLog) testMessage.get("messageData");
        Date dateTime = sysLog.getCreateTime();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime);
        log.info("LOG_FILE====="+System.getProperty("user.dir"));
        File file = new File(System.getProperty("user.dir")+File.pathSeparator+"logging-full.log");
        if(file.exists()){
            log.info("file exists=================");
        }else{
            //TODO
            //改成从项目中取
            file = new File("E:\\bmworkspace2\\springboot-dubbo-consumer\\logging-full.log");
            log.info("file not exists================");
        }
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str = "";
            StringBuffer sb = new StringBuffer();
            int len = 0 ;
            String start="";
            while((str=reader.readLine())!=null){
                if(str.contains(dateStr)&&(str.contains("request has initialized"))){
                    start = str;
                    reader.mark(len);
                }else{
                    len  += str.length();
                }
            }
            reader.reset();
            sb.append(start).append("\n");
            while ((str=reader.readLine())!=null){
                sb.append(str).append("\n");
                if(str.contains(dateStr)&&(str.contains("request has destroyed"))){
                    break;
                }
            }
            reader.close();
            sysLog.setFullLog(sb.toString());
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex2){
            ex2.printStackTrace();
        }
        int result = sysLogService.insertSysLog(sysLog);
        log.info("result="+result);

    }
}
