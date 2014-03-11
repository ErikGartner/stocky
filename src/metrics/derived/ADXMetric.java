package metrics.derived;

import metrics.CloseMetric;
import metrics.HighMetric;
import metrics.LowMetric;
import stock.NQuotes;
import stock.Quote;

import java.util.List;

/**
 * Average directional movement index
 * Created by erik on 11/03/14.
 */
public class ADXMetric extends NQStockMetric {

    public static final String NAME = "adx";

    private ADXMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {

        List<Quote> allQuotes = nQuotes.getAllQuotes();
        for(int i = nQuotes.getFirst(); i <= nQuotes.getLast(); i++){

            double yHigh = 0.0;
            double yLow = 0.0;
            if(i > 1){
                yHigh = allQuotes.get(i - 1).getMetric(HighMetric.NAME).getValue();
                yLow = allQuotes.get(i - 1).getMetric(LowMetric.NAME).getValue();
            }

            //TODO: Finish this.



        }

    }

}
