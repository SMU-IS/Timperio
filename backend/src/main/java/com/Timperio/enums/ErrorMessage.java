package com.Timperio.enums;

public enum ErrorMessage {
    NO_CUSTOMER_FOUND("No customer found");

    private final String errMsg;

    ErrorMessage(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return errMsg;
    }
}
