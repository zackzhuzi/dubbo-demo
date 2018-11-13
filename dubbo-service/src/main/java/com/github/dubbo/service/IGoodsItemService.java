package com.github.dubbo.service;

import java.util.List;

import model.GoodsItemDTO;

import com.baomidou.mybatisplus.service.IService;
import com.github.dubbo.model.GoodsItem;

/**
 * @since 2018-01-05
 */
public interface IGoodsItemService extends IService<GoodsItem> {
    
    /**
     * 
     * @param start
     * @param count
     * @param categoryId
     * @param brandId
     * @param sort:sale,publishTime
     * @return
     */
    public List<GoodsItemDTO> getGoodsItemDTOs(final int start, final int count, final int categoryId,
        final int brandId, final String sort);
    
    @Deprecated
    List<GoodsItemDTO> getGoodsItemDTOsFromDB(int start, int count, int categoryId, int brandId, String sort);
}
