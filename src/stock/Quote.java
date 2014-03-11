package stock;

import metrics.StockMetric;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Immutable class containing a day and it's associated values.
 * Created by erik on 04/03/14.
 */
public class Quote implements Comparable<Quote> {

    private LocalDate date;
    private Map<String, StockMetric> metrics;

    public Quote(LocalDate d, List<StockMetric> metricList) {
        date = d;
        metrics = new HashMap<String, StockMetric>();
        for (StockMetric sm : metricList) {
            metrics.put(sm.getName(), sm);
        }
    }

    public List<StockMetric> getMetrics() {
        return new ArrayList<StockMetric>(metrics.values());
    }

    public StockMetric getMetric(String name) {
        return metrics.get(name);
    }

    public String toString() {
        return String.format("<%s> %s", date, metrics);
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(Quote quote) {
        return date.compareTo(quote.date);
    }

}
