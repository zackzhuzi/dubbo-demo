package com.github.dubbo.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import model.GoodsItemDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dubbo.cache.CacheService;
import com.github.dubbo.cache.SortedItem;
import com.github.dubbo.commons.utils.Map2ObjectUtils;
import com.github.dubbo.constant.RedisConstant;
import com.github.dubbo.mapper.GoodsBrandMapper;
import com.github.dubbo.mapper.GoodsCategoryMapper;
import com.github.dubbo.mapper.GoodsInfoMapper;
import com.github.dubbo.mapper.GoodsItemMapper;
import com.github.dubbo.mapper.GoodsItemStatMapper;
import com.github.dubbo.model.GoodsBrand;
import com.github.dubbo.model.GoodsCategory;
import com.github.dubbo.model.GoodsInfo;
import com.github.dubbo.model.GoodsItem;
import com.github.dubbo.model.GoodsItemStat;
import com.github.dubbo.service.IGoodsCacheService;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Goods Cache ServiceImpl
 * 
 * @author yuzhu.peng
 *
 */
@Service
public class GoodsCacheServiceImpl implements IGoodsCacheService {
    private static final Logger logger = LoggerFactory.getLogger(GoodsCacheServiceImpl.class);
    
    @Autowired
    private CacheService cacheService;
    
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    
    @Autowired
    private GoodsBrandMapper goodsBrandMapper;
    
    @Autowired
    private GoodsItemStatMapper goodsItemStatMapper;
    
    @Autowired
    private GoodsItemMapper goodsItemMapper;
    
    @Override
    public GoodsItemDTO getGoodsCache(int goodsItemId) {
        if (goodsItemId <= 0) {
            return null;
        }
        initGoodsCache(goodsItemId);
        Map<String, Object> map = cacheService.getMap(RedisConstant.GOODS_ITEM_PREFIX + goodsItemId);
        GoodsItemDTO goodsItemDto = Map2ObjectUtils.mapToObject(map, GoodsItemDTO.class);
        Map<String, Object> statMap = getGoodsStatCache(goodsItemId);
        goodsItemDto.setStock(Integer.parseInt(statMap.get(RedisConstant.GOODS_ITEM_STAT_FIELD_STOCK).toString()));
        goodsItemDto.setStock(Integer.parseInt(statMap.get(RedisConstant.GOODS_ITEM_STAT_FIELD_SALECOUNT).toString()));
        return goodsItemDto;
    }

    @Override
    public GoodsItemDTO getGoodsCacheTest(int goodsItemId) {
        if (goodsItemId <= 0) {
            return null;
        }
        initGoodsCache(goodsItemId);
        Map<String, Object> map = cacheService.getMap(RedisConstant.GOODS_ITEM_PREFIX + goodsItemId);
        GoodsItemDTO goodsItemDto = Map2ObjectUtils.mapToObject(map, GoodsItemDTO.class);
        return goodsItemDto;
    }
    
    @Override
    public Map<String, Object> getGoodsStatCache(int goodsItemId) {
        initGoodsStatCache(goodsItemId);
        Map<String, Object> statMap = cacheService.getMap(RedisConstant.GOODS_ITEM_STAT_PREFIX + goodsItemId);
        return statMap;
    }
    
    @Override
    public Set<Object> getGoodsItemSaleCountZset(int start, int number) {
        initGoodsItemSaleCountZset();
        return cacheService.getSortedSet(RedisConstant.GOODS_ITEM_ID_SALECOUNT_ZSET, start, number);
    }
    
    @Override
    public Set<Object> getGoodsItemPublishTimeZset(int start, int number) {
        initGoodsItemPublishTimeZset();
        return cacheService.getSortedSet(RedisConstant.GOODS_ITEM_ID_PUBLISHTIME_ZSET, start, number);
    }
    
    @Override
    public Set<Object> getGoodsItemCategorySet(int categoryId) {
        initGoodsItemCategorySet(categoryId);
        return cacheService.getSet(RedisConstant.GOODS_ITEM_ID_CATEGORY_SET + categoryId);
    }
    
    @Override
    public Set<Object> getGoodsItemBrandSet(int brandId) {
        initGoodsItemBrandSet(brandId);
        return cacheService.getSet(RedisConstant.GOODS_ITEM_ID_BRAND_SET + brandId);
    }
    
