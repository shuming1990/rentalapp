package com.rental.car.rentalapp.infrasturcture.exception;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "success"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "No Authentication."),
    NOT_FOUND(404, "Interface Not Exist"),
    INTERNAL_SERVER_ERROR(500, "System Internal Error!!"),
    METHOD_NOT_ALLOWED(405,"Method Not Allowed."),

    RULE_VIOLATION(1001, ""),
    NOT_SAVE(1002, ""),
    ;
    private Integer code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
