package com.example.grocery.api.tranzaction;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TranzactionRepository extends MongoRepository<Tranzaction, String> {
    @Query("{ 'productGroceryId' : ObjectId('?0')}")
    Optional <List<Tranzaction>> findByProductGroceryId(String productGroceryId);


    @Query("{ userId: ObjectId('?0'),  createdAt: {$gte: ?1, $lte: ?2 }}")

    List<Tranzaction> findByDateAndUser(String userid, Date startDate, Date endDate);

    @Query("{ productGroceryId: ObjectId('?0'),  createdAt: {$gte: ?1, $lte: ?2 }}")
    List<Tranzaction> findByDateAndGrocery(String productGroceries, Date startDate, Date endDate);
}
