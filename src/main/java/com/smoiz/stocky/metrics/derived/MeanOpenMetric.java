package com.smoiz.stocky.metrics.derived;

import com.smoiz.stocky.metrics.CloseMetric;
import com.smoiz.stocky.metrics.OpenMetric;
import com.smoiz.stocky.stock.NQuotes;

/**
 * Created by erik on 09/03/14.
 */
public class MeanOpenMetric extends NQStockMetric {

    public static final String NAME = "mean_open";

    private MeanOpenMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        return new MeanOpenMetric(average(OpenMetric.NAME, nQuotes));
    }

}
