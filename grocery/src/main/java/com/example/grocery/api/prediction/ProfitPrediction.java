package com.example.grocery.api.prediction;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class ProfitPrediction {
    public static double[] predictProfit(double[] historicalData, int forecastMonths) {
        if (historicalData.length < 2 || forecastMonths < 1) {
            throw new IllegalArgumentException("Invalid input data");
        }

        SimpleRegression regression = new SimpleRegression();

        int numHistoricalMonths = historicalData.length;
        for (int i = 0; i < numHistoricalMonths; i++) {
            regression.addData(i + 1, historicalData[i]);
        }

        double slope = regression.getSlope();
        double intercept = regression.getIntercept();

        double[] profitForecasts = new double[forecastMonths];
        for (int i = 0; i < forecastMonths; i++) {
            double nextMonth = numHistoricalMonths + i + 1;
            profitForecasts[i] = slope * nextMonth + intercept;
        }

        return profitForecasts;
    }
}
