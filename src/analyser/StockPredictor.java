package analyser;

import stock.Stock;
import stock.StockTrend;

/**
 * Created by erik on 05/03/14.
 */
public abstract class StockPredictor {

    protected Stock stock;
    protected StockTrend predicted;

    protected StockPredictor() {
    }

    public String toString() {
       return String.format("%s[%s] with accuracy %f", getName(), getOptions(), accuracy());
    }

    /**
     * Need to be called before using the predictor.
     */
    public void buildPredictor(Stock stock){
        this.stock = stock;
        predicted = computePrediction();
    }

    public StockTrend prediction() {
        if (predicted == null) {
            throw new RuntimeException("Predictor not built.");
        }
        return predicted;
    }

    public abstract double accuracy();

    protected abstract StockTrend computePrediction();

    protected abstract String getName();

    protected abstract String getOptions();

}
