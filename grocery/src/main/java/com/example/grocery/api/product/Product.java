package com.example.grocery.api.product;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Data
@Document(collection = "products")
public class Product {
    private String id;
    private String name;
    private String description;
    private List<String> categories;
}
