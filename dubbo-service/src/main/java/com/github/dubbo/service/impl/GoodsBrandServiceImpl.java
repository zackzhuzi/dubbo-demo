package com.github.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.dubbo.mapper.GoodsBrandMapper;
import com.github.dubbo.model.GoodsBrand;
import com.github.dubbo.service.IGoodsBrandService;
import com.github.dubbo.service.IGoodsCacheService;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author will.li
 * @since 2018-01-05
 */
@Service
public class GoodsBrandServiceImpl extends ServiceImpl<GoodsBrandMapper, GoodsBrand> implements IGoodsBrandService {
    @Autowired
    private GoodsBrandMapper goodsBrandMapper;
    
    @Autowired
    private IGoodsCacheService goodCacheService;
    
    @Override
    public boolean addGoodsBrand(GoodsBrand goodsBrand) {
        Integer insert = goodsBrandMapper.insert(goodsBrand);
        return insert > 0;
    }
    
}
