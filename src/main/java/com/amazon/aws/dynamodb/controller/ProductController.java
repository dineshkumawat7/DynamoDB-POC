package com.amazon.aws.dynamodb.controller;

import com.amazon.aws.dynamodb.dto.ProductDto;
import com.amazon.aws.dynamodb.dto.common.Metadata;
import com.amazon.aws.dynamodb.dto.common.MsgInfo;
import com.amazon.aws.dynamodb.dto.common.Response;
import com.amazon.aws.dynamodb.exception.ServiceException;
import com.amazon.aws.dynamodb.log.CloudWatchLogs;
import com.amazon.aws.dynamodb.service.ProductService;
import com.amazon.aws.dynamodb.util.Constants;
import com.amazon.aws.dynamodb.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dynamodb/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CloudWatchLogs logger;

    @PostMapping
    public ResponseEntity<Response> createNewProduct(ProductDto productDto) {
        ResponseEntity<Response> response = null;
        try {
            response = productService.createNewProduct(productDto);
        } catch (ServiceException e) {
            return getErrorResponse(new Metadata(), e.getMsgCode(), e.getMessage());
        }
        return getSuccessResponse(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllProducts() {
        ResponseEntity<Response> response = null;
        try {
            response = productService.getAllProducts();
        } catch (ServiceException e) {
            return getErrorResponse(new Metadata(), e.getMsgCode(), e.getMessage());
        }
        return getSuccessResponse(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProductById(@PathVariable String id) {
        ResponseEntity<Response> response = null;
        try {
            response = productService.getProductById(id);
        } catch (ServiceException e) {
            return getErrorResponse(new Metadata(), e.getMsgCode(), e.getMessage());
        }
        return getSuccessResponse(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProductById(@PathVariable("id") String productId, ProductDto productDto) {
        ResponseEntity<Response> response = null;
        try {
            response = productService.updateProduct(productId, productDto);
        } catch (ServiceException e) {
            return getErrorResponse(new Metadata(), e.getMsgCode(), e.getMessage());
        }
        return getSuccessResponse(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable String id) {
        ResponseEntity<Response> response = null;
        try {
            response = productService.deleteProduct(id);
        } catch (ServiceException e) {
            return getErrorResponse(new Metadata(), e.getMsgCode(), e.getMessage());
        }
        return getSuccessResponse(response);
    }

    /**
     * This method is used to set payload, message description and metadata to
     * Response.
     *
     * @param response - Response object
     * @return Response
     */
    public ResponseEntity<Response> getSuccessResponse(ResponseEntity<Response> response) {
        Response responseBody = response.getBody();
        try {
            logger.log(1, responseBody.getMetadata().getSource(), responseBody.getMetadata().getRequestId(),
                    Utility.getLogMessage(ProductController.class, Utility.getCurrentMethodName(), String.format("Response ::: %s", Utility.objectToJSONString(response))));
        } catch (Exception e) {
            response.getBody().setMsgInfo(
                    new MsgInfo(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, Constants.FAILURE_TAG, e.getMessage())
            );
        }
        return response;
    }

    /**
     * This method is used to set error message and metadata to Response.
     *
     * @param metadata     - metadata
     * @param errorCode    - error code
     * @param errorMessage - error message
     * @return Response
     */
    public ResponseEntity<Response> getErrorResponse(Metadata metadata, String errorCode, String errorMessage) {
        Response response = new Response(Utility.updatedMetadata(metadata), new MsgInfo(errorCode, Constants.FAILURE_TAG, errorMessage));
        try {
            logger.log(3, metadata.getSource(), metadata.getRequestId(),
                    Utility.getLogMessage(ProductController.class, Utility.getCurrentMethodName(), String.format("Response ::: %s", Utility.objectToJSONString(response))));
        } catch (Exception e) {
            response.setMsgInfo(
                    new MsgInfo(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, Constants.FAILURE_TAG, e.getMessage())
            );
        }
        return ResponseEntity.status(Integer.parseInt(errorCode)).body(response);
    }

    /**
     * This endpoint is consumed by load balancer for health check.
     *
     * @return Object
     */
    @GetMapping("/status")
    public Object healthCheck() {
        return "Application is running up";
    }
}
