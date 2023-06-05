package com.example.grocery.api.tranzaction;

import com.example.grocery.enums.TranzactionType;
import lombok.Data;

import java.util.Date;

@Data
public class TranzactionDTO {
    private String userId;
    private String productGroceryId;
    private TranzactionType tranzactionType;
    private Float quantity;
    private Date createdAt;
}
