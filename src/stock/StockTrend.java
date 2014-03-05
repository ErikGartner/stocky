package stock;

import metrics.CloseMetric;
import metrics.OpenMetric;

/**
 * Created by erik on 05/03/14.
 */
public enum StockTrend {

    BULL,
    BEAR;

    public static StockTrend stockTrend(Quote quote) {

        double before = quote.getMetric(OpenMetric.NAME).getValue();
        double after = quote.getMetric(CloseMetric.NAME).getValue();
        return after > before ? BULL : BEAR;

    }

}
