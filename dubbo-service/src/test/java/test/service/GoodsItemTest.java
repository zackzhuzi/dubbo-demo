package test.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.base.BaseTest;

import com.github.dubbo.mapper.GoodsItemMapper;
import com.github.dubbo.model.GoodsInfo;
import com.github.dubbo.model.GoodsItem;
import com.github.dubbo.service.IGoodsCacheService;
import com.github.dubbo.service.IGoodsInfoService;
import com.github.dubbo.service.IGoodsItemService;
import com.google.common.collect.Maps;

public class GoodsItemTest extends BaseTest {
    
    @Autowired
    IGoodsInfoService goodsInfoService;
    
    @Autowired
    IGoodsItemService GoodsItemService;
    
    @Autowired
    IGoodsCacheService goodsCacheService;
    
    @Autowired
    private GoodsItemMapper GoodsItemMapper;
    
    @Test
    public void addGoodsItemTest() {
        Map<String, Object> paramMap = Maps.newHashMap();
        List<GoodsInfo> selectByMap = goodsInfoService.selectByMap(paramMap);
        for (GoodsInfo goodsInfo : selectByMap) {
            for (int i = 0; i < 10; i++) {
                try {
                    GoodsItem goodsItem = new GoodsItem();
                    goodsItem.setGoodsId(goodsInfo.getId());
                    goodsItem.setGoodsItemName(goodsInfo.getGoodsName() + "_" + i);
                    goodsItem.setMarketPrice(BigDecimal.valueOf(1000));
                    goodsItem.setPrice(BigDecimal.valueOf(800));
                    goodsItem.setShopPrice(BigDecimal.valueOf(666));
                    goodsItem.setStatus(GoodsItem.Status.Valid.getValue());
                    goodsItem.setStock(100);
                    goodsItem.setCategoryId(goodsInfo.getCategoryId());
                    goodsItem.setBrandId(goodsInfo.getBrandId());
                    boolean insert = GoodsItemService.insert(goodsItem);
                    Assert.assertTrue(insert);
                    goodsCacheService.initGoodsCache(goodsItem.getId());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
