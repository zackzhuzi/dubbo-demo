package com.github.dubbo.model;

import java.util.Date;

public class GoodsItemStat {
    private int id;
    
    private int goodsItemId;
    
    private int saleCount;
    
    private Date lastUpdateTime;
    
    private int goodsId;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getGoodsItemId() {
        return goodsItemId;
    }
    
    public void setGoodsItemId(int goodsItemId) {
        this.goodsItemId = goodsItemId;
    }
    
    public int getSaleCount() {
        return saleCount;
    }
    
    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }
    
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }
    
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    public int getGoodsId() {
        return goodsId;
    }
    
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
    
}
