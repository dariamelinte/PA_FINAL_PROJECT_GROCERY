package com.example.grocery.api.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    void create(CategoryDTO dto) { categoryRepository.save(CategoryMapper.dtoToEntity(dto));}

    List<Category> getAll() { return categoryRepository.findAll();}

    Category getById(String id) { return categoryRepository.findById(id).orElse(null);}

    void update(String id, CategoryDTO dto){
        var cat = this.getById(id);
        if(cat == null) return;

        if(dto.getName() != null) cat.setName(dto.getName());

        categoryRepository.save(cat);
    }

    void delete(String id){
        var cat = this.getById(id);
        categoryRepository.delete(cat);
    }
}
