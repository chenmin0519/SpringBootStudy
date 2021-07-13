package com.sentinel.test.chenmin;

import com.sentinel.test.chenmin.anno.WordTable;
import lombok.Data;

@Data
public class TestModel {

    @WordTable(row = 1,name="名字",erect = 1)
    private String name;
    @WordTable(row = 1,name="部门",erect = 3)
    private String department;
    @WordTable(row = 2,name="字段",erect = 1)
    private String ss;
    @WordTable(row = 2,name="你妹",erect = 3)
    private String haha;
    @WordTable(row = 3,name="文本",merge = true,mergeNum = {0,1,2,3})
    private String text;
}
