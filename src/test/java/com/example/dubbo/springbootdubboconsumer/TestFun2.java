package com.example.dubbo.springbootdubboconsumer;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestFun2 {


    interface Function{
        String apply(long timestamp);
    }



    private static void outputTime(TimeDemo timeDemo){
        Function time = timestamp -> {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(new Date(timestamp));
        };
        System.out.println(time.apply(timeDemo.createTime));
        System.out.println(time.apply(timeDemo.updateTime));
    }

    public static void main(String[] args){
        TimeDemo timeDemo = new TimeDemo();
        timeDemo.createTime = System.currentTimeMillis();
        timeDemo.updateTime = timeDemo.createTime+10000;
        outputTime(timeDemo);
    }
}
class TimeDemo{
    long createTime;

    long updateTime;
}
