package com.example.grocery.api.tranzaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranzactionService {
    @Autowired
    private TranzactionRepository tranzactionRepository;

    void create(TranzactionDTO tranzactionDTO) {
        tranzactionRepository.save(TranzactionMapper.dtoToEntity(tranzactionDTO));
    }

    List<Tranzaction> getAll() {
        return tranzactionRepository.findAll();
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
