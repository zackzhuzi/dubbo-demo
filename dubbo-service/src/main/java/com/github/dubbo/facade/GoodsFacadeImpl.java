package com.github.dubbo.facade;

import java.util.List;

import model.GoodsItemDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dubbo.service.IGoodsItemService;
import com.github.dubbo.service.IGoodsStockService;

@Component
public class GoodsFacadeImpl implements GoodsFacade {
    
    @Autowired
    private IGoodsItemService goodsItemService;
    
    @Autowired
    private IGoodsStockService goodsStockService;
    
    @Override
    public List<GoodsItemDTO> getGoodsItems(int start, int count, final int categoryId, final int brandId,
        final String sort) {
        return goodsItemService.getGoodsItemDTOs(start, count, categoryId, brandId, sort);
    }
    
    @Override
    public boolean reduceStock(int goodsItemId, int count) {
        return goodsStockService.reduceStock(goodsItemId, count);
    }
    
}
