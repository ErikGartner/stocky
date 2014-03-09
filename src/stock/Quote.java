package stock;

import metrics.StockMetric;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by erik on 04/03/14.
 */
public class Quote implements Comparable<Quote> {

    private LocalDate date;
    private Map<String, StockMetric> metrics;

    public Quote(LocalDate d) {
        date = d;
        metrics = new TreeMap<String, StockMetric>();
    }

    public void addMetric(StockMetric sm) {
        metrics.put(sm.getName(), sm);
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
