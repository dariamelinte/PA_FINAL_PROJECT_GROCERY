package com.example.grocery.api.grocery;

import com.example.grocery.api.user.User;
import com.example.grocery.api.user.UserDTO;
import com.example.grocery.api.user.UserMapper;
import com.example.grocery.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryService {
    @Autowired
    private GroceryRepository groceryRepository;

    void create(GroceryDTO groceryDTO) {
        groceryRepository.save(GroceryMapper.dtoToEntity(groceryDTO));
    }

    List<Grocery> getAll() {
        return groceryRepository.findAll();
    }

    Grocery getById(String id) {
        return groceryRepository.findById(id).orElse(null);
    }

    void update(String id, GroceryDTO groceryDTO, Boolean override){
        Grocery oldGrocery = this.getById(id);
        if (oldGrocery == null) return;

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
    }

    void delete(String id){
        Grocery grocery = this.getById(id);
        groceryRepository.delete(grocery);
    }
}
