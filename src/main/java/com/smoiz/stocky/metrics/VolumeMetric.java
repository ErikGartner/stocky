package com.smoiz.stocky.metrics;

/**
 * Created by erik on 05/03/14.
 */
public class VolumeMetric extends StockMetric {

    public static final String NAME = "volume";

    public VolumeMetric(double value) {
        super(NAME, value);
    }

}
