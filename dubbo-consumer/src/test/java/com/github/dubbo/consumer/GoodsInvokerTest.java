package com.github.dubbo.consumer;

import java.io.IOException;
import java.util.List;

import model.GoodsConstant;
import model.GoodsItemDTO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GoodsInvokerTest extends BaseTest {
    
    @Autowired
    private GoodsInvoker goodsInvoker;
    
    @Test
    public void listTest() {
        List<GoodsItemDTO> listGoods = goodsInvoker.listGoods(0, 10, 6, 1, GoodsConstant.SORT_SALE);
        for (GoodsItemDTO goodsItemDTO : listGoods) {
            System.out.println(goodsItemDTO);
        }
        try {
            System.in.read();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
