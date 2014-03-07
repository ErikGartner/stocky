package analyser;

import stock.Stock;
import stock.StockTrend;

/**
 * Created by erik on 05/03/14.
 */
public abstract class StockPredictor {

    protected Stock stock;
    protected StockTrend predicted;

    protected StockPredictor(Stock stock) {
        this.stock = stock;
    }

    public String toString() {
        return String.format("%s[%s] with accuracy %f", getName(), getOptions(), accuracy());
    }

    public StockTrend prediction() {
        if (predicted == null) {
            predicted = computePrediction();
        }
        return predicted;
    }

    public abstract double accuracy();

    protected abstract StockTrend computePrediction();

    protected abstract String getName();

    protected abstract String getOptions();

}
