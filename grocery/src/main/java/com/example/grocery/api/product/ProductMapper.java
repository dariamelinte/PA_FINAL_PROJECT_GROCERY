package com.example.grocery.api.product;

public class ProductMapper {
    public static Product dtoToEntity(ProductDTO dto){
        var prod = new Product();
        prod.setName(dto.getName());
        prod.setDescription(dto.getDescription());
        prod.setCategories(dto.getCategories());
        return prod;
    }
}
