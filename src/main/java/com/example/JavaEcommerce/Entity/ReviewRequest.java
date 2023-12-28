package com.example.JavaEcommerce.Entity;

public class ReviewRequest {

    private Long userId;
    private Long productId;
    private int rating;
    private String comment;

    // Constructors, getters, and setters

    public ReviewRequest() {
    }

    public ReviewRequest(Long userId, Long productId, int rating, String comment) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and setters (you can generate these using your IDE or write them manually)

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
