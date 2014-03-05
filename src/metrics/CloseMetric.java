package metrics;

/**
 * Created by erik on 05/03/14.
 */
public class CloseMetric extends StockMetric {

    public static final String NAME = "close";

    public CloseMetric(double value) {
        super(NAME, value);
    }

}
