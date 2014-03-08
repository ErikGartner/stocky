package analyser;

import stock.Stock;
import stock.StockTrend;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by erik on 07/03/14.
 */
public class VoteMetaPredictor extends MetaPredictor {

    public VoteMetaPredictor(List<StockPredictor> predictors) {
        super(predictors);
    }

    @Override
    protected StockTrend selectStockTrend(Map<StockPredictor, StockTrend> votes) {
        Collection<StockTrend> voteList = votes.values();
        StockTrend trend = null;
        int count = 0;
        for (StockTrend st : StockTrend.values()) {
            int i = Collections.frequency(voteList, st);
            if (i > count) {
                count = i;
                trend = st;
            }
        }
        return trend;
    }

    @Override
    protected String getName() {
        return "Voting Meta Predictor";
    }

    @Override
    protected String getOptions() {
        StringBuilder sb = new StringBuilder();
        for(StockPredictor sp : predictors){
            sb.append(sp.getName()).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }
}
