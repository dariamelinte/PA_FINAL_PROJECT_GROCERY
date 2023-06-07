package com.example.grocery.api.prediction;

import com.example.grocery.api.productGroceries.ProductGroceriesService;
import com.example.grocery.api.tranzaction.Tranzaction;
import com.example.grocery.api.tranzaction.TranzactionRepository;
import com.example.grocery.enums.TranzactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatsService {
    @Autowired
    TranzactionRepository tranzactionRepository;

    @Autowired
    ProductGroceriesService productGroceriesService;

    private static int  getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    private static int  getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    private double[] getMonthArray(String productGroceryId){
        var tranzactions  = tranzactionRepository.findByProductGroceryId(productGroceryId).orElse(null);
        if (tranzactions == null) return null;
        Collections.sort(tranzactions, Comparator.comparing(Tranzaction::getCreatedAt));

        int startYear = getYear(tranzactions.get(0).getCreatedAt());
        int stopYear = getYear(tranzactions.get(tranzactions.size() - 1).getCreatedAt());
        double [] values = new double[12*(stopYear - startYear + 1)];

        for(var tranzaction : tranzactions){
            int index = 12 * ( getYear(tranzaction.getCreatedAt()) -  startYear) + getMonth(tranzaction.getCreatedAt());
            var product = productGroceriesService.getById(tranzaction.getProductGroceryId());

            double profit = tranzaction.getTranzactionType() == TranzactionType.BUY
                    ? -1 * tranzaction.getQuantity() * product.getBuyPrice()
                    : tranzaction.getQuantity() * product.getSellPrice();

            values[index] += profit;
        }
        return values;
    }

    private double[] getMonthArrayForGrocery(String groceryId){
        var productGroceries = productGroceriesService.getByGroceryId(groceryId);
        var tranzactions = new ArrayList<Tranzaction>();

        for( var productGrocery: productGroceries)
            tranzactions.addAll(tranzactionRepository.findByProductGroceryId(productGrocery.getId()).orElse(null));

        if (tranzactions == null) return new double[0];
        Collections.sort(tranzactions, Comparator.comparing(Tranzaction::getCreatedAt));

        int startYear = getYear(tranzactions.get(0).getCreatedAt());
        int stopYear = getYear(tranzactions.get(tranzactions.size() - 1).getCreatedAt());
        double [] values = new double[12*(stopYear - startYear + 1)];

        for(var tranzaction : tranzactions){
            int index = 12 * ( getYear(tranzaction.getCreatedAt()) -  startYear) + getMonth(tranzaction.getCreatedAt());
            var product = productGroceriesService.getById(tranzaction.getProductGroceryId());

            double profit = tranzaction.getTranzactionType() == TranzactionType.BUY
                    ? -1 * tranzaction.getQuantity() * product.getBuyPrice()
                    : tranzaction.getQuantity() * product.getSellPrice();

            values[index] += profit;
        }
        return values;
    }

    public double[] prediction(String productGroceryId, int months){
        var values = getMonthArray(productGroceryId);
        var algorithm = new SARIMA();
        double[] forecast = algorithm.fit(values, months);
        return forecast;
    }

    public double[] predictionForGrocery(String groceryId, int months){
        var values = getMonthArrayForGrocery(groceryId);
        var algorithm = new SARIMA();
        double[] forecast = algorithm.fit(values, months);
        return forecast;
    }
}
