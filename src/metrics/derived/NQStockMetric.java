package metrics.derived;

import metrics.StockMetric;
import stock.NQuotes;

/**
 * Created by erik on 09/03/14.
 */
public abstract class NQStockMetric extends StockMetric {

    protected NQStockMetric(String name, double value) {
        super(name, value);
    }

}
