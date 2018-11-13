package com.github.dubbo.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GoodsBrand {
    private int id;
    private String brandName;
    private String brandDesc;
    private int    orderNum;
    private int    isShow;
    private String logo;
    private Date   createDate;


    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public enum IsShow {
        Show(0), NotShow(1);

        private int                         value;

        private static Map<Integer, IsShow> map;

        static {
            map = new HashMap<Integer, IsShow>();
            for (IsShow item : IsShow.values()) {
                map.put(item.getValue(), item);
            }
        }

        IsShow(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static IsShow fromValue(int value) {
            return map.get(value);
        }
    }
}
