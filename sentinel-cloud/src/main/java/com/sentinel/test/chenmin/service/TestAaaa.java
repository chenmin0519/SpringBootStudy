package com.sentinel.test.chenmin.service;

import com.sentinel.test.chenmin.anno.ChenminGetMapping;
import com.sentinel.test.chenmin.anno.ChenminProxy;
import com.sentinel.test.chenmin.anno.ChenminRequestParam;

@ChenminProxy(name = "/user")
public interface TestAaaa {

    @ChenminGetMapping(value = "/sayHi")
    String sayHello(@ChenminRequestParam(value = "name") String name) throws Exception;

    @ChenminGetMapping(value = "/httptest")
    String test(@ChenminRequestParam(value = "name") String name) throws Exception;
}
