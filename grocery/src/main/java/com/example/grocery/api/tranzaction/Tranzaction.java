package com.example.grocery.api.tranzaction;

import com.example.grocery.enums.TranzactionType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Data
@Document(collection = "tranzactions")
public class Tranzaction {
    private String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;
    @Field(targetType = FieldType.OBJECT_ID)
    private String productGroceryId;
    private TranzactionType tranzactionType;
    private Float quantity;
    private Date createdAt;
}
