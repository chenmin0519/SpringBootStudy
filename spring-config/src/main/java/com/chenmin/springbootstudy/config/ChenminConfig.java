package com.chenmin.springbootstudy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenmin
 * @title: ChenminConfig
 * @projectName SpringBootStudy
 * @description: TODO
 * @date 2019-07-0822:52
 * @Version: 1.0
 * @JDK: 10
 */
@Configuration
@ComponentScan("com.chenmin.springbootstudy")
public class ChenminConfig {
    @Value("${chenmin.name}")
    private String name;
    @Value("${chenmin.age}")
    private String age;
    @Value("${chenmin.sex}")
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
