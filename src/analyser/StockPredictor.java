package analyser;

import stock.Stock;
import stock.StockTrend;

/**
 * Created by erik on 05/03/14.
 */
public abstract class StockPredictor {

    protected Stock stock;

    protected StockPredictor(Stock stock) {
        this.stock = stock;
    }

    public String toString() {
        return String.format("%s[%s] with accuracy %f", getName(), getOptions(), accuracy());
    }

    public abstract StockTrend prediction();

    public abstract double accuracy();

    protected abstract String getName();

    protected abstract String getOptions();

}
