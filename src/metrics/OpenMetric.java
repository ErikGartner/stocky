package metrics;

import org.joda.time.LocalDate;

/**
 * Created by erik on 05/03/14.
 */
public class OpenMetric extends StockMetric {

    public OpenMetric(LocalDate date, double value) {
        super("open", date, value);
    }

}
