package com.smoiz.stocky.analyser;

import com.smoiz.stocky.metrics.derived.*;
import com.smoiz.stocky.stock.Stock;
import com.smoiz.stocky.stock.StockTrend;

/**
 * Created by erik on 04/02/15.
 */
public abstract class StockPredictor {

    protected Stock stock;

    public StockPredictor(Stock stock){
        this.stock = stock;
    }

    public String toString() {
        return String.format("%s[%s] - %s", getLongName(), getOptions(), stock.getSymbol());
    }

    public Stock getStock(){
        return stock;
    }

    protected abstract String getLongName();

    protected abstract String getOptions();

    public abstract StockTrend prediction();

    public abstract double accuracy();

}
