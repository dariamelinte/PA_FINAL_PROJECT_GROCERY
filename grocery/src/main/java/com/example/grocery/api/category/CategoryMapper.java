package com.example.grocery.api.category;

public class CategoryMapper {
    public static Category dtoToEntity(CategoryDTO dto){
        var cat = new Category();
        cat.setName(dto.getName());
        return cat;
    }

}
