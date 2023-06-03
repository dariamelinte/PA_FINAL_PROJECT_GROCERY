package com.example.grocery.api.productGroceries;

import com.example.grocery.enums.SellType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "product_groceries")
public class ProductGroceries {
    private String id;
    private String groceryId;
    private String productId;
    private SellType sellType;
}
