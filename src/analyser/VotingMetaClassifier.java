package analyser;

import stock.Stock;
import stock.StockTrend;

import java.util.Collections;
import java.util.List;

/**
 * Created by erik on 07/03/14.
 */
public class VotingMetaClassifier extends MetaClassifier {

    public VotingMetaClassifier(Stock stock, List<StockPredictor> predictors) {
        super(stock, predictors);
    }

    @Override
    protected StockTrend computePrediction() {

        List<StockTrend> votes = calculateTrends();

        StockTrend trend = null;
        int count = 0;
        for (StockTrend st : StockTrend.values()) {
            int i = Collections.frequency(votes, st);
            if (i > count) {
                count = i;
                trend = st;
            }
        }

        return trend;
    }

    @Override
    protected String getName() {
        return "Voting Meta Classifier";
    }

    @Override
    protected String getOptions() {
        return predictors.toString();
    }
}
