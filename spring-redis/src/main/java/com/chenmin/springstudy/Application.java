package com.chenmin.springstudy;

import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenmin
 * @title: ConfigOneApplication
 * @projectName SpringBootStudy
 * @description: TODO
 * @date 2019-07-0823:31
 * @Version: 1.0
 * @JDK: 10
 */
public class Application {

    public static void main(String[] args) {

        Integer size = 100;
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),size,0.001);

        Integer temp1 = 1;
        Integer temp2 = 1;
        for(int i = 0 ; i <= 300 ; i++){
            temp1 = i;
            temp2 = i;
            if(temp1 != temp2){
                break;
            }
            System.out.println(i);
        }

//        int i = 10;
//        Application application = new Application();
//        application.method(i);
//        System.out.println(i);

    }

    public void method(int i){
        System.setOut(new PrintStream(System.out){
            @Override
            public void println(int x) {
                super.println(i * 10);
            }
        });
    }


}
