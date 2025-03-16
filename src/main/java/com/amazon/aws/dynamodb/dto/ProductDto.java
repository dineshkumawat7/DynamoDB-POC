package com.amazon.aws.dynamodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private double price;

    @JsonProperty("category")
    private String category;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("imageUrls")
    private List<String> imageUrls;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("rating")
    private int rating;

    public ProductDto() {
        super();
    }

    public ProductDto(String name, String description, double price, String category, int quantity, List<String> tags, List<String> imageUrls, String brand, int rating) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.tags = tags;
        this.imageUrls = imageUrls;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setTags(List<String> tags) {
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
}
