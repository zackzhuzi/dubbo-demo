package com.github.dubbo.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.dubbo.mapper.GoodsInfoMapper;
import com.github.dubbo.model.GoodsInfo;
import com.github.dubbo.service.IGoodsInfoService;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author will.li
 * @since 2018-01-05
 */
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements IGoodsInfoService {

}
