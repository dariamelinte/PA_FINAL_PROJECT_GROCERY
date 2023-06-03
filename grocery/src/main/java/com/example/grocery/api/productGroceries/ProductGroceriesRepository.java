package com.example.grocery.api.productGroceries;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGroceriesRepository extends MongoRepository<ProductGroceries, String> {
}
