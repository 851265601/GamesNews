package com.example.newapp.entity;

import java.io.Serializable;

public class CategoryEntity implements Serializable {

    /**
     * categoryId : 1
     * categoryName : 游戏
     */

    private int categoryId;
    private String categoryName;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
