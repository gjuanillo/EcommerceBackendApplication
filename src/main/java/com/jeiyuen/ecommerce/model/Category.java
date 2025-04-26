package com.jeiyuen.ecommerce.model;

public class Category{
    private Long categoryId;
    private String categoryMame;

    public Category(){}
    public Category(Long categoryId, String categoryMame) {
        this.categoryId = categoryId;
        this.categoryMame = categoryMame;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryMame() {
        return categoryMame;
    }
    public void setCategoryMame(String categoryMame) {
        this.categoryMame = categoryMame;
    }

}
