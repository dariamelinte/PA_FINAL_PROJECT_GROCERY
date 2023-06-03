package com.example.grocery.api.products;

public class ProductMapper {
    public static Product dtoToEntity(ProductDTO dto){
        var prod = new Product();
        prod.setName(dto.getName());
        prod.setDescription(dto.getDescription());
        return prod;
    }
}
