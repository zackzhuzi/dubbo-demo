package com.github.dubbo.commons.exception;

public class GoodsServiceException extends Exception {
    private static final long serialVersionUID = -2630867353709843362L;
    
    private ErrorCode errorCode;
    
    public GoodsServiceException() {
        super();
    }
    
    public GoodsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public GoodsServiceException(String message) {
        super(message);
    }
    
    public GoodsServiceException(Throwable cause) {
        super(cause);
    }
    
    public GoodsServiceException(ErrorCode code, Throwable cause) {
        super("errorCode=" + code.getCode() + ",errorMsg=" + code.getMsg(), cause);
        this.errorCode = code;
    }
    
    public GoodsServiceException(ErrorCode code) {
        super("errorCode=" + code.getCode() + ",errorMsg=" + code.getMsg());
        this.errorCode = code;
    }
    
    public ErrorCode getErrorCode() {
        return errorCode;
    }
    
}
