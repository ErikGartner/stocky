package metrics;

import org.joda.time.LocalDate;

/**
 * Created by erik on 05/03/14.
 */
public class HighMetric extends StockMetric {

    public HighMetric(LocalDate date, double value) {
        super("high", date, value);
    }

}
