package com.smoiz.stocky.analyser;

import com.smoiz.stocky.stock.Stock;
import com.smoiz.stocky.utils.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik on 16/03/14.
 */
public class PredictorFactory {

    public static List<StockPredictor> createPredictors(Settings settings, List<Stock> stocks) {

        List<String> predictors = settings.getPredictors();
        List<StockPredictor> stockPredictors = new ArrayList<StockPredictor>();
        for (String s : predictors) {
            for(Stock stock: stocks) {
                StockPredictor stockPredictor = createStockPredictor(settings.getUsedMetrics(), s, stock);
                stockPredictors.add(stockPredictor);
            }
        }

        return stockPredictors;
    }

    public static StockPredictor createStockPredictor(String[] metrics, String name, Stock stock) {

        if (name.equals(KNNSimplePredictor.getName())) {
            return new KNNSimplePredictor(metrics, stock);
        } else if (name.equals(LogisticSimplePredictor.getName())) {
            return new LogisticSimplePredictor(metrics, stock);
        } else if (name.equals(NBayesSimplePredictor.getName())) {
            return new NBayesSimplePredictor(metrics, stock);
        } else if (name.equals(SVMSimplePredictor.getName())) {
            return new SVMSimplePredictor(metrics, stock);
        } else if (name.equals(PerceptronPredictor.getName())){
            return new PerceptronPredictor(metrics, stock);
        } else {
            throw new RuntimeException("Unknown predictor identifier in settings.");
        }

    }

}
