package com.example.grocery.api.productGroceries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductGroceriesService {
    @Autowired
    private ProductGroceriesRepository repository;

    void create(ProductGroceriesDTO dto) {repository.save(ProductGroceriesMapper.dtoToEntity(dto));}

    List<ProductGroceries> getAll() { return  repository.findAll(); }

    public List<ProductGroceries> getByGroceryId(String groceryId) { return repository.findByGroceryId(groceryId);}

    public ProductGroceries getById(String id) { return  repository.findById(id).orElse(null);}

    void update(String id, ProductGroceriesDTO dto, Boolean override){
        var entity = this.getById(id);
        if( entity == null) return;

        if(Boolean.TRUE.equals(override) || dto.getProductId() != null) entity.setProductId(dto.getProductId());
        if(Boolean.TRUE.equals(override) || dto.getGroceryId() != null) entity.setGroceryId(dto.getGroceryId());
        if(Boolean.TRUE.equals(override) || dto.getSellType() != null) entity.setSellType(dto.getSellType());
        if(Boolean.TRUE.equals(override) || dto.getBuyPrice() != null) entity.setBuyPrice(dto.getBuyPrice());

        repository.save(entity);
    }

    void delete(String id){
        var entity = this.getById(id);
        repository.delete(entity);
    }
}
