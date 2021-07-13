package com.sentinel.test.chenmin.generate;

import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

@Data
public class MethodModel {
    private String requestType;
    private String url;
    private String resultType;
    private String methodName;
    private Class<?>[] paramsTypes;
    private Parameter[] parameters;
    private Annotation[][] parameterAnnotations;
    private int parameterCount;
}
