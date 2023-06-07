package com.example.grocery.api.tranzaction;
import com.example.grocery.utils.Response;
import org.bson.types.ObjectId;
import com.example.grocery.api.productGroceries.ProductGroceries;
import com.example.grocery.api.productGroceries.ProductGroceriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.grocery.utils.Messages.*;

@Service
public class TranzactionService {
    @Autowired
    private TranzactionRepository tranzactionRepository;


    @Autowired
    private ProductGroceriesRepository productGroceriesRepository;

    
    Response<Tranzaction> create(TranzactionDTO tranzactionDTO) {
        Response<Tranzaction> response = new Response<>();
        tranzactionRepository.save(TranzactionMapper.dtoToEntity(tranzactionDTO));

        response.setStatus(HttpStatus.CREATED);
        response.setMessage(createSuccessful("Tranzaction"));
        return response;
    }

    Response<List<Tranzaction>> getAll() {
        Response<List<Tranzaction>> response = new Response<>();
        List<Tranzaction> tranzactions = tranzactionRepository.findAll();

        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("Tranzaction"));
        response.setData(tranzactions);
        return response;
    }

    Response<Tranzaction> getById(String id) {
        Response<Tranzaction> response = new Response<>();
        Tranzaction tranzaction = tranzactionRepository.findById(id).orElse(null);

        if (tranzaction == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("tranzaction"));
            return response;
        }


        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("Tranzaction"));
        response.setData(tranzaction);
        return response;
    }

    Response<Tranzaction> update(String id, TranzactionDTO tranzactionDTO, Boolean override){
        Response<Tranzaction> response = new Response<>();

        Tranzaction oldTranzaction = tranzactionRepository.findById(id).orElse(null);

        if (oldTranzaction == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("tranzaction"));
            return response;
        }

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

        Tranzaction tranzaction = tranzactionRepository.findById(id).orElse(null);
        response.setStatus(HttpStatus.OK);
        response.setMessage(updateSuccessful("Tranzaction"));
        response.setData(tranzaction);

        return response;
    }

    Response<List<Tranzaction>> getByDateAndUser(String userid, Date startDate, Date endDate) {
        Response<List<Tranzaction>> response = new Response<>();

        List<Tranzaction> tranzactions = tranzactionRepository.findByDateAndUser(userid, startDate, endDate);

        if (tranzactions == null || tranzactions.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage(nonExistingResource("tranzaction"));
            return response;
        }

        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("Tranzaction"));
        response.setData(tranzactions);
        return response;
    }

    Response<List<Tranzaction>> getByDateAndGrocery(String groceryId, Date startDate, Date endDate) {
        Response<List<Tranzaction>> response = new Response<>();

        var productGroceriesList = productGroceriesRepository.findByGroceryId(groceryId).stream()
                .map(ProductGroceries::getId)
                .toList();

        var hashSet = new HashSet<Tranzaction>();
        for(var pg: productGroceriesList){
            System.out.println(pg);
            hashSet.addAll(tranzactionRepository.findByDateAndGrocery(pg, startDate, endDate));
        }

        response.setStatus(HttpStatus.OK);
        response.setMessage(fetchSuccessful("Tranzactions"));
        response.setData(hashSet.stream().toList());

        return response;
    }

    Response<Tranzaction> delete(String id){
        Response<Tranzaction> response = new Response<>();
        Tranzaction tranzaction = tranzactionRepository.findById(id).orElse(null);

        if (tranzaction == null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage(nonExistingResource("tranzaction"));
            return response;
        }

        tranzactionRepository.delete(tranzaction);

        tranzaction = tranzactionRepository.findById(id).orElse(null);
        response.setStatus(tranzaction != null ? HttpStatus.INTERNAL_SERVER_ERROR :HttpStatus.OK);
        response.setMessage(tranzaction != null ? somethingWentWrong : deleteSuccessful("Tranzaction"));
        return response;
    }
}
