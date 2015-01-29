package com.smoiz.stocky.metrics.derived;

import com.smoiz.stocky.metrics.CloseMetric;
import com.smoiz.stocky.stock.NQuotes;

/**
 * Calculates the volatility of the stock (std. dev).
 */
public class VolatilityMetric extends NQStockMetric {

    public static final String NAME = "volatility";

    private VolatilityMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        return new VolatilityMetric(standardDeviation(CloseMetric.NAME, nQuotes) / average(CloseMetric.NAME, nQuotes));
    }

}
