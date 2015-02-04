package com.smoiz.stocky.analyser;

import com.smoiz.stocky.stock.Stock;
import com.smoiz.stocky.stock.StockTrend;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;

/**
 * Created by erik on 02/02/15.
 */
public class NeuralPredictor extends StockPredictor {

    public NeuralPredictor(String metrics[], Stock stock) {
        super(stock);
    }

    @Override
    protected String getLongName() {
        return null;
    }

    @Override
    protected String getOptions() {
        return null;
    }

    @Override
    public StockTrend prediction() {
        return null;
    }

    @Override
    public double accuracy() {
        return 0;
    }
}
