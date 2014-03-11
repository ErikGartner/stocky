package metrics.derived;

import metrics.CloseMetric;
import metrics.HighMetric;
import metrics.OpenMetric;
import stock.NQuotes;

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
        double high = nQuotes.getQuotes().get(0).getMetric(HighMetric.NAME).getValue();
        double change = (high - avg_close) / avg_close;
        return new HighToMeanMetric(change);
    }
}
