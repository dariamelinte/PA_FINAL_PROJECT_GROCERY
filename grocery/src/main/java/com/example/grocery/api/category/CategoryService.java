package com.example.grocery.api.category;

import com.example.grocery.api.user.User;
import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    Response<Category> create(CategoryDTO dto) {
        Response<Category> response = new Response<>();
        categoryRepository.save(CategoryMapper.dtoToEntity(dto));

        Category category = categoryRepository.findByName(dto.getName()).orElse(null);

        if (category == null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Something went wrong, please try again!");
            return response;
        }

        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Category created successfully.");
        response.setData(category);
        return response;
    }

    Response<List<Category>> getAll() {
        Response<List<Category>> response = new Response<>();
        List<Category> categories = categoryRepository.findAll();

        response.setStatus(HttpStatus.OK);
        response.setMessage("Categories fetched.");
        response.setData(categories);
        return response;
    }

    Response<Category> getById(String id) {
        Response<Category> response = new Response<>();
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage("The respective category does not exist.");
            return response;
        }


        response.setStatus(HttpStatus.OK);
        response.setMessage("Category fetched successfully.");
        response.setData(category);
        return response;
    }

    Response<Category> update(String id, CategoryDTO dto){
        Response<Category> response = new Response<>();

        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage("The respective category does not exist.");
            return response;
        }

        if(dto.getName() != null) category.setName(dto.getName());

        categoryRepository.save(category);

        category = categoryRepository.findById(id).orElse(null);
        response.setStatus(HttpStatus.OK);
        response.setMessage("Category updated successfully.");
        response.setData(category);

        return response;
    }

    Response<Category> delete(String id){
        Response<Category> response = new Response<>();
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage("The respective category does not exist.");
            return response;
        }

        categoryRepository.delete(category);

        category = categoryRepository.findById(id).orElse(null);
        response.setStatus(category != null ? HttpStatus.INTERNAL_SERVER_ERROR :HttpStatus.OK);
        response.setMessage(category != null ? "Something went wrong." : "Category deleted successfully.");
        return response;
    }
}
