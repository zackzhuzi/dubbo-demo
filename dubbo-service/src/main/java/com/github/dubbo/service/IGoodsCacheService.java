package com.github.dubbo.service;

import java.util.Map;
import java.util.Set;

import model.GoodsItemDTO;

public interface IGoodsCacheService {
    Set<Object> getGoodsItemSaleCountZset(int start, int number);
    
    Set<Object> getGoodsItemPublishTimeZset(int start, int number);
    
    Set<Object> getGoodsItemCategorySet(int categoryId);
    
    Set<Object> getGoodsItemBrandSet(int brandId);
    
    GoodsItemDTO getGoodsCache(int goodsItemId);
    
    void initGoodsItemBrandSet(int brandId);
    
    void initGoodsItemCategorySet(int categoryId);
    
    void initGoodsItemPublishTimeZset();
    
    void initGoodsItemSaleCountZset();
    
    void initGoodsCache(int goodsItemId);
    
    void initBrandSet();
    
    void initCategorySet();
    
    Set<Object> getCategorySet();
    
    Set<Object> getBrandSet();
    
    void initAllSet();

    Set<Object> getPublishTimeSortGoodsItemIds(int categoryId, int brandId, int start, int count);

    Set<Object> getSalecountSortGoodsItemIds(int categoryId, int brandId, int start, int count);

    void initGoodsStatCache(int goodsItemId);

    Map<String, Object> getGoodsStatCache(int goodsItemId);

    GoodsItemDTO getGoodsCacheTest(int goodsItemId);
    
}
