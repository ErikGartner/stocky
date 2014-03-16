package analyser;

import stocky.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik on 16/03/14.
 */
public class PredictorFactory {

    public static List<StockPredictor> createPredictors(Settings settings) {

        List<String> predictors = settings.getPredictors();
        List<StockPredictor> stockPredictors = new ArrayList<StockPredictor>();
        for (String s : predictors) {
            stockPredictors.add(createStockPredictor(s));
        }

        if (settings.isBootstrapp()) {
            stockPredictors.add(createBootStrappingPredictor(stockPredictors));
        }

        return stockPredictors;
    }

    private static StockPredictor createStockPredictor(String name) {

        if (name.equals(KNNSimplePredictor.getName())) {
            return new KNNSimplePredictor();
        } else if (name.equals(LogisticSimplePredictor.getName())) {
            return new LogisticSimplePredictor();
        } else if (name.equals(NBayesSimplePredictor.getName())) {
            return new NBayesSimplePredictor();
        } else if (name.equals(SVMSimplePredictor.getName())) {
            return new SVMSimplePredictor();
        } else {
            throw new RuntimeException("Unknown predictor identifier in settings.");
        }

    }

    private static StockPredictor createBootStrappingPredictor(List<StockPredictor> predictors) {
        return new BootstrappingPredictor(predictors);
    }

}
