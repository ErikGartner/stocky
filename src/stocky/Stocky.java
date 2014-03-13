package stocky;

import analyser.*;
import dataloader.StockDataLoader;
import dataloader.YahooStockLoader;
import metrics.derived.MeanChangeMetric;
import metrics.derived.MeanCloseMetric;
import metrics.derived.VolatilityMetric;
import org.joda.time.LocalDate;
import stock.NQuotes;
import stock.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik on 04/03/14.
 */
public class Stocky {

    public static final boolean DEBUG = true;

    public static void main(String[] args) {

        PushoverNotifier notifier = new PushoverNotifier();
        List<String> symbols = new ArrayList<String>();
        symbols.add("AAPL");
        symbols.add("AXS");
        symbols.add("^OMX");
        LocalDate start = LocalDate.parse("2000-01-01");
        LocalDate end = LocalDate.parse("2014-03-09");
        String[] metrics = {MeanChangeMetric.NAME,
                MeanCloseMetric.NAME,
                //MeanVolumeMetric.NAME,
                VolatilityMetric.NAME};
        //ADXMetric.NAME};
        StockDataLoader loader = new YahooStockLoader(symbols, start, end);
        List<Stock> stocks = loader.createStockList();

        List<NQuotes> nQuotes = stocks.get(0).getNQuotes(5);
        for(NQuotes nq : nQuotes){
            //       System.out.println(nq);
        }

        StockPredictor predictor = new KNNSimplePredictor();
        StockPredictor predictor1 = new LogisticSimplePredictor();
        StockPredictor predictor2 = new NBayesSimplePredictor();
        StockPredictor predictor3 = new SVMSimplePredictor();
        predictor.setUsedMetrics(metrics);
        predictor1.setUsedMetrics(metrics);
        predictor2.setUsedMetrics(metrics);
        predictor3.setUsedMetrics(metrics);
        List<StockPredictor> predictors = new ArrayList<StockPredictor>();
        predictors.add(predictor);
        predictors.add(predictor1);
        predictors.add(predictor2);
        predictor.setScope(20);
        predictor1.setScope(20);
        predictor2.setScope(20);
        predictor3.setScope(20);
//        predictors.add(predictor3);
        StockPredictor metaPredictor = new BootstrappingPredictor(predictors);

        for (StockPredictor sp : predictors) {
            for (Stock stock : stocks) {

                sp.buildPredictor(stock);

                System.out.println(sp.toString());
                System.out.println(sp.featureScores());
                System.out.println(stock.getName() + ": " + sp.prediction());

            }
        }

        for (Stock stock : stocks) {

            metaPredictor.buildPredictor(stock);
            System.out.println(metaPredictor.toString());
            System.out.println(metaPredictor.featureScores());
            System.out.println(stock.getName() + ": " + metaPredictor.prediction());

        }

    }

}
