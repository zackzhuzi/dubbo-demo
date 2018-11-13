package com.github.dubbo.commons.exception;

public class ErrorCode {
    public static ErrorCode SUCCESS = new ErrorCode("0000", "成功");
    
    public static ErrorCode GOODS_STOCK_UPDATE_ERROR = new ErrorCode("0001", "更新货物库存异常");
    public static ErrorCode GOODS_SALE_COUNT_UPDATE_ERROR = new ErrorCode("0002", "更新货物销量异常");
    
    protected ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    private String code;
    
    private String msg;
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
