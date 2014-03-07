package metrics;

/**
 * Created by erik on 07/03/14.
 */
public class AdjustedCloseMetric extends StockMetric {

    public static final String NAME = "adjusted close";

    public AdjustedCloseMetric(double value) {
        super(NAME, value);
    }
}
