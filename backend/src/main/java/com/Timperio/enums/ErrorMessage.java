package com.Timperio.enums;

public enum ErrorMessage {
    NO_CUSTOMER_FOUND("No customer found"),
    UNAUTHORIZED("Unauthorized: You need to authenticate to access this resource."),
    FORBIDDEN("Access Denied: You do not have the required permissions to access this resource.");

    private final String errMsg;

    ErrorMessage(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getMessage() {
        return this.errMsg;
    }
}
