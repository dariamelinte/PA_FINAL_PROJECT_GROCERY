package com.example.grocery.api.grocery;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Data
@Document(collection = "groceries")
public class Grocery {
    private String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;
    private String name;
    private Double latitude;
    private Double longitude;
}
