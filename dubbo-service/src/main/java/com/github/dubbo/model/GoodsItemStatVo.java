package com.github.dubbo.model;

public class GoodsItemStatVo {
    private int goodsItemId;
    
    private int stock;
    
    private int saleCount;
    
    public int getGoodsItemId() {
        return goodsItemId;
    }
    
    public void setGoodsItemId(int goodsItemId) {
        this.goodsItemId = goodsItemId;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public int getSaleCount() {
        return saleCount;
    }
    
    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }
    
}
