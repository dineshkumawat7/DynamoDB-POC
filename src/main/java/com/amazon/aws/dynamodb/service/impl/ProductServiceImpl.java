package com.amazon.aws.dynamodb.service.impl;

import com.amazon.aws.dynamodb.dto.ProductDto;
import com.amazon.aws.dynamodb.dto.common.Metadata;
import com.amazon.aws.dynamodb.dto.common.MsgInfo;
import com.amazon.aws.dynamodb.dto.common.Payload;
import com.amazon.aws.dynamodb.dto.common.Response;
import com.amazon.aws.dynamodb.entity.Product;
import com.amazon.aws.dynamodb.exception.ServiceException;
import com.amazon.aws.dynamodb.log.CloudWatchLogs;
import com.amazon.aws.dynamodb.repository.ProductRepo;
import com.amazon.aws.dynamodb.service.ProductService;
import com.amazon.aws.dynamodb.util.Constants;
import com.amazon.aws.dynamodb.util.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CloudWatchLogs logger;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public ResponseEntity<Response> createNewProduct(ProductDto productDto) {
        Response response = new Response();
        response.setMetadata(Utility.updatedMetadata(new Metadata()));
        try {
            Product product = mapToProduct(productDto, new Product());
            product.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
            product.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
            Product createdProduct = productRepo.save(product);
            response.setMsgInfo(new MsgInfo(Constants.CREATED_STATUS_CODE, Constants.SUCCESS_TAG, "New product created successfully"));
            response.setPayload(mapper.readValue(mapper.writeValueAsString(createdProduct), Payload.class));
        } catch (Exception e) {
            logger.log(3, response.getMetadata().getSource(), response.getMetadata().getRequestId(),
                    Utility.getLogMessage(ProductServiceImpl.class, Utility.getCurrentMethodName(), String.format("Response ::: %s", e.getMessage())));
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
        return ResponseEntity.status(Integer.parseInt(Constants.CREATED_STATUS_CODE)).body(response);
    }

    private Product mapToProduct(ProductDto productDto, Product product) {
        return Optional.ofNullable(productDto).map(dto -> {
            product.setId(Optional.ofNullable(product.getId()).orElse(UUID.randomUUID().toString()));
            product.setName(Optional.ofNullable(dto.getName()).orElse("Unknown"));
            product.setDescription(Optional.ofNullable(dto.getDescription()).orElse("No description"));
            product.setPrice(dto.getPrice());
            product.setCategory(Optional.ofNullable(dto.getCategory()).orElse("Uncategorized"));
            product.setQuantity(dto.getQuantity());
            product.setTags(Optional.ofNullable(dto.getTags()).orElseGet(List::of));
            product.setImageUrls(Optional.ofNullable(dto.getImageUrls()).orElseGet(List::of));
            product.setBrand(Optional.ofNullable(dto.getBrand()).orElse("No brand"));
            product.setRating(dto.getRating());
            product.setAvailable(true);
            return product;
        }).orElseThrow(() -> new IllegalArgumentException("Product DTO cannot be null"));
    }

    @Override
    public ResponseEntity<Response> getProductById(String productId) {
        Response response = new Response();
        response.setMetadata(Utility.updatedMetadata(new Metadata()));
        try {
            Optional<Product> optionalProduct = productRepo.findById(productId);
            if (optionalProduct.isPresent()) {
                response.setPayload(mapper.readValue(mapper.writeValueAsString(optionalProduct.get()), Payload.class));
                response.setMsgInfo(new MsgInfo(Constants.OK_STATUS_CODE, Constants.SUCCESS_TAG, "Product fetched successfully"));
            } else {
                throw new ServiceException(Constants.NOT_FOUND_STATUS_CODE, String.format("Product not found with ID: %s", productId));
            }
        } catch (ServiceException e) {
            logger.log(3, response.getMetadata().getSource(), response.getMetadata().getRequestId(),
                    Utility.getLogMessage(ProductServiceImpl.class, Utility.getCurrentMethodName(), String.format("Response ::: %s", e.getMessage())));
            throw new ServiceException(Constants.NOT_FOUND_STATUS_CODE, e.getExceptionMessage());
        } catch (Exception e) {
            logger.log(3, response.getMetadata().getSource(), response.getMetadata().getRequestId(),
                    Utility.getLogMessage(ProductServiceImpl.class, Utility.getCurrentMethodName(), String.format("Response ::: %s", e.getMessage())));
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
        return ResponseEntity.status(Integer.parseInt(Constants.OK_STATUS_CODE)).body(response);
    }

    @Override
    public ResponseEntity<Response> getAllProducts() {
        Response response = new Response();
        response.setMetadata(Utility.updatedMetadata(new Metadata()));
        try {
            Iterable<Product> productIterable = productRepo.findAll();
            List<Product> products = StreamSupport
                    .stream(productIterable.spliterator(), false)
                    .toList();
            response.setPayload(mapper.readValue(mapper.writeValueAsString(products), List.class));
            response.setMsgInfo(new MsgInfo(Constants.OK_STATUS_CODE, Constants.SUCCESS_TAG, "Product fetched successfully"));
        } catch (Exception e) {
            logger.log(3, response.getMetadata().getSource(), response.getMetadata().getRequestId(),
                    Utility.getLogMessage(ProductServiceImpl.class, Utility.getCurrentMethodName(), String.format("Response ::: %s", e.getMessage())));
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }

        return ResponseEntity.status(Integer.parseInt(Constants.OK_STATUS_CODE)).body(response);
    }

    @Override
    public ResponseEntity<Response> updateProduct(String productId, ProductDto productDto) {
        Response response = new Response();
        response.setMetadata(Utility.updatedMetadata(new Metadata()));
        try {
            Optional<Product> existingProduct = productRepo.findById(productId);
            if (existingProduct.isPresent()) {
                Product product = mapToProduct(productDto, existingProduct.get());
                product.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                Product updatedProduct = productRepo.save(product);
                response.setMsgInfo(new MsgInfo(Constants.OK_STATUS_CODE, Constants.SUCCESS_TAG, "Product updated successfully."));
                response.setPayload(mapper.readValue(mapper.writeValueAsString(updatedProduct), Payload.class));
            } else {
                throw new ServiceException(Constants.NOT_FOUND_STATUS_CODE, String.format("Product not found with ID: %s", productId));
            }
        } catch (ServiceException e) {
            logger.log(3, response.getMetadata().getSource(), response.getMetadata().getRequestId(),
                    Utility.getLogMessage(ProductServiceImpl.class, Utility.getCurrentMethodName(), String.format("Response ::: %s", e.getMessage())));
            throw new ServiceException(Constants.NOT_FOUND_STATUS_CODE, e.getExceptionMessage());
        } catch (Exception e) {
            logger.log(3, response.getMetadata().getSource(), response.getMetadata().getRequestId(),
                    Utility.getLogMessage(ProductServiceImpl.class, Utility.getCurrentMethodName(), String.format("Response ::: %s", e.getMessage())));
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
        return ResponseEntity.status(Integer.parseInt(Constants.OK_STATUS_CODE)).body(response);
    }

    @Override
    public ResponseEntity<Response> deleteProduct(String productId) {
        Response response = new Response();
        response.setMetadata(Utility.updatedMetadata(new Metadata()));
        try {
            Optional<Product> optionalProduct = productRepo.findById(productId);
            if (optionalProduct.isPresent()) {
                productRepo.delete(optionalProduct.get());
                response.setMsgInfo(new MsgInfo(Constants.OK_STATUS_CODE, Constants.SUCCESS_TAG, "Product deleted successfully."));
            } else {
                throw new ServiceException(Constants.NOT_FOUND_STATUS_CODE, String.format("Product not found with ID: %s", productId));
            }
        } catch (ServiceException e) {
            logger.log(3, response.getMetadata().getSource(), response.getMetadata().getRequestId(),
                    Utility.getLogMessage(ProductServiceImpl.class, Utility.getCurrentMethodName(), String.format("Response ::: %s", e.getMessage())));
            throw new ServiceException(Constants.NOT_FOUND_STATUS_CODE, e.getExceptionMessage());
        } catch (Exception e) {
            logger.log(3, response.getMetadata().getSource(), response.getMetadata().getRequestId(),
                    Utility.getLogMessage(ProductServiceImpl.class, Utility.getCurrentMethodName(), String.format("Response ::: %s", e.getMessage())));
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE, e.getMessage());
        }
        return ResponseEntity.status(Integer.parseInt(Constants.OK_STATUS_CODE)).body(response);
    }

}
