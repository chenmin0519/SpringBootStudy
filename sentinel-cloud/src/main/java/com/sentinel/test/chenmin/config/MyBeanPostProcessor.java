package com.sentinel.test.chenmin.config;

import com.sentinel.test.chenmin.service.TestAaaa;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
//@Configuration 这个类没用了
public class MyBeanPostProcessor implements ApplicationContextAware, BeanPostProcessor {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TestAaaa) {
            // 如果遇到需要替换的Bean，我们直接换成自己实现的Bean即可（这里可以把就得removeBeanDefinition，然后注册新的registerBeanDefinition）
            // 这里的myConfig要继承自defaultConfig，否则引用的地方会报错
            return bean;
        }
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        TestAaaa aa = applicationContext.getAutowireCapableBeanFactory().getBean(TestAaaa.class);
        if (bean instanceof TestAaaa) {
            System.out.println(beanName);
            return bean;
        }
        return bean;
    }

}