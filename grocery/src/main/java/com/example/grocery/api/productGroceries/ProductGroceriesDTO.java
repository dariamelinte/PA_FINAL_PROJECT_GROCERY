package com.example.grocery.api.productGroceries;

import com.example.grocery.enums.SellType;
import lombok.Data;

@Data
public class ProductGroceriesDTO {
    private String groceryId;
    private String productId;
    private SellType sellType;
}
