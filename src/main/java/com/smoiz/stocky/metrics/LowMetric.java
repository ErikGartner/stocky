package com.smoiz.stocky.metrics;

/**
 * Created by erik on 05/03/14.
 */
public class LowMetric extends StockMetric {

    public static final String NAME = "low";

    public LowMetric(double value) {
        super(NAME, value);
    }

}
