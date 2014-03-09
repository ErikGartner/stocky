package metrics.derived;

import metrics.AdjustedCloseMetric;
import metrics.CloseMetric;
import metrics.StockMetric;
import stock.NQuotes;
import stock.Quote;

import java.util.List;

/**
 * Created by erik on 09/03/14.
 */
public class AverageCloseMetric extends NQStockMetric{

    public static final String NAME = "avg_close";

    private AverageCloseMetric(double value){
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        return new AverageCloseMetric(average(AdjustedCloseMetric.NAME, nQuotes));
    }


}
