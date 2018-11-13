package test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.base.BaseTest;

import com.github.dubbo.model.GoodsCategory;
import com.github.dubbo.service.IGoodsCategoryService;

public class GoodsCategoryTest extends BaseTest {

    @Autowired
    IGoodsCategoryService goodsCategoryService;

    @Test
    public void addGoodsCategoryTest() {
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategoryName("Electronics");
        goodsCategory.setGrade(1);
        goodsCategory.setOrderNum(1);
        goodsCategory.setStatus(GoodsCategory.Status.Valid.getValue());
        int goodsCategoryId = goodsCategoryService.insertGoodsCategory(goodsCategory);
        Assert.assertNotEquals(0, goodsCategoryId);

        GoodsCategory subGoodsCategory = new GoodsCategory();
        subGoodsCategory.setCategoryName("Phone");
        subGoodsCategory.setGrade(2);
        subGoodsCategory.setOrderNum(1);
        subGoodsCategory.setStatus(GoodsCategory.Status.Valid.getValue());
        subGoodsCategory.setParent(goodsCategory.getId());
        int goodsCategoryId2 = goodsCategoryService.insertGoodsCategory(subGoodsCategory);
        Assert.assertNotEquals(0, goodsCategoryId2);
    }
}
