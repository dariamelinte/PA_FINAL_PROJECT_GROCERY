package com.example.grocery.api.tranzaction;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranzactionRepository extends MongoRepository<Tranzaction, String> {
    @Query("{ 'productGroceryId' : ObjectId('?0')}")
    Optional <List<Tranzaction>> findByProductGroceryId(String productGroceryId);
}
