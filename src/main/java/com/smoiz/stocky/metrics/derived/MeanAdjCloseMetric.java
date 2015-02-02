package com.smoiz.stocky.metrics.derived;

import com.smoiz.stocky.metrics.AdjustedCloseMetric;
import com.smoiz.stocky.metrics.CloseMetric;
import com.smoiz.stocky.stock.NQuotes;

/**
 * Created by erik on 09/03/14.
 */
public class MeanAdjCloseMetric extends NQStockMetric {

    public static final String NAME = "mean_adj_close";

    private MeanAdjCloseMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        return new MeanAdjCloseMetric(average(AdjustedCloseMetric.NAME, nQuotes));
    }

}
