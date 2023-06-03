package com.example.grocery.api.grocery;

import com.example.grocery.api.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepository extends MongoRepository<Grocery, String> {
}
