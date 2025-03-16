package com.amazon.aws.dynamodb.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class Response {
    @JsonProperty("metadata")
    private Metadata metadata;

    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;

    @JsonProperty("payload")
    private Object payload;

    public Response() {
    }

    public Response(Metadata metadata, MsgInfo msgInfo) {
        this.metadata = metadata;
        this.msgInfo = msgInfo;
    }

    public Response(Metadata metadata, MsgInfo msgInfo, Object payload) {
        this.metadata = metadata;
        this.msgInfo = msgInfo;
        this.payload = payload;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("msgInfo")
    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    @JsonProperty("msgInfo")
    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    @JsonProperty("payload")
    public Object getPayload() {
        return payload;
    }

    @JsonProperty("payload")
    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @JsonProperty("payload")
    public <T> T getPayloadAs(Class<T> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(payload, type);
    }

    @JsonProperty("payload")
    public <T> List<T> getPayloadAsList(Class<T> type) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(payload, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
    }
}
