package com.chenmin.springbootstudy.application;

import com.chenmin.springbootstudy.config.ChenminConfig;
import com.chenmin.springbootstudy.myfinal.MyFiled;
import com.chenmin.springbootstudy.service.TestService;
import com.chenmin.springbootstudy.service.impl.HelloService;
import com.chenmin.springbootstudy.service.impl.HiService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenmin
 * @title: ConfigOneApplication
 * @projectName SpringBootStudy
 * @description: TODO
 * @date 2019-07-0823:31
 * @Version: 1.0
 * @JDK: 10
 */
public class ConfigOneApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ChenminConfig.class);
        TestService service1 = context.getBean(HelloService.class);
        System.out.println("----->"+service1.getSayInfo()+"------>"+MyFiled.beans.get("chenminhello"));
        TestService service2 = context.getBean(HiService.class);
        System.out.println("----->"+service2.getSayInfo()+"------>"+MyFiled.beans.get("chenminhi"));
        System.out.println("My Map Is : "+MyFiled.beans);
        context.removeBeanDefinition("helloService");
        System.out.println("My Map Is : "+MyFiled.beans);
        context.removeBeanDefinition("hiService");
        System.out.println("My Map Is : "+MyFiled.beans);
    }
}
