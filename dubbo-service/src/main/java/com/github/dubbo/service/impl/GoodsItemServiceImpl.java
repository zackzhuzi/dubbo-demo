package com.github.dubbo.service.impl;

import java.util.List;
import java.util.Set;

import model.GoodsConstant;
import model.GoodsItemDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.dubbo.cache.CacheService;
import com.github.dubbo.mapper.GoodsBrandMapper;
import com.github.dubbo.mapper.GoodsCategoryMapper;
import com.github.dubbo.mapper.GoodsInfoMapper;
import com.github.dubbo.mapper.GoodsItemMapper;
import com.github.dubbo.mapper.GoodsItemStatMapper;
import com.github.dubbo.model.GoodsItem;
import com.github.dubbo.service.IGoodsCacheService;
import com.github.dubbo.service.IGoodsItemService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * <p>
 * 单品表 服务实现类
 * </p>
 *
 * @author will.li
 * @since 2018-01-05
 */
@Service
public class GoodsItemServiceImpl extends ServiceImpl<GoodsItemMapper, GoodsItem> implements IGoodsItemService {
    private Logger logger = LoggerFactory.getLogger(GoodsItemServiceImpl.class);
    
    @Autowired
    private GoodsItemMapper goodsItemMapper;
    
    @Autowired
    private GoodsItemStatMapper goodsItemStatMapper;
    
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    
    @Autowired
    private GoodsBrandMapper goodsBrandMapper;
    
    @Autowired
    private CacheService cacheService;
    
    @Autowired
    private IGoodsCacheService goodsCacheService;
    
    @Override
    public List<GoodsItemDTO> getGoodsItemDTOs(final int start, final int count, final int categoryId,
        final int brandId, final String sort) {
        Set<Object> goodsItemIdSet = Sets.newHashSet();
        if (GoodsConstant.SORT_PUBLISHTIME.equals(sort)) {
            goodsItemIdSet = goodsCacheService.getPublishTimeSortGoodsItemIds(categoryId, brandId, start, count);
        }
        else {
            goodsItemIdSet = goodsCacheService.getSalecountSortGoodsItemIds(categoryId, brandId, start, count);
        }
        List<GoodsItemDTO> result = Lists.newArrayListWithExpectedSize(goodsItemIdSet.size());
        for (Object goodsItemId : goodsItemIdSet) {
            GoodsItemDTO goodsCache = goodsCacheService.getGoodsCache(Integer.parseInt(goodsItemId.toString()));
            if (goodsCache != null) {
                result.add(goodsCache);
            }
        }
        return result;
    }
    
    @Deprecated
    @Override
    public List<GoodsItemDTO> getGoodsItemDTOsFromDB(final int start, final int count, final int categoryId,
        final int brandId, final String sort) {
        
        Set<Object> goodsItemIdSet = Sets.newHashSet();
        if (GoodsConstant.SORT_PUBLISHTIME.equals(sort)) {
            goodsItemIdSet = goodsCacheService.getPublishTimeSortGoodsItemIds(categoryId, brandId, start, count);
        }
        else {
            goodsItemIdSet = goodsCacheService.getSalecountSortGoodsItemIds(categoryId, brandId, start, count);
        }
        List<GoodsItemDTO> result = Lists.newArrayListWithExpectedSize(goodsItemIdSet.size());
        for (Object goodsItemId : goodsItemIdSet) {
            GoodsItemDTO goodsCache = goodsCacheService.getGoodsCache(Integer.parseInt(goodsItemId.toString()));
            if (goodsCache != null) {
                result.add(goodsCache);
            }
        }
        return result;
    }
    
}
