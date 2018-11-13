package com.github.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.dubbo.mapper.GoodsCategoryMapper;
import com.github.dubbo.model.GoodsCategory;
import com.github.dubbo.service.IGoodsCategoryService;

/**
 * <p>
 * 商品类目表 服务实现类
 * </p>
 *
 * @since 2018-01-05
 */
@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements IGoodsCategoryService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public int insertGoodsCategory(GoodsCategory goodsCategory) {
        return goodsCategoryMapper.insertGoodsCategory(goodsCategory);
    }

}
