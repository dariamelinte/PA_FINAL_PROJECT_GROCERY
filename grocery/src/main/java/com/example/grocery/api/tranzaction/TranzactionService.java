package com.example.grocery.api.tranzaction;
import org.bson.types.ObjectId;
import com.example.grocery.api.productGroceries.ProductGroceries;
import com.example.grocery.api.productGroceries.ProductGroceriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranzactionService {
    @Autowired
    private TranzactionRepository tranzactionRepository;


    @Autowired
    private ProductGroceriesRepository productGroceriesRepository;

    void create(TranzactionDTO tranzactionDTO) {
        tranzactionRepository.save(TranzactionMapper.dtoToEntity(tranzactionDTO));
    }

    List<Tranzaction> getAll() {
        return tranzactionRepository.findAll();
    }
    List<Tranzaction> getByDateAndUser(String userid, Date startDate, Date endDate) {
        return tranzactionRepository.findByDateAndUser(userid, startDate, endDate);
    }

    List<Tranzaction> getByDateAndGrocery(String groceryId, Date startDate, Date endDate) {
        var tranzactions = new ArrayList<ObjectId>();

        var productGroceriesList = productGroceriesRepository.findByGroceryId(groceryId).stream()
                .map(ProductGroceries::getId)
                .collect(Collectors.toList());

        var hashSet = new HashSet<Tranzaction>();
        for(var pg: productGroceriesList){
            System.out.println(pg);
            hashSet.addAll(tranzactionRepository.findByDateAndGrocery(pg, startDate, endDate));
        }

        return hashSet.stream().toList();
    }

    Tranzaction getById(String id) {
        return tranzactionRepository.findById(id).orElse(null);
    }

    void update(String id, TranzactionDTO tranzactionDTO, Boolean override){
        Tranzaction oldTranzaction = this.getById(id);
        if (oldTranzaction == null) return;

        if (Boolean.TRUE.equals(override) || tranzactionDTO.getUserId() != null) {
            oldTranzaction.setUserId(tranzactionDTO.getUserId());
        }
        if (Boolean.TRUE.equals(override) || tranzactionDTO.getProductGroceryId() != null) {
            oldTranzaction.setProductGroceryId(tranzactionDTO.getProductGroceryId());
        }
        if (Boolean.TRUE.equals(override) || tranzactionDTO.getTranzactionType() != null) {
            oldTranzaction.setTranzactionType(tranzactionDTO.getTranzactionType());
        }
        if (Boolean.TRUE.equals(override) || tranzactionDTO.getQuantity() != null) {
            oldTranzaction.setQuantity(tranzactionDTO.getQuantity());
        }
        if (Boolean.TRUE.equals(override) || tranzactionDTO.getCreatedAt() != null) {
            oldTranzaction.setCreatedAt(tranzactionDTO.getCreatedAt());
        }

        tranzactionRepository.save(oldTranzaction);
    }

    void delete(String id){
        Tranzaction tranzaction = this.getById(id);
        tranzactionRepository.delete(tranzaction);
    }
}
