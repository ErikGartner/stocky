package metrics;

/**
 * Created by erik on 05/03/14.
 */
public class OpenMetric extends StockMetric {

    public static final String NAME = "open";

    public OpenMetric(double value) {
        super(NAME, value);
    }

}
