package metrics.derived;

import metrics.CloseMetric;
import metrics.HighMetric;
import metrics.LowMetric;
import metrics.StockMetric;
import stock.NQuotes;
import stock.Quote;

import java.util.List;

/**
 * Created by erik on 09/03/14.
 */
public abstract class NQStockMetric extends StockMetric {

    protected NQStockMetric(String name, double value) {
        super(name, value);
    }

    protected static double average(String metricName, NQuotes nQuotes) {
        List<Quote> quotes = nQuotes.getQuotes();
        double total = 0.0;
        for (Quote q : quotes) {
            StockMetric sm = q.getMetric(metricName);
            if (sm == null)
                throw new RuntimeException("Missing required metric: " + metricName);
            total += sm.getValue();
        }
        return total / quotes.size();
    }

    protected static double standardDeviation(String metricName, NQuotes nQuotes) {
        double avg = average(metricName, nQuotes);
        int n = nQuotes.getN();
        double totalDev = 0.0;
        for (Quote q : nQuotes.getQuotes()) {
            double close = q.getMetric(metricName).getValue();
            totalDev += Math.pow(close - avg, 2);
        }
        return Math.sqrt(totalDev / n);
    }

    protected static double trueRange(Quote q, Quote prev) {
        double high = q.getMetric(HighMetric.NAME).getValue();
        double low = q.getMetric(LowMetric.NAME).getValue();
        double closePrev = prev.getMetric(CloseMetric.NAME).getValue();
        return Math.max(high - low, Math.max(Math.abs(high - closePrev), Math.abs(low - closePrev)));
    }

}
