package metrics.derived;

import metrics.CloseMetric;
import metrics.HighMetric;
import stock.NQuotes;

/**
 * Average directional movement index
 * Created by erik on 11/03/14.
 */
public class ADXMetric extends NQStockMetric {

    public static final String NAME = "adx";

    private ADXMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        return new ADXMetric(0.0);
    }

}
