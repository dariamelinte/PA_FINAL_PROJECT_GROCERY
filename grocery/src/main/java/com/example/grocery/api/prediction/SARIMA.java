package com.example.grocery.api.prediction;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.Arrays;

public class SARIMA {
    private int p; // AR order
    private int d; // differencing order
    private int q; // MA order
    private int s; // seasonal period (number of months)

    public SARIMA(int p, int d, int q, int s) {
        this.p = p;
        this.d = d;
        this.q = q;
        this.s = s;
    }

    public double[] fit(double[] data, int forecastPeriod) {
        int n = data.length;
        int totalPeriods = n + forecastPeriod;
        double[] forecast = new double[totalPeriods];

        double[] seasonalComponent = fitSeasonalComponent(data);
        double[] residual = fitNonSeasonalComponent(data, seasonalComponent);

        for (int i = 0; i < n; i++) {
            forecast[i] = seasonalComponent[i % s] + residual[i];
        }

        for (int i = n; i < totalPeriods; i++) {
            int seasonIndex = i % s;
            int prevSeasonIndex = (i - s) % s;
            forecast[i] = seasonalComponent[seasonIndex] + residual[n - s + seasonIndex]
                    + (forecast[i - s] - seasonalComponent[prevSeasonIndex]);
        }

        return Arrays.copyOfRange(forecast, n, totalPeriods);
    }

    private double[] fitSeasonalComponent(double[] data) {
        int n = data.length;
        double[] seasonalComponent = new double[n];

        for (int i = 0; i < n; i++) {
            seasonalComponent[i] = data[i % s];
        }

        return seasonalComponent;
    }

    private double[] fitNonSeasonalComponent(double[] data, double[] seasonalComponent) {
        int n = data.length;
        double[] residual = new double[n];

        for (int i = 0; i < n; i++) {
            residual[i] = data[i] - seasonalComponent[i % s];
        }

        return residual;
    }

    public static void main(String[] args) {
        // Example usage
        double[] data = {3124.17, 2461.93, 3675.28, 4179.19, 1662.06, 3922.11, 2044.75, 4596.31, 2866.52, 3733.18, 2514.46, 1485.88, 4293.75, 1301.49, 3846.62, 2041.85, 3467.91, 4676.06, 2798.42, 4071.27, 2285.18, 4910.68, 3492.09, 2967.81};
        SARIMA sarima = new SARIMA(1, 1, 1, 12);
        int forecastPeriod = 6;
        double[] forecast = sarima.fit(data, forecastPeriod);

        System.out.println("Original profit data:");
        for (double value : data) {
            System.out.print(value + " ");
        }

        System.out.println("\nForecasted profit data:");
        for (double value : forecast) {
            System.out.print(value + " ");
        }
    }
}