    @Override
    public void initGoodsCache(int goodsItemId) {
        if (goodsItemId <= 0) {
            return;
        }
        if (cacheService.exist(RedisConstant.GOODS_ITEM_PREFIX + goodsItemId)) {
            return;
        }
        GoodsItem goodsItem = goodsItemMapper.selectById(goodsItemId);
        if (goodsItem == null || goodsItem.getStatus() == GoodsItem.Status.Invalid.getValue()) {
            return;
        }
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsItem.getGoodsId());
        if (goodsInfo.getStatus() == GoodsInfo.Status.Invalid.getValue()) {
            return;
        }
        GoodsCategory category = goodsCategoryMapper.selectById(goodsInfo.getCategoryId());
        GoodsBrand brand = goodsBrandMapper.selectById(goodsInfo.getBrandId());
        Map<String, Object> goodsItemMap = Maps.newHashMap();
        goodsItemMap.put("goodsItemId", goodsItem.getId());
        goodsItemMap.put("goodsItemName", goodsItem.getGoodsItemName());
        goodsItemMap.put("goodsId", goodsItem.getGoodsId());
        goodsItemMap.put("goodsName", goodsInfo.getGoodsName());
        goodsItemMap.put("goodsDesc", goodsInfo.getGoodsDesc());
        goodsItemMap.put("price", goodsItem.getPrice());
        goodsItemMap.put("marketPrice", goodsItem.getMarketPrice());
        goodsItemMap.put("shopPrice", goodsItem.getShopPrice());
        goodsItemMap.put("categoryName", category.getCategoryName());
        goodsItemMap.put("brandName", brand.getBrandName());
        goodsItemMap.put("brandDesc", brand.getBrandDesc());
        goodsItemMap.put("brandLogo", brand.getLogo());
        goodsItemMap.put("createDate", goodsItem.getCreateDate());
        goodsItemMap.put("categoryId", goodsItem.getCategoryId());
        goodsItemMap.put("brandId", goodsItem.getBrandId());
        cacheService.setMap(RedisConstant.GOODS_ITEM_PREFIX + goodsItem.getId(), goodsItemMap);
        cacheService.expire(RedisConstant.GOODS_ITEM_PREFIX + goodsItem.getId(), RedisConstant.SECONDS_GOODS_ITEM
            + new Random().nextInt(300));// 添加随机数，避免所有商品同时过期，同时造成缓存击穿，引起雪崩
        logger.info("init goods cache done | goodsItemMap = {}", goodsItemMap);
    }
    
    @Override
    public void initGoodsStatCache(int goodsItemId) {
        if (goodsItemId <= 0) {
            return;
        }
        if (cacheService.exist(RedisConstant.GOODS_ITEM_STAT_PREFIX + goodsItemId)) {
            return;
        }
        
        GoodsItem goodsItem = goodsItemMapper.selectById(goodsItemId);
        if (goodsItem == null || goodsItem.getStatus() == GoodsItem.Status.Invalid.getValue()) {
            return;
        }
        GoodsItemStat goodsItemStat = goodsItemStatMapper.selectByGoodsItemId(goodsItem.getId());
        Map<String, Object> goodsItemStatMap = Maps.newHashMap();
        goodsItemStatMap.put("goodsItemId", goodsItem.getId());
        goodsItemStatMap.put("stock", goodsItem.getStock());
        goodsItemStatMap.put("saleCount", goodsItemStat == null ? 0 : goodsItemStat.getSaleCount());
        cacheService.setMap(RedisConstant.GOODS_ITEM_STAT_PREFIX + goodsItem.getId(), goodsItemStatMap);// 永不过期
        // cacheService.expire(RedisConstant.GOODS_ITEM_STAT_PREFIX + goodsItem.getId(),
        // RedisConstant.SECONDS_GOODS_ITEM);
        logger.info("init goods stat cache done | goodsItemStatMap = {}", goodsItemStatMap);
    }
    
    @Override
    public void initGoodsItemSaleCountZset() {
        if (cacheService.exist(RedisConstant.GOODS_ITEM_ID_SALECOUNT_ZSET)) {
            return;
        }
        List<SortedItem> goodsItemIdsWithSaleCount =
            goodsItemMapper.selectGoodsItemIdsWithSaleCount(0, RedisConstant.NUMBER_SALECOUNT_ZSET);
        Set<SortedItem> sortedItems = Sets.newHashSet(goodsItemIdsWithSaleCount);
        cacheService.setSortedSet(RedisConstant.GOODS_ITEM_ID_SALECOUNT_ZSET, sortedItems);
        cacheService.expire(RedisConstant.GOODS_ITEM_ID_SALECOUNT_ZSET, RedisConstant.SECONDS_ITEMID_SALECOUNT_ZSET);
        logger.info("init GoodsItem SaleCount Zset success");
    }
    
    @Override
    public void initGoodsItemPublishTimeZset() {
        if (cacheService.exist(RedisConstant.GOODS_ITEM_ID_PUBLISHTIME_ZSET)) {
            return;
        }
        List<SortedItem> goodsItemIdsWithPublishTime =
            goodsItemMapper.selectGoodsItemIdsWithPublishTime(0, RedisConstant.NUMBER_PUBLISHTIME_ZSET);
        Set<SortedItem> sortedItems = Sets.newHashSet(goodsItemIdsWithPublishTime);
        cacheService.setSortedSet(RedisConstant.GOODS_ITEM_ID_PUBLISHTIME_ZSET, sortedItems);
        cacheService.expire(RedisConstant.GOODS_ITEM_ID_PUBLISHTIME_ZSET, RedisConstant.SECONDS_ITEMID_PUBLISHTIME_ZSET);
        logger.info("init GoodsItem PublishTime Zset success");
    }
    
    @Override
    public void initGoodsItemCategorySet(int categoryId) {
        if (cacheService.exist(RedisConstant.GOODS_ITEM_ID_CATEGORY_SET + categoryId)) {
            return;
        }
        List<Integer> goodsItemIds = goodsItemMapper.selectGoodsItemIds(categoryId, 0, 0, 0);
        if (goodsItemIds == null || goodsItemIds.isEmpty()) {
            return;
        }
        Set<Object> goodsItemIdsSet = Sets.newHashSet(goodsItemIds);
        cacheService.setSet(RedisConstant.GOODS_ITEM_ID_CATEGORY_SET + categoryId, goodsItemIdsSet);
        cacheService.expire(RedisConstant.GOODS_ITEM_ID_CATEGORY_SET + categoryId,
            RedisConstant.SECONDS_ITEMID_CATEGORY_SET);
        logger.info("init Goods Item Category Set Done");
    }
    
    @Override
    public void initGoodsItemBrandSet(int brandId) {
        if (cacheService.exist(RedisConstant.GOODS_ITEM_ID_BRAND_SET + brandId)) {
            return;
        }
        List<Integer> goodsItemIds = goodsItemMapper.selectGoodsItemIds(0, brandId, 0, 0);
        if (goodsItemIds == null || goodsItemIds.isEmpty()) {
            return;
        }
        Set<Object> goodsItemIdsSet = Sets.newHashSet(goodsItemIds);
        cacheService.setSet(RedisConstant.GOODS_ITEM_ID_BRAND_SET + brandId, goodsItemIdsSet);
        cacheService.expire(RedisConstant.GOODS_ITEM_ID_BRAND_SET + brandId, RedisConstant.SECONDS_ITEMID_BRAND_SET);
        logger.info("init Goods Item Brand Set done");
    }
    
    @Override
    public void initAllSet() {
        initGoodsItemSaleCountZset();
        initGoodsItemPublishTimeZset();
        Set<Object> brandSet = getBrandSet();
        for (Object brandId : brandSet) {
            initGoodsItemBrandSet(Integer.parseInt(brandId.toString()));
        }
        Set<Object> categorySet = getCategorySet();
        for (Object cid : categorySet) {
            initGoodsItemCategorySet(Integer.parseInt(cid.toString()));
        }
        logger.info("init all set done");
    }
    
    @Override
    public void initCategorySet() {
        if (cacheService.exist(RedisConstant.GOODS_CATEGORY_SET)) {
            return;
        }
        List<GoodsCategory> selectCategoryList = goodsCategoryMapper.selectCategoryList();
        if (selectCategoryList == null || selectCategoryList.isEmpty()) {
            return;
        }
        
        Collection<Integer> transform =
            Collections2.transform(selectCategoryList, new Function<GoodsCategory, Integer>() {
                
                @Override
                public Integer apply(GoodsCategory paramF) {
                    return paramF.getId();
                }
            });
        cacheService.setSet(RedisConstant.GOODS_CATEGORY_SET, Sets.newHashSet(transform));
        cacheService.expire(RedisConstant.GOODS_CATEGORY_SET, RedisConstant.SECONDS_CATEGORY_SET);
        logger.info("init goods category set success");
    }
    
    @Override
    public void initBrandSet() {
        if (cacheService.exist(RedisConstant.GOODS_BRAND_SET)) {
            return;
        }
        List<GoodsBrand> selectBrandList = goodsBrandMapper.selectBrandList();
        if (selectBrandList == null || selectBrandList.isEmpty()) {
            return;
        }
        
        Collection<Integer> transform = Collections2.transform(selectBrandList, new Function<GoodsBrand, Integer>() {
            
            @Override
            public Integer apply(GoodsBrand paramF) {
                return paramF.getId();
            }
        });
        cacheService.setSet(RedisConstant.GOODS_BRAND_SET, Sets.newHashSet(transform));
        cacheService.expire(RedisConstant.GOODS_BRAND_SET, RedisConstant.SECONDS_BRAND_SET);
        logger.info("init goods brand set success");
    }
    
    @Override
    public Set<Object> getCategorySet() {
        initCategorySet();
        Set<Object> set = cacheService.getSet(RedisConstant.GOODS_CATEGORY_SET);
        return set;
    }
    
    @Override
    public Set<Object> getBrandSet() {
        initBrandSet();
        Set<Object> set = cacheService.getSet(RedisConstant.GOODS_BRAND_SET);
        return set;
    }
    
    @Override
    public Set<Object> getPublishTimeSortGoodsItemIds(int categoryId, int brandId, int start, int count) {
        String sourceSetKey = RedisConstant.GOODS_ITEM_ID_PUBLISHTIME_ZSET;
        initGoodsItemPublishTimeZset();
        if (categoryId > 0 || brandId > 0) {
            Set<String> interSectSetKeys = Sets.newHashSet();
            String destKey = sourceSetKey;
            if (categoryId > 0) {
                initGoodsItemCategorySet(categoryId);
                interSectSetKeys.add(RedisConstant.GOODS_ITEM_ID_CATEGORY_SET + categoryId);
                destKey = destKey + ":" + RedisConstant.GOODS_ITEM_ID_CATEGORY_SET + categoryId;
            }
            if (brandId > 0) {
                initGoodsItemBrandSet(brandId);
                interSectSetKeys.add(RedisConstant.GOODS_ITEM_ID_BRAND_SET + brandId);
                destKey = destKey + ":" + RedisConstant.GOODS_ITEM_ID_BRAND_SET + brandId;
            }
            
            if (!cacheService.exist(destKey)) {
                cacheService.interSectAndStore(sourceSetKey, interSectSetKeys, destKey);
                cacheService.expire(destKey, RedisConstant.SECONDS_INTERSECT_SET);
            }
            Set<Object> sortedSet = cacheService.getSortedSet(destKey, start, count);
            return sortedSet;
        }
        return getGoodsItemPublishTimeZset(start, count);
    }
    
    @Override
    public Set<Object> getSalecountSortGoodsItemIds(int categoryId, int brandId, int start, int count) {
        String sourceSetKey = RedisConstant.GOODS_ITEM_ID_SALECOUNT_ZSET;
        initGoodsItemSaleCountZset();
        if (categoryId > 0 || brandId > 0) {
            Set<String> interSectSetKeys = Sets.newHashSet();
            String destKey = sourceSetKey;
            if (categoryId > 0) {
                initGoodsItemCategorySet(categoryId);
                interSectSetKeys.add(RedisConstant.GOODS_ITEM_ID_CATEGORY_SET + categoryId);
                destKey = destKey + ":" + RedisConstant.GOODS_ITEM_ID_CATEGORY_SET + categoryId;
            }
            if (brandId > 0) {
                initGoodsItemBrandSet(brandId);
                interSectSetKeys.add(RedisConstant.GOODS_ITEM_ID_BRAND_SET + brandId);
                destKey = destKey + ":" + RedisConstant.GOODS_ITEM_ID_BRAND_SET + brandId;
            }
            
            if (!cacheService.exist(destKey)) {
                cacheService.interSectAndStore(sourceSetKey, interSectSetKeys, destKey);
                cacheService.expire(destKey, RedisConstant.SECONDS_INTERSECT_SET);
            }
            Set<Object> sortedSet = cacheService.getSortedSet(destKey, start, count);
            return sortedSet;
        }
        return getGoodsItemSaleCountZset(start, count);
    }
}
