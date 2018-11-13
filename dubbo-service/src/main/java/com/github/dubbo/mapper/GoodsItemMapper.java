package com.github.dubbo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.dubbo.cache.SortedItem;
import com.github.dubbo.model.GoodsItem;
import com.github.dubbo.model.GoodsItemStatVo;

public interface GoodsItemMapper extends BaseMapper<GoodsItem> {
    
    public List<Integer> selectGoodsItemIds(@Param("categoryId") int categoryId, @Param("brandId") int brandId,
        @Param("start") int start, @Param("count") int count);
    
    public List<SortedItem> selectGoodsItemIdsWithSaleCount(@Param("start") int start, @Param("count") int count);
    
    public int updateStock(@Param("goodsItemId") int goodsItemId, @Param("count") int count);
    
    public List<SortedItem> selectGoodsItemIdsWithPublishTime(@Param("start") int start, @Param("count") int count);
    
    public List<GoodsItemStatVo> selectStockAndSaleCount();
}
