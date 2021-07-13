package com.sentinel.test.chenmin;

import lombok.Data;

@Data
public class WordModel {
    private int row;//第几行
    private int erect;
    private String name;
    private Boolean merge;
    private int[] mergeNum;
    private String value;
}
