package metrics;

import org.joda.time.LocalDate;

/**
 * Created by erik on 05/03/14.
 */
public class CloseMetric extends StockMetric {

    public CloseMetric(LocalDate date, double value) {
        super("close", date, value);
    }

}
