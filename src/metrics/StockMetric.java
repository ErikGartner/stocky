package metrics;

import org.joda.time.LocalDate;

/**
 * Created by erik on 05/03/14.
 */
public abstract class StockMetric {

    protected String name;
    protected LocalDate date;
    protected double value;

    protected StockMetric(String name, LocalDate date, double value) {
        this.name = name;
        this.date = date;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s: %f", name, value);
    }

}
