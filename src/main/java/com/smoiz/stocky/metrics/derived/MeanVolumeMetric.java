package com.smoiz.stocky.metrics.derived;

import com.smoiz.stocky.metrics.VolumeMetric;
import com.smoiz.stocky.stock.NQuotes;


/**
 * Created by erik on 09/03/14.
 */
public class MeanVolumeMetric extends NQStockMetric {

    public static final String NAME = "mean_volume";

    private MeanVolumeMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        return new MeanVolumeMetric(average(VolumeMetric.NAME, nQuotes));
    }

}
