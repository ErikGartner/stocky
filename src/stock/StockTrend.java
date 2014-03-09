package stock;

import metrics.derived.AverageCloseMetric;
import metrics.CloseMetric;
import metrics.OpenMetric;

/**
 * Created by erik on 05/03/14.
 */
public enum StockTrend {

    BULL,
    BEAR;

    public static StockTrend trendFromOpenClose(Quote quote) {
        double before = quote.getMetric(OpenMetric.NAME).getValue();
        double after = quote.getMetric(CloseMetric.NAME).getValue();
        return after > before ? BULL : BEAR;
    }

    public static StockTrend trendFromCloseClose(Quote before, Quote after) {
        return before.getMetric(CloseMetric.NAME).getValue() < after.getMetric(CloseMetric.NAME).getValue() ? BULL : BEAR;
    }

    public static StockTrend trendFromNQuotesAverage(NQuotes before, NQuotes after){
        return before.getMetric(AverageCloseMetric.NAME).getValue() < after.getMetric(AverageCloseMetric.NAME).getValue() ? BULL : BEAR;
    }

}
