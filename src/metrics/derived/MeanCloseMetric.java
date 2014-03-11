package metrics.derived;

import metrics.CloseMetric;
import stock.NQuotes;

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
