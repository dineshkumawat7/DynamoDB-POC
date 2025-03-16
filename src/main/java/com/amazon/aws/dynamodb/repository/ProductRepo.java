package com.amazon.aws.dynamodb.repository;

import com.amazon.aws.dynamodb.entity.Product;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface ProductRepo extends CrudRepository<Product, String> {

}
