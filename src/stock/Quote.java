package stock;

import metrics.StockMetric;
import org.joda.time.LocalDate;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by erik on 04/03/14.
 */
public class Quote {

    private LocalDate date;
    private List<StockMetric> metrics;

    public Quote(LocalDate d) {
        date = d;
        metrics = new LinkedList<StockMetric>();
    }

    public void addMetric(StockMetric sm) {
        metrics.add(sm);
    }

    public String toString() {
        return String.format("<%s> %s", date, metrics);
    }

}
