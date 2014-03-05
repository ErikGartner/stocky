package metrics;

import org.joda.time.LocalDate;

/**
 * Created by erik on 05/03/14.
 */
public class VolumeMetric extends StockMetric {

    public VolumeMetric(LocalDate date, double value) {
        super("volume", date, value);
    }

}
