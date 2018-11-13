package com.github.dubbo.facade;

import java.util.List;

import model.GoodsItemDTO;

/**
 * Goods Facade
 * 
 * @author Administrator
 */
public interface GoodsFacade {
    
    public boolean reduceStock(int goodsItemId, int count);
    
    public List<GoodsItemDTO> getGoodsItems(int start, int count, int categoryId, int brandId, String sort);
}
