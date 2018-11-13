package test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.base.BaseTest;

import com.github.dubbo.model.GoodsBrand;
import com.github.dubbo.service.IGoodsBrandService;

public class GoodsBrandTest extends BaseTest {
    
    @Autowired
    IGoodsBrandService goodsBrandService;
    
    @Test
    public void addGoodsBrandTest() {
        GoodsBrand goodsBrand = new GoodsBrand();
        goodsBrand.setBrandDesc("HuaWei is not an apple but an electronic product");
        goodsBrand.setBrandName("HuaWei");
        goodsBrand.setIsShow(GoodsBrand.IsShow.Show.getValue());
        goodsBrand.setLogo("https://img.alicdn.com/imgextra/i3/2616970884/TB26kv8mGagSKJjy0FbXXa.mVXa_!!2616970884.jpg");
        goodsBrand.setOrderNum(2);
        boolean insert = goodsBrandService.insert(goodsBrand);
        Assert.assertTrue(insert);
    }
}
