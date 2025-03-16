package com.amazon.aws.dynamodb.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {
    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("apiVersion")
    private String apiVersion;

    @JsonProperty("source")
    private String source;

    public Metadata() {
    }

    public Metadata(String timestamp, String requestId, String apiVersion, String source) {
        this.timestamp = timestamp;
        this.requestId = requestId;
        this.apiVersion = apiVersion;
        this.source = source;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("requestId")
    public String getRequestId() {
        return requestId;
    }

    @JsonProperty("requestId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @JsonProperty("apiVersion")
    public String getApiVersion() {
        return apiVersion;
    }

    @JsonProperty("apiVersion")
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }
}
