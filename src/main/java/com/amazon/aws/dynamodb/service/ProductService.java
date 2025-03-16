package com.amazon.aws.dynamodb.service;

import com.amazon.aws.dynamodb.dto.ProductDto;
import com.amazon.aws.dynamodb.dto.common.Response;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<Response> createNewProduct(ProductDto productDto);
    ResponseEntity<Response> getProductById(String productId);
    ResponseEntity<Response> getAllProducts();
    ResponseEntity<Response> updateProduct(String productId, ProductDto productDto);
    ResponseEntity<Response> deleteProduct(String productId);
}
