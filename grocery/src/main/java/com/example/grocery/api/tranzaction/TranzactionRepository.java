package com.example.grocery.api.tranzaction;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranzactionRepository extends MongoRepository<Tranzaction, String> {
}
