package analyser;

import stock.Quote;
import stock.Stock;
import stock.StockTrend;

import java.util.*;

/**
 * Created by erik on 07/03/14.
 */
public abstract class MetaPredictor extends StockPredictor {

    protected List<StockPredictor> predictors;

    protected MetaPredictor(List<StockPredictor> predictors) {
        super();
        this.predictors = predictors;
    }

    @Override
    public double accuracy() {
        return -1;
    }

    protected Map<StockPredictor, StockTrend> calculateTrends() {
        Map<StockPredictor, StockTrend> votes = new HashMap<StockPredictor, StockTrend>(predictors.size());
        for (StockPredictor sp : predictors) {
            votes.put(sp, sp.prediction());
        }
        return votes;
    }

    protected void buildPredictors(Stock stock){
        for(StockPredictor sp : predictors){
            sp.buildPredictor(stock);
        }
    }

    @Override
    protected StockTrend computePrediction() {
        buildPredictors(stock);
        Map<StockPredictor, StockTrend> votes = calculateTrends();
        return selectStockTrend(votes);
    }

    /**
     * This method is VERY slow, and for that reason isn't currently used.
     */
    protected double crossValidateAccuracy(){

        int setSize = stock.getQuotes().size();
        List<Quote> completeSet = stock.getQuotes();
        List<Quote> trainingSet = completeSet.subList(0, (int) (setSize * 0.8));
        Stock trainingStock = new Stock(stock.getSymbol(), trainingSet);

        int correct = 0;
        int tested = 0;
        for(int i = trainingSet.size(); i < setSize; i++){
            buildPredictors(trainingStock);
            StockTrend st = selectStockTrend(calculateTrends());
            StockTrend expected = StockTrend.trendFromCloseClose(completeSet.get(i - 1), completeSet.get(i));
            tested++;
            if(st.equals(expected)){
                correct++;
            }
            trainingStock.addQuote(completeSet.get(i));
        }

        return (double)correct/tested;

    }

    protected abstract StockTrend selectStockTrend(Map<StockPredictor, StockTrend> votes);

}
