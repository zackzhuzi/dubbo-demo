package com.github.dubbo.consumer;

import java.util.List;

import model.GoodsItemDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dubbo.facade.GoodsFacade;

/**
 * 消费者
 * 
 * @author yuzhupeng
 */
@Component
public class GoodsInvoker {
    
    @Autowired
    private GoodsFacade goodsFacade;
    
    public List<GoodsItemDTO> listGoods(int start, int count, int categoryId, int brandId, String sort) {
        List<GoodsItemDTO> goodsItems = goodsFacade.getGoodsItems(start, count, categoryId, brandId, sort);
        return goodsItems;
    }
    
}
