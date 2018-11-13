package com.github.dubbo.service;

/**
 * Stock Service
 * 
 * @author yuzhu.peng
 *
 */
public interface IGoodsStockService {
    /**
     * 
     * @param goodsItemId
     * @param count positive means reduce stock; negative means increase stock
     * @return
     */
    public boolean reduceStock(final int goodsItemId, final int count);
}
