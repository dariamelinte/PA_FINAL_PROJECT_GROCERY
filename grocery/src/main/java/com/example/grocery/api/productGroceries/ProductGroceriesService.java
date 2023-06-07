package com.example.grocery.api.productGroceries;

import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductGroceriesService {
    @Autowired
    private ProductGroceriesRepository repository;

    Response<ProductGroceries> create(ProductGroceriesDTO dto) {
        var response = new Response<ProductGroceries>();
        var pg = repository.save(ProductGroceriesMapper.dtoToEntity(dto));
        if (pg == null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Something went wrong, please try again!");
            return response;
        }

        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Product grocery created successfully.");
        response.setData(pg);
        return response;
    }

    Response<List<ProductGroceries>> getAll() {
        var response = new Response<List<ProductGroceries>>();
        var all = repository.findAll();

        response.setStatus(HttpStatus.OK);
        response.setMessage("Products grocery fetched.");
        response.setData(all);
        return response;
    }

    public List<ProductGroceries> getByGroceryId(String groceryId) { return repository.findByGroceryId(groceryId);}

    public Response<ProductGroceries> getById(String id) {
        Response<ProductGroceries> response = new Response<ProductGroceries>();
        var entity =  repository.findById(id).orElse(null);

        if (entity == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage("The respective product grocery does not exist.");
            return response;
        }

        response.setStatus(HttpStatus.OK);
        response.setMessage("Product grocery fetched successfully.");
        response.setData(entity);
        return response;
    }

    public Response<ProductGroceries> update(String id, ProductGroceriesDTO dto, Boolean override){
        var response = new Response<ProductGroceries>();
        var entity = this.getById(id).getData();
        if( entity == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage("The respective category does not exist.");
            return response;
        }

        if(Boolean.TRUE.equals(override) || dto.getProductId() != null) entity.setProductId(dto.getProductId());
        if(Boolean.TRUE.equals(override) || dto.getGroceryId() != null) entity.setGroceryId(dto.getGroceryId());
        if(Boolean.TRUE.equals(override) || dto.getSellType() != null) entity.setSellType(dto.getSellType());
        if(Boolean.TRUE.equals(override) || dto.getBuyPrice() != null) entity.setBuyPrice(dto.getBuyPrice());
        if(Boolean.TRUE.equals(override) || dto.getSellPrice() != null) entity.setSellPrice(dto.getSellPrice());

        var pg = repository.save(entity);
        response.setStatus(HttpStatus.OK);
        response.setMessage("Category updated successfully.");
        response.setData(pg);
        return response;
    }

    public Response<ProductGroceries> delete(String id){
        var response = new Response<ProductGroceries>();
        var entity = this.getById(id).getData();

        if(entity == null){
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage("The respective category does not exist.");
            return response;
        }
        repository.delete(entity);

        entity = this.getById(id).getData();
        response.setStatus(entity != null ? HttpStatus.INTERNAL_SERVER_ERROR :HttpStatus.OK);
        response.setMessage(entity != null ? "Something went wrong." : "Category deleted successfully.");
        return response;
    }
}
