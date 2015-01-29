package com.smoiz.stocky.metrics.derived;

import com.smoiz.stocky.metrics.CloseMetric;
import com.smoiz.stocky.metrics.HighMetric;
import com.smoiz.stocky.stock.NQuotes;
import com.smoiz.stocky.stock.Quote;

/**
 * Created by erik on 10/03/14.
 */
public class HighToMeanMetric extends NQStockMetric {

    public static final String NAME = "high_to_mean";

    private HighToMeanMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        double avg_close = average(CloseMetric.NAME, nQuotes);
        double high = 0.0;
        for (Quote q : nQuotes.getQuotes()) {
            high = Math.max(high, q.getMetric(HighMetric.NAME).getValue());
        }
        double change = (high - avg_close) / avg_close;
        return new HighToMeanMetric(change);
    }
}
