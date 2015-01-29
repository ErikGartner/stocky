package com.smoiz.stocky.metrics;

/**
 * Created by erik on 05/03/14.
 */
public class HighMetric extends StockMetric {

    public static final String NAME = "high";

    public HighMetric(double value) {
        super(NAME, value);
    }

}
