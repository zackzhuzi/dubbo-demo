package com.github.dubbo.mapper;

import org.apache.ibatis.annotations.Insert;

import com.github.dubbo.model.SinaBlog;

public interface SinaBlogMapper {
    @Insert("insert into sina_blog (`title`,`content`,`date`) values (#{title},#{content},#{date})")
    public int add(SinaBlog sinaBlog);
}
