package com.github.dubbo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.dubbo.model.GoodsBrand;

public interface GoodsBrandMapper extends BaseMapper<GoodsBrand> {
    
    public List<GoodsBrand> selectBrandList();
}
