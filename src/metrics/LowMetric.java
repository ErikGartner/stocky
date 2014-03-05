package metrics;

import org.joda.time.LocalDate;

/**
 * Created by erik on 05/03/14.
 */
public class LowMetric extends StockMetric {

    public LowMetric(LocalDate date, double value) {
        super("low", date, value);
    }

}
