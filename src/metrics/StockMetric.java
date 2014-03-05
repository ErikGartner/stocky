package metrics;

/**
 * Created by erik on 05/03/14.
 */
public abstract class StockMetric {

    protected String name;
    protected double value;

    protected StockMetric(String name, double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s: %f", name, value);
    }

}
