package com.cognifide.home.work.controller;

public class CustomErrorType {

    private int errorCode;
    private String errorMessage;

    public CustomErrorType(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
