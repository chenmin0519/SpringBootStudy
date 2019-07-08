package com.chenmin.springbootstudy.service.impl;

import com.chenmin.springbootstudy.myfinal.MyFiled;
import com.chenmin.springbootstudy.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author chenmin
 * @title: HelloService
 * @projectName SpringBootStudy
 * @description: TODO
 * @date 2019-07-0823:12
 * @Version: 1.0
 * @JDK: 10
 */
@Service
public class HelloService implements TestService {

    @PostConstruct
    public void init(){
        MyFiled.beans.put("chenminhello","say hello");
    }

    @Override
    public String getSayInfo() {
        return "say hello";
    }

    @PreDestroy
    public void destroy(){
        MyFiled.beans.remove("chenminhello");
        System.out.println("注销Hello");
    }
}
