package metrics.derived;

import metrics.CloseMetric;
import metrics.StockMetric;
import stock.NQuotes;
import stock.Quote;

import java.util.List;

/**
 * Created by erik on 09/03/14.
 */
public class AverageCloseMetric extends NQStockMetric{

    public static final String NAME = "average_close";

    private AverageCloseMetric(double value){
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {
        List<Quote> quotes = nQuotes.getQuotes();
        double total = 0.0;
        for(Quote q : quotes){
            StockMetric sm = q.getMetric(CloseMetric.NAME);
            if(sm == null)
                throw new RuntimeException("Missing required metric: " + CloseMetric.NAME);
            total += sm.getValue();
        }
        return new AverageCloseMetric(total / quotes.size());
    }


}
