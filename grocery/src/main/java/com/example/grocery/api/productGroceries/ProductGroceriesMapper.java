package com.example.grocery.api.productGroceries;

public class ProductGroceriesMapper {
    public static ProductGroceries dtoToEntity(ProductGroceriesDTO dto){
        var entity = new ProductGroceries();
        entity.setProductId(dto.getProductId());
        entity.setSellType(dto.getSellType());
        entity.setGroceryId(dto.getGroceryId());
        entity.setSellPrice(dto.getSellPrice());
        entity.setBuyPrice(dto.getBuyPrice());
        return entity;
    }
}
