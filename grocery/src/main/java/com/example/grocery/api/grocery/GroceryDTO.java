package com.example.grocery.api.grocery;

import lombok.Data;

@Data
public class GroceryDTO {
    private String userId;
    private String name;
    private Double latitude;
    private Double longitude;
}
