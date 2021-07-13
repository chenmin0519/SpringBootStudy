package com.sentinel.test.chenmin.config;

import com.sentinel.test.chenmin.cglib.ChenminClassLoder;
import com.sentinel.test.chenmin.generate.GenerateJava;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化一个必须在容器创建时就创建的bean，
 **/
@Configuration
@EnableConfigurationProperties({ChenminConfig.class})
public class DemoServiceAutoConfiguration implements InitializingBean {

    private final ApplicationContext context;

    private final ChenminConfig chenminConfig;

    public DemoServiceAutoConfiguration(ApplicationContext context,ChenminConfig chenminConfig){
        this.context = context;
        this.chenminConfig = chenminConfig;
    }

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;
    /**
     * 初始化Bean
     */
    @Override
    public void afterPropertiesSet() {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        GenerateJava generateJava = new GenerateJava();
        generateJava.generateClasses(defaultListableBeanFactory);

       
    }

}