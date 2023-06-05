package com.example.grocery.api.productGroceries;

import com.example.grocery.enums.SellType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@Document(collection = "product_groceries")
public class ProductGroceries {
    private String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String productId;
    @Field(targetType = FieldType.OBJECT_ID)
    private String groceryId;
    private SellType sellType;

    private Integer sellPrice;
    private Integer buyPrice;
}
