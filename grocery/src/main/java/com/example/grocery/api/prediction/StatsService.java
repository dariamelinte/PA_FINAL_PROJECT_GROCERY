package com.example.grocery.api.prediction;

import com.example.grocery.api.productGroceries.ProductGroceriesService;
import com.example.grocery.api.tranzaction.Tranzaction;
import com.example.grocery.api.tranzaction.TranzactionRepository;
import com.example.grocery.enums.TranzactionType;
import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        int lastIndex = 0;
        int firstIndex = -1;
        for(var tranzaction : tranzactions){
            int index = 12 * ( getYear(tranzaction.getCreatedAt()) -  startYear) + getMonth(tranzaction.getCreatedAt());
            var product = productGroceriesService.getById(tranzaction.getProductGroceryId());
            System.out.println(getMonth(tranzaction.getCreatedAt()));
            System.out.println(tranzaction.getTranzactionType());
            System.out.println(tranzaction.getQuantity());
            System.out.println(product.getData().getBuyPrice());
            System.out.println(product.getData().getSellPrice());
            double profit = tranzaction.getTranzactionType() == TranzactionType.BUY
                    ? -1 * tranzaction.getQuantity() * product.getData().getBuyPrice()
                    : tranzaction.getQuantity() * product.getData().getSellPrice();

            System.out.println(profit);
            System.out.println();
            lastIndex = index;
            if(firstIndex == -1) firstIndex = index;
            values[index] += profit;
        }
        System.out.println(firstIndex);
        return Arrays.copyOfRange(values, firstIndex, lastIndex + 1);
    }

    private double[] getMonthArrayForGrocery(String groceryId){
        var productGroceries = productGroceriesService.getByGroceryId(groceryId);
        var tranzactions = new ArrayList<Tranzaction>();

        for( var productGrocery: productGroceries)
            tranzactions.addAll(tranzactionRepository.findByProductGroceryId(productGrocery.getId()).orElse(null));

        if (tranzactions == null) return new double[0];
        Collections.sort(tranzactions, Comparator.comparing(Tranzaction::getCreatedAt));
        int lastIndex = 0;
        int firstIndex = -1;
        int startYear = getYear(tranzactions.get(0).getCreatedAt());
        int stopYear = getYear(tranzactions.get(tranzactions.size() - 1).getCreatedAt());
        double [] values = new double[12*(stopYear - startYear + 1)];

        for(var tranzaction : tranzactions){
            int index = 12 * ( getYear(tranzaction.getCreatedAt()) -  startYear) + getMonth(tranzaction.getCreatedAt());
            var product = productGroceriesService.getById(tranzaction.getProductGroceryId());

            double profit = tranzaction.getTranzactionType() == TranzactionType.BUY
                    ? -1 * tranzaction.getQuantity() * product.getData().getBuyPrice()
                    : tranzaction.getQuantity() * product.getData().getSellPrice();
            lastIndex = index;
            if(firstIndex == -1) firstIndex = index;
            values[index] += profit;
        }
        System.out.println(firstIndex);
        return Arrays.copyOfRange(values, firstIndex, lastIndex + 1);
    }

    public Response<double[]> prediction(String productGroceryId, int months){
        var response = new Response<double[]>();
        var values = getMonthArray(productGroceryId);
        var algorithm = new SARIMA();
        double[] forecast = ProfitPrediction.predictProfit(values, months);
//        System.out.println(values);
        for (double number : values) {
            System.out.print(number + " ");
        }
        System.out.println();
        for (double number : forecast) {
            System.out.print(number + " ");
        }
        System.out.println();
        response.setData(forecast);
        response.setStatus(HttpStatus.OK);
        response.setMessage("The predicted values for the next " + months + " months have been computed.");
        return response;
    }

    public  Response<double[]> predictionForGrocery(String groceryId, int months){
        var response = new Response<double[]>();

        var values = getMonthArrayForGrocery(groceryId);
//        var algorithm = new SARIMA();
        double[] forecast = ProfitPrediction.predictProfit(values, months);

        response.setData(forecast);
        response.setStatus(HttpStatus.OK);
        response.setMessage("The predicted values for the next " + months + " months have been computed.");

        return response;
    }
}
