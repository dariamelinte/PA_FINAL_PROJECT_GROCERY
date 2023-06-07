package com.example.grocery.api.productGroceries;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductGroceriesRepository extends MongoRepository<ProductGroceries, String> {
    @Query("{groceryId : ObjectId('?0')}")
    List<ProductGroceries> findByGroceryId(String groceryId);
}