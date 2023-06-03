package com.example.grocery.api.grocery;

import com.example.grocery.api.user.User;
import com.example.grocery.api.user.UserDTO;
import com.example.grocery.utils.Hash;

public class GroceryMapper {
    public static Grocery dtoToEntity(GroceryDTO groceryDTO) {
        Grocery grocery = new Grocery();
        grocery.setUserId(groceryDTO.getUserId());
        grocery.setName(groceryDTO.getName());
        grocery.setLatitude(groceryDTO.getLatitude());
        grocery.setLongitude(groceryDTO.getLongitude());
        return grocery;
    }
}
