package metrics.derived;

import metrics.VolumeMetric;
import stock.NQuotes;


/**
 * Created by erik on 09/03/14.
 */
public class AverageVolumeMetric extends NQStockMetric {

    public static final String NAME = "avg_volume";

    private AverageVolumeMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        return new AverageVolumeMetric(average(VolumeMetric.NAME, nQuotes));
    }

}
