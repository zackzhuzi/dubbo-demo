package com.github.dubbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dubbo.mapper.GoodsItemMapper;
import com.github.dubbo.service.IGoodsCacheService;

/**
 * 
 * @author yuzhu.peng
 *
 */
@Component
public class CacheInitTask implements InitializingBean {
    
    private static Logger logger = LoggerFactory.getLogger(CacheInitTask.class);
    
    @Autowired
    private IGoodsCacheService goodsCacheService;
    
    @Autowired
    private GoodsItemMapper goodsItemMapper;
    
    public void initGoodsCache() {
        logger.info("Initing Goods Cache Begins");
        goodsCacheService.initAllSet();
        List<Integer> selectGoodsItemIds = goodsItemMapper.selectGoodsItemIds(0, 0, 0, 0);
        for (Integer goodsItemId : selectGoodsItemIds) {
            goodsCacheService.initGoodsCache(goodsItemId);
            goodsCacheService.initGoodsStatCache(goodsItemId);
        }
        logger.info("Initing Goods Cache finishes");
    }
    
    @Override
    public void afterPropertiesSet()
        throws Exception {
        this.initGoodsCache();
    }
}
