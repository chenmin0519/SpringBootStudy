package com.sentinel.test.chenmin.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface WordTable {
    int row() default 0; //第几行
    int erect() default 0;//列
    String name() default "";
    boolean merge() default false; //是否合并单元格
    int[] mergeNum() default {};//合并格数0开始
}
