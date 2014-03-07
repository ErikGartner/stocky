package analyser;

import stock.Stock;
import stock.StockTrend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik on 07/03/14.
 */
public abstract class MetaClassifier extends StockPredictor {

    protected List<StockPredictor> predictors;

    protected MetaClassifier(Stock stock, List<StockPredictor> predictors) {
        super(stock);
        this.predictors = predictors;
    }

    @Override
    public double accuracy() {
        return -1;
    }

    protected List<StockTrend> calculateTrends() {
        List<StockTrend> votes = new ArrayList<StockTrend>(predictors.size());
        for (StockPredictor sp : predictors) {
            votes.add(sp.prediction());
        }
        return votes;
    }

}
