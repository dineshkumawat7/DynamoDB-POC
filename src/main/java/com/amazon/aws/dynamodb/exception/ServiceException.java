package com.amazon.aws.dynamodb.exception;

public class ServiceException extends RuntimeException{
    private final String msgCode;
    private final String exceptionMessage;

    public ServiceException(String msgCode, String exceptionMessage){
        super(exceptionMessage);
        this.msgCode = msgCode;
        this.exceptionMessage = exceptionMessage;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
