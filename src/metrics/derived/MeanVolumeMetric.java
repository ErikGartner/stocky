package metrics.derived;

import metrics.VolumeMetric;
import stock.NQuotes;


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
