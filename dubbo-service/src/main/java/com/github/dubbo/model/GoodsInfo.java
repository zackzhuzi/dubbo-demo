package com.github.dubbo.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GoodsInfo {
    private int id;
    
    private int categoryId;
    
    private int brandId;
    
    private String goodsName;
    
    private String goodsDesc;
    
    private int status;
    
    private String entGoodsCode;
    
    private int createUser;
    
    private Date createDate;
    
    private Date modDate;
    
    private String modUserId;
    
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
    
    public String getGoodsName() {
        return goodsName;
    }
    
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    
    public String getGoodsDesc() {
        return goodsDesc;
    }
    
    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getEntGoodsCode() {
        return entGoodsCode;
    }
    
    public void setEntGoodsCode(String entGoodsCode) {
        this.entGoodsCode = entGoodsCode;
    }
    
    public int getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public Date getModDate() {
        return modDate;
    }
    
    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }
    
    public String getModUserId() {
        return modUserId;
    }
    
    public void setModUserId(String modUserId) {
        this.modUserId = modUserId;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
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
