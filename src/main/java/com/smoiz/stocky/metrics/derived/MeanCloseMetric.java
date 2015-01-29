package com.smoiz.stocky.metrics.derived;

import com.smoiz.stocky.metrics.CloseMetric;
import com.smoiz.stocky.stock.NQuotes;

/**
 * Created by erik on 09/03/14.
 */
public class MeanCloseMetric extends NQStockMetric {

    public static final String NAME = "mean_close";

    private MeanCloseMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        return new MeanCloseMetric(average(CloseMetric.NAME, nQuotes));
    }

}
