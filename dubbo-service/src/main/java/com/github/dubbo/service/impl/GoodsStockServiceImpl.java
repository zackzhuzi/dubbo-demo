package com.github.dubbo.service.impl;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.dubbo.cache.CacheService;
import com.github.dubbo.constant.CommonConstant;
import com.github.dubbo.constant.MQConstant;
import com.github.dubbo.constant.RedisConstant;
import com.github.dubbo.mq.Producer;
import com.github.dubbo.service.IGoodsCacheService;
import com.github.dubbo.service.IGoodsStockService;

@Service
public class GoodsStockServiceImpl implements IGoodsStockService {
    private static final Logger logger = LoggerFactory.getLogger(GoodsStockServiceImpl.class);
    
    @Autowired
    private CacheService cacheService;
    
    @Autowired
    private IGoodsCacheService goodsCacheService;
    
    @Autowired
    private Producer producer;
    
    @Override
    public boolean reduceStock(final int goodsItemId, final int count) {
        Map<String, Object> goodsStatCache = goodsCacheService.getGoodsStatCache(goodsItemId);
        /**
         * 前置：获取分布式锁<br>
         * 第一步：修改stock缓存，并返回状态<br>
         * 第二步：发送修改数据库的消息至MQ<br>
         */
        int stock = Integer.parseInt(goodsStatCache.get(RedisConstant.GOODS_ITEM_STAT_FIELD_STOCK).toString());
        if (stock < count) {
            logger.error("stock数量小于购买数量");
            return false;
        }
        String requestId = CommonConstant.ServerId + "@$#" + Thread.currentThread().getId();
        try {
            if (cacheService.lockWithTimeout(RedisConstant.LOCK_KEY_STOCK, requestId, 10)) {
                Long hincr =
                    cacheService.hincr(RedisConstant.GOODS_ITEM_STAT_PREFIX + goodsItemId,
                        RedisConstant.GOODS_ITEM_STAT_FIELD_STOCK,
                        -count);
                if (hincr >= 0) {
                    Long hincr2 =
                        cacheService.hincr(RedisConstant.GOODS_ITEM_STAT_PREFIX + goodsItemId,
                            RedisConstant.GOODS_ITEM_STAT_FIELD_SALECOUNT,
                            count);
                    if (hincr2 >= 0) {
                        // Send MQ
                        producer.sendMessage(MQConstant.GOODS_STOCK_QUEUE_NAME, new MessageCreator() {
                            public Message createMessage(Session session)
                                throws JMSException {
                                MapMessage mapMessage = session.createMapMessage();
                                mapMessage.setStringProperty("goodsItemStockType", "reduce");
                                mapMessage.setInt("goodsItemId", goodsItemId);
                                mapMessage.setInt("count", count);
                                return mapMessage;
                            }
                        });
                        return true;
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("更新库存异常", e);
        }
        finally {
            cacheService.unLock(RedisConstant.LOCK_KEY_STOCK, requestId);
        }
        return false;
    }
}
