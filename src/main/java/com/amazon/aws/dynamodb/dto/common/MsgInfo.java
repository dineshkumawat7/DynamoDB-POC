package com.amazon.aws.dynamodb.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgInfo {
    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;


    public MsgInfo() {
    }

    public MsgInfo(String statusCode, String status, String message) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
    }

    @JsonProperty("statusCode")
    public String getStatusCode() {
        return statusCode;
    }

    @JsonProperty("statusCode")
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }
}
