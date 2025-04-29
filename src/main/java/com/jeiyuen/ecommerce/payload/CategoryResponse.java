package com.jeiyuen.ecommerce.payload;

import java.util.List;

public class CategoryResponse{

    private List<CategoryDTO> content;

    public CategoryResponse() {
    }
    public CategoryResponse(List<CategoryDTO> content) {
        this.content = content;
    }
    
    public List<CategoryDTO> getContent() {
        return content;
    }
    public void setContent(List<CategoryDTO> content) {
        this.content = content;
    }


}
