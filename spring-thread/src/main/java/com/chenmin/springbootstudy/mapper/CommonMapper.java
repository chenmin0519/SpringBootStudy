package com.chenmin.springbootstudy.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface CommonMapper<T> extends MySqlMapper<T>, Mapper<T> {

}
