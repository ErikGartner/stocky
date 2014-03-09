package metrics.derived;

import metrics.CloseMetric;
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
}
