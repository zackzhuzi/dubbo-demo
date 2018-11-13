package test.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.base.BaseTest;

import com.github.dubbo.model.GoodsInfo;
import com.github.dubbo.service.IGoodsInfoService;

public class GoodsInfoTest extends BaseTest {
    
    @Autowired
    IGoodsInfoService goodsInfoService;
    
    @Test
    public void addGoodsInfoTest() {
        StringBuilder sb = null;
        
        for (int i = 0; i < 10; i++) {
            sb = new StringBuilder();
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setBrandId(2);
            goodsInfo.setCategoryId(3);
            goodsInfo.setGoodsDesc(sb.append("Huawei").append(i).append(i).append(i).append(i).toString());
            goodsInfo.setGoodsName("Huawei" + i);
            goodsInfo.setStatus(GoodsInfo.Status.Valid.getValue());
            goodsInfo.setModDate(new Date());
            boolean insert = goodsInfoService.insert(goodsInfo);
            Assert.assertTrue(insert);
        }
    }
}
