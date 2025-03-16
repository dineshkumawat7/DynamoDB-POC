package com.amazon.aws.dynamodb.log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LogsConstants {
    public static final String LOGGING_PATTERN ="Log type: %s ; Request ID: %s ; Source: %s ; %s";
    public static final String DEBUG_LOG_TAG = "DEBUG";
    public static final String INFO_LOG_TAG = "INFO";
    public static final String ERROR_LOG_TAG = "ERROR";

    public LogsConstants() {
    }

    public static final Map<Integer, String> logTypeDeciderMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(2, INFO_LOG_TAG);
        map.put(3, ERROR_LOG_TAG);
        return Collections.unmodifiableMap(map);
    }
}
