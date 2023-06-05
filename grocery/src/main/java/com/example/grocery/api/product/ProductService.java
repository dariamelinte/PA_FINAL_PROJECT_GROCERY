package com.example.grocery.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private  ProductRepository productRepository;

    void create(ProductDTO dto) { productRepository.save(ProductMapper.dtoToEntity(dto));}

    List<Product> getAll(){ return productRepository.findAll(); }

    Product getById(String id) { return productRepository.findById(id).orElse(null);}

    void update(String id, ProductDTO dto, Boolean override){
        var prod = this.getById(id);
        if(prod == null) return;

        if(Boolean.TRUE.equals(override) || dto.getDescription() != null) prod.setDescription(dto.getDescription());
        if(Boolean.TRUE.equals(override) || dto.getName() != null) prod.setName(dto.getName());
        if(Boolean.TRUE.equals(override) || dto.getCategories() != null) prod.setCategories(dto.getCategories());

        productRepository.save(prod);
    }

    void delete(String id){
        var prod = this.getById(id);
        productRepository.delete(prod);
    }
}
