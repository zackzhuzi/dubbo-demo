package com.github.dubbo.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GoodsItem {
    
    private int id;
    
    private String goodsItemName;
    
    private int goodsId;
    
    private BigDecimal price;
    
    private BigDecimal marketPrice;
    
    private BigDecimal shopPrice;
    
    private int status;
    
    private Date createDate;
    
    private Date updateDate;
    
    private int categoryId;
    
    private int brandId;
    
    private int stock;
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public int getBrandId() {
        return brandId;
    }
    
    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
    
    public String getGoodsItemName() {
        return goodsItemName;
    }
    
    public void setGoodsItemName(String goodsItemName) {
        this.goodsItemName = goodsItemName;
    }
    
    public int getGoodsId() {
        return goodsId;
    }
    
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }
    
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }
    
    public BigDecimal getShopPrice() {
        return shopPrice;
    }
    
    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public enum Status {
        Valid(0), Invalid(1);
        
        private int value;
        
        private static Map<Integer, Status> map;
        
        static {
            map = new HashMap<Integer, Status>();
            for (Status item : Status.values()) {
                map.put(item.getValue(), item);
            }
        }
        
        Status(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
        
        public static Status fromValue(int value) {
            return map.get(value);
        }
    }
    
}
