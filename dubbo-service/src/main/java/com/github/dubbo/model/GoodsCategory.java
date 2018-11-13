package com.github.dubbo.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GoodsCategory {
    private int id;
    
    private String categoryName;
    
    private int grade;
    
    private int parent;
    
    private int orderNum;
    
    private int status;
    
    private Date createDate;
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public int getGrade() {
        return grade;
    }
    
    public void setGrade(int grade) {
        this.grade = grade;
    }
    
    public int getParent() {
        return parent;
    }
    
    public void setParent(int parent) {
        this.parent = parent;
    }
    
    public int getOrderNum() {
        return orderNum;
    }
    
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
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
