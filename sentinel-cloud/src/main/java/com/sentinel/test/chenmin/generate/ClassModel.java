package com.sentinel.test.chenmin.generate;

import lombok.Data;

@Data
public class ClassModel {
    private String packName;
    private String interfaceName;
    private String interfaceSimpleName;
    private String className;
    private Class<?> claz;
}
