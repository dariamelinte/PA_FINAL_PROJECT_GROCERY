package com.example.grocery.api.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private List<String> categories;
}
