package com.chenmin.springbootstudy.service.impl;

import com.chenmin.springbootstudy.myfinal.MyFiled;
import com.chenmin.springbootstudy.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author chenmin
 * @title: HiService
 * @projectName SpringBootStudy
 * @description: TODO
 * @date 2019-07-0823:14
 * @Version: 1.0
 * @JDK: 10
 */
@Service
public class HiService implements TestService {
    @PostConstruct
    public void init(){
        MyFiled.beans.put("chenminhi","say hi");
    }
    @Override
    public String getSayInfo() {
        return "say hi";
    }

    @PreDestroy
    public void destroy(){
        MyFiled.beans.remove("chenminhi");
        System.out.println("注销hi");
    }
}
