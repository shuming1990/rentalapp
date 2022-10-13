package com.rental.car.rentalapp.infrasturcture.exception;


import lombok.Getter;

@Getter
public class AppException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    protected int errorCode;
    protected String errorMsg;
    protected ResultCode resultCode;
    public AppException() {
        super();
    }

    public AppException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.errorCode = resultCode.getCode();
        this.errorMsg = resultCode.getMessage();
        this.resultCode = resultCode;
    }
    public AppException(ResultCode resultCode, String otherMessage) {
        super(resultCode.getMessage()+" "+otherMessage);
        this.errorCode = resultCode.getCode();
        this.errorMsg = resultCode.getMessage()+" "+otherMessage;
        this.resultCode = resultCode;
    }

    public AppException(ResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.errorCode = resultCode.getCode();
        this.errorMsg = resultCode.getMessage();
        this.resultCode = resultCode;
    }

}
