package com.example.dubbo.springbootdubboconsumer.vo;

import com.example.springbootdubbo.po.SysLog;
import lombok.Data;

import java.util.Date;

@Data
public class SysLogExtNew {

    private Long id;
    private String model;
    private String method;
    private String param;
    private Integer result;
    private Date createTime;
    private String fullLog;

    private Long  startTime;

    private Long endTime;

}
