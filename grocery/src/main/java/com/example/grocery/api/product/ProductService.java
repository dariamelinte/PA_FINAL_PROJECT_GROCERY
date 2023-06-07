package com.example.grocery.api.product;

import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.grocery.utils.Messages.*;

@Service
public class ProductService {
    @Autowired
    private  ProductRepository productRepository;

    Response<Product> create(ProductDTO productDTO) {
        Response<Product> response = new Response<>();
        productRepository.save(ProductMapper.dtoToEntity(productDTO));

        response.setStatus(HttpStatus.CREATED);
        response.setMessage(createSuccessful("Product"));
        return response;
    }

    Response<List<Product>> getAll() {
        Response<List<Product>> response = new Response<>();
        List<Product> categories = productRepository.findAll();

        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("Product"));
        response.setData(categories);
        return response;
    }

    Response<Product> getById(String id) {
        Response<Product> response = new Response<>();
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("product"));
            return response;
        }


        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("Product"));
        response.setData(product);
        return response;
    }

    Response<Product> update(String id, ProductDTO productDTO, Boolean override){
        Response<Product> response = new Response<>();

        Product oldProduct = productRepository.findById(id).orElse(null);

        if (oldProduct == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("product"));
            return response;
        }

        if(Boolean.TRUE.equals(override) || productDTO.getDescription() != null) oldProduct.setDescription(productDTO.getDescription());
        if(Boolean.TRUE.equals(override) || productDTO.getName() != null) oldProduct.setName(productDTO.getName());
        if(Boolean.TRUE.equals(override) || productDTO.getCategories() != null) oldProduct.setCategories(productDTO.getCategories());


        productRepository.save(oldProduct);

        Product product = productRepository.findById(id).orElse(null);
        response.setStatus(HttpStatus.OK);
        response.setMessage(updateSuccessful("Product"));
        response.setData(product);

        return response;
    }

    Response<Product> delete(String id){
        Response<Product> response = new Response<>();
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("product"));
            return response;
        }

        productRepository.delete(product);

        product = productRepository.findById(id).orElse(null);
        response.setStatus(product != null ? HttpStatus.INTERNAL_SERVER_ERROR :HttpStatus.OK);
        response.setMessage(product != null ? somethingWentWrong : deleteSuccessful("Product"));
        return response;
    }
}
