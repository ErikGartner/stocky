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

    public abstract StockTrend prediction();

}
