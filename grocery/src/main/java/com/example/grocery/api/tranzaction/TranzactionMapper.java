package com.example.grocery.api.tranzaction;

public class TranzactionMapper {
    public static Tranzaction dtoToEntity(TranzactionDTO tranzactionDTO) {
        Tranzaction tranzaction = new Tranzaction();
        tranzaction.setUserId(tranzactionDTO.getUserId());
        tranzaction.setProductGroceryId(tranzactionDTO.getProductGroceryId());
        tranzaction.setTranzactionType(tranzactionDTO.getTranzactionType());
        tranzaction.setQuantity(tranzactionDTO.getQuantity());
        tranzaction.setCreatedAt(tranzactionDTO.getCreatedAt());
        return tranzaction;
    }
}
