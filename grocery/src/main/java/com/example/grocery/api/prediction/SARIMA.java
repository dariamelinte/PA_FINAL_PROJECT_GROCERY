package com.example.grocery.api.prediction;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.Arrays;

public class SARIMA {
    private final int seasonalPeriod = 12;

    public SARIMA() {
    }

    public double[] fit(double[] data, int forecastPeriod) {
        int n = data.length;
        int totalPeriods = n + forecastPeriod;
        double[] forecast = new double[totalPeriods];

        double[] seasonalComponent = fitSeasonalComponent(data);
        double[] residual = fitNonSeasonalComponent(data, seasonalComponent);

        for (int i = 0; i < n; i++) {
            forecast[i] = 0.6 * seasonalComponent[i % seasonalPeriod] + 0.4 * residual[i];
        }

        for (int i = n; i < totalPeriods; i++) {
            int seasonIndex = i % seasonalPeriod;
            int prevSeasonIndex = (i - seasonalPeriod) % seasonalPeriod;
            forecast[i] = 0.6 * seasonalComponent[seasonIndex] + 0.4 * residual[n - seasonalPeriod + seasonIndex]
                    + (0.6 * forecast[i - seasonalPeriod] - 0.6 * seasonalComponent[prevSeasonIndex]);
        }

        return Arrays.copyOfRange(forecast, n, totalPeriods);
    }

    private double[] fitSeasonalComponent(double[] data) {
        int n = data.length;
        double[] seasonalComponent = new double[n];

        for (int i = 0; i < n; i++) {
            seasonalComponent[i] = data[i % seasonalPeriod];
        }

        return seasonalComponent;
    }

    private double[] fitNonSeasonalComponent(double[] data, double[] seasonalComponent) {
        int n = data.length;
        double[] residual = new double[n];

        for (int i = 0; i < n; i++) {
            residual[i] = data[i] - seasonalComponent[i % seasonalPeriod];
        }

        return residual;
    }
}
