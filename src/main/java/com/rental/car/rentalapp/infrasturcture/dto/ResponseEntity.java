package com.rental.car.rentalapp.infrasturcture.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.car.rentalapp.infrasturcture.exception.ResultCode;
import lombok.Data;

import java.io.Serializable;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseEntity<T> implements Serializable {
    private static final long serialVersionUID = -1L;
    private int responseCode;
    private String responseMessage;
    private T responseObject;

    public ResponseEntity(ResultCode resultCode){
        this.responseCode = resultCode.getCode();
        this.responseMessage = resultCode.getMessage();
    }

    public ResponseEntity(ResultCode resultCode, String otherMessage,T data){
        this.responseCode = resultCode.getCode();
        this.responseMessage = otherMessage;
        this.setResponseObject(data);
    }

    public ResponseEntity(ResultCode resultCode,T data){
        this.responseCode = resultCode.getCode();
        this.responseMessage = resultCode.getMessage();
        this.setResponseObject(data);
    }
}
