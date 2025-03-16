package com.amazon.aws.dynamodb.util;

import com.amazon.aws.dynamodb.dto.common.Metadata;
import com.amazon.aws.dynamodb.exception.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Date;
import java.util.UUID;

public class Utility {

    /**
     * This method is used to get current time.
     *
     * @return Current time
     */
    public static String getCurrentDateTime() {
        return String.format("%s", new Date());
    }

    /**
     * This method is used to get current class name.
     *
     * @return Current class name
     */
    public static String getCurrentClassName() {
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }

    /**
     * This method is used to get current method name.
     *
     * @return Current method name
     */
    public static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    /**
     * This method is used to get log pattern.
     *
     * @param className
     * @param methodName
     * @param message
     * @return String - log message
     */
    public static String getLogMessage(Class<?> className, String methodName, String message) {
        return String.format("Time : %s ; Class : %s ; Method : %s ; Message : %s",
                new Date(), className.getSimpleName(), methodName, message);
    }

    /**
     * This method is used to convert class object to JSON string.
     *
     * @param object - Object of a class
     * @return String - JSON string
     * @throws ServiceException
     */
    public static String objectToJSONString(Object object) throws ServiceException {
        String jsonString = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            jsonString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ServiceException(e.getMessage(), Constants.INTERNAL_SERVER_ERROR_STATUS_CODE);
        }
        return jsonString;
    }

    /**
     * This method is used to update metadata information.
     *
     * @param metadata
     * @return Metadata - updated metadata
     */
    public static Metadata updatedMetadata(Metadata metadata) {
        if (metadata.getRequestId() == null || metadata.getRequestId().isBlank() || metadata.getRequestId().equalsIgnoreCase("")) {
            metadata.setRequestId(UUID.randomUUID().toString());
        }
        if (metadata.getSource() == null || metadata.getSource().isBlank() || metadata.getSource().equalsIgnoreCase("")) {
            metadata.setSource("Amazon");
        }
        if (metadata.getApiVersion() == null || metadata.getApiVersion().isBlank() || metadata.getApiVersion().equalsIgnoreCase("")) {
            metadata.setApiVersion("v0.0.1");
        }
        metadata.setTimestamp(String.format("%s", new Date()));
        return metadata;
    }
}
