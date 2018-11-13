package com.github.dubbo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.dubbo.model.GoodsCategory;

public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {

    public int insertGoodsCategory(GoodsCategory goodsCategory);
    public List<GoodsCategory> selectCategoryList();


}
