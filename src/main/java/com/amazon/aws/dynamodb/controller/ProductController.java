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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new product", description = "Creates a new product and returns a response with metadata.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Response> createNewProduct(@RequestBody ProductDto productDto) {
        ResponseEntity<Response> response = null;
        try {
            response = productService.createNewProduct(productDto);
        } catch (ServiceException e) {
            return getErrorResponse(new Metadata(), e.getMsgCode(), e.getMessage());
        }
        return getSuccessResponse(response);
    }

    @Operation(summary = "Get all products", description = "Fetches a list of all available products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Get product by ID", description = "Fetches a product based on the provided product ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully retrieved",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Update product by ID", description = "Updates the product details for the given product ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully updated",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProductById(@PathVariable("id") String productId, @RequestBody ProductDto productDto) {
        ResponseEntity<Response> response = null;
        try {
            response = productService.updateProduct(productId, productDto);
        } catch (ServiceException e) {
            return getErrorResponse(new Metadata(), e.getMsgCode(), e.getMessage());
        }
        return getSuccessResponse(response);
    }

    @Operation(summary = "Delete product by ID", description = "Deletes a product based on the provided product ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully deleted",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Health Check", description = "Checks the status of the application.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application is running"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/status")
    public Object healthCheck() {
        return "Application is running up";
    }
}
