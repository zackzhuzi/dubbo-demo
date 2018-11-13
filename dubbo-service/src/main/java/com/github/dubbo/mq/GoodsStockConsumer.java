package com.github.dubbo.mq;

import javax.jms.MapMessage;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.dubbo.commons.exception.ErrorCode;
import com.github.dubbo.commons.exception.GoodsServiceException;
import com.github.dubbo.mapper.GoodsItemMapper;
import com.github.dubbo.mapper.GoodsItemStatMapper;

/**
 * 对于消费消息，建议是使用MessageListener机制，而不用receive方法，原因是receive方法会阻塞，那么在多个消息队列时 ，会阻塞于某一个没有消息的队列，而没有去消费有消息的队列。
 * 
 * @author yuzhu.peng
 */
public class GoodsStockConsumer extends AbstractConsumer {
    
    @Autowired
    private GoodsItemMapper goodsItemMapper;
    
    @Autowired
    private GoodsItemStatMapper goodsItemStatMapper;
    
    private static final Logger logger = LoggerFactory.getLogger(GoodsStockConsumer.class);
    
    @Override
    public void handleMessgage(Message message)
        throws Exception {
        MapMessage mapMessage = (MapMessage)message;
        int goodsItemId = mapMessage.getInt("goodsItemId");
        int count = mapMessage.getInt("count");
        
        int updateStock = goodsItemMapper.updateStock(goodsItemId, count);
        if (updateStock > 0) {
            int updateSaleCount = goodsItemStatMapper.updateSaleCount(goodsItemId, count);
            if (updateSaleCount > 0) {
                logger.info("更新库存和增加销量成功|goodsItemId={}|count={}", goodsItemId, count);
            }
            else {
                logger.error("增加销量失败|goodsItemId={}|count={}", goodsItemId, count);
                throw new GoodsServiceException(ErrorCode.GOODS_SALE_COUNT_UPDATE_ERROR);
            }
        }
        else {
            logger.error("更新库存失败|goodsItemId={}|count={}", goodsItemId, count);
            throw new GoodsServiceException(ErrorCode.GOODS_STOCK_UPDATE_ERROR);
        }
    }
}
