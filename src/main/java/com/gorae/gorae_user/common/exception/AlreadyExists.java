package com.gorae.gorae_user.common.exception;

public class AlreadyExists extends ClientError {
    public AlreadyExists(String message) {
        this.errorCode = "AlreadyExists";
        this.errorMessage = message;
    }
}
