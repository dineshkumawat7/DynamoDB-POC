package com.amazon.aws.dynamodb.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CloudWatchLogs {

    private static final Logger logger = LoggerFactory.getLogger(CloudWatchLogs.class);

    public void log(int messageLogLevel, String source, String requestId, String message){
        String logTypeTag = LogsConstants.logTypeDeciderMap().getOrDefault(messageLogLevel,
                LogsConstants.DEBUG_LOG_TAG);
        sendToCloudWatch(String.format(LogsConstants.LOGGING_PATTERN, logTypeTag, requestId, source, message));
    }

    public void sendToCloudWatch(String logMessage) {
        logger.info(logMessage);
    }
}
