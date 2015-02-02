package com.smoiz.stocky.metrics.derived;

import com.smoiz.stocky.metrics.CloseMetric;
import com.smoiz.stocky.metrics.LowMetric;
import com.smoiz.stocky.stock.NQuotes;

/**
 * Created by erik on 09/03/14.
 */
public class MeanLowMetric extends NQStockMetric {

    public static final String NAME = "mean_low";

    private MeanLowMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        return new MeanLowMetric(average(LowMetric.NAME, nQuotes));
    }

}
