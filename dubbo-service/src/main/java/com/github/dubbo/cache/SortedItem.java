package com.github.dubbo.cache;

public class SortedItem {
    private Object value;
    
    private Double score;
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    @Override
    public String toString() {
        return "SortedItem [value=" + value.toString() + ", score=" + score + "]";
    }
    
}
