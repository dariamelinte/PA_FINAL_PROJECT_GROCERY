package com.example.grocery.api.grocery;

import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.grocery.utils.Messages.*;
import static com.example.grocery.utils.Messages.fetchSuccessful;

@Service
public class GroceryService {
    @Autowired
    private GroceryRepository groceryRepository;

    Response<Grocery> create(GroceryDTO groceryDTO) {
        Response<Grocery> response = new Response<>();
        groceryRepository.save(GroceryMapper.dtoToEntity(groceryDTO));

        response.setStatus(HttpStatus.CREATED);
        response.setMessage(createSuccessful("Grocery"));
        return response;
    }

    Response<List<Grocery>> getAll() {
        Response<List<Grocery>> response = new Response<>();
        List<Grocery> categories = groceryRepository.findAll();

        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("Grocery"));
        response.setData(categories);
        return response;
    }

    Response<Grocery> getById(String id) {
        Response<Grocery> response = new Response<>();
        Grocery grocery = groceryRepository.findById(id).orElse(null);

        if (grocery == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("grocery"));
            return response;
        }


        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("Grocery"));
        response.setData(grocery);
        return response;
    }

    Response<Grocery> update(String id, GroceryDTO groceryDTO, Boolean override){
        Response<Grocery> response = new Response<>();

        Grocery oldGrocery = groceryRepository.findById(id).orElse(null);

        if (oldGrocery == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("grocery"));
            return response;
        }

        if (Boolean.TRUE.equals(override) || groceryDTO.getUserId() != null) {
            oldGrocery.setUserId(groceryDTO.getUserId());
        }
        if (Boolean.TRUE.equals(override) || groceryDTO.getName() != null) {
            oldGrocery.setName(groceryDTO.getName());
        }
        if (Boolean.TRUE.equals(override) || groceryDTO.getLatitude() != null) {
            oldGrocery.setLatitude(groceryDTO.getLatitude());
        }
        if (Boolean.TRUE.equals(override) || groceryDTO.getLongitude() != null) {
            oldGrocery.setLongitude(groceryDTO.getLongitude());
        }

        groceryRepository.save(oldGrocery);

        Grocery grocery = groceryRepository.findById(id).orElse(null);
        response.setStatus(HttpStatus.OK);
        response.setMessage(updateSuccessful("Grocery"));
        response.setData(grocery);

        return response;
    }

    Response<Grocery> delete(String id){
        Response<Grocery> response = new Response<>();
        Grocery grocery = groceryRepository.findById(id).orElse(null);

        if (grocery == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("grocery"));
            return response;
        }

        groceryRepository.delete(grocery);

        grocery = groceryRepository.findById(id).orElse(null);
        response.setStatus(grocery != null ? HttpStatus.INTERNAL_SERVER_ERROR :HttpStatus.OK);
        response.setMessage(grocery != null ? somethingWentWrong : deleteSuccessful("Grocery"));
        return response;
    }
}
