package com.github.dubbo.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.dubbo.model.GoodsItemStat;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author will.li
 * @since 2018-01-05
 */
public interface GoodsItemStatMapper extends BaseMapper<GoodsItemStat> {
    
    public GoodsItemStat selectByGoodsItemId(@Param("goodsItemId") int goodsItemId);
    
    public int updateSaleCount(@Param("goodsItemId") int goodsItemId, @Param("count") int count);
}
