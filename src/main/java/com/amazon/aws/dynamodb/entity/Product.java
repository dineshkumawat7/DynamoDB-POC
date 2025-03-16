package com.amazon.aws.dynamodb.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@DynamoDBTable(tableName = "PRODUCT_TABLE")
public class Product implements Serializable {
    @DynamoDBHashKey
    @JsonProperty("id")
    private String id = UUID.randomUUID().toString();

    @DynamoDBAttribute
    @JsonProperty("name")
    private String name;

    @DynamoDBAttribute
    @JsonProperty("description")
    private String description;

    @DynamoDBAttribute
    @JsonProperty("price")
    private double price;

    @DynamoDBAttribute
    @JsonProperty("category")
    private String category;

    @DynamoDBAttribute
    @JsonProperty("quantity")
    private int quantity;

    @DynamoDBAttribute
    @JsonProperty("tags")
    private List<String> tags;

    @DynamoDBAttribute
    @JsonProperty("imageUrls")
    private List<String> imageUrls;

    @DynamoDBAttribute
    @JsonProperty("brand")
    private String brand;

    @DynamoDBAttribute
    @JsonProperty("rating")
    private int rating;

    @DynamoDBAttribute
    @JsonProperty("createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdAt;

    @DynamoDBAttribute
    @JsonProperty("updatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedAt;

    @DynamoDBAttribute
    @JsonProperty("available")
    private boolean available;

    public Product() {
    }

    public Product(String id, String name, String description, double price, String category, int quantity, List<String> tags, List<String> imageUrls,
                   String brand, int rating, String createdAt, String updatedAt, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.tags = tags;
        this.imageUrls = imageUrls;
        this.brand = brand;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags() {
        this.tags = tags;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
