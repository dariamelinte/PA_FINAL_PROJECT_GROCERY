package com.example.grocery.api.grocery;

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
