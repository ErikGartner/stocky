package stocky;

import analyser.*;
import dataloader.StockDataLoader;
import dataloader.YahooStockLoader;
import org.joda.time.LocalDate;
import stock.Stock;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik on 04/03/14.
 */
public class Stocky {

    public static final boolean DEBUG = true;
    public static PushoverNotifier notifier;
    public static Settings settings;

    public static void main(String[] args) {

        try {
            settings = Settings.readSettings("config.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        notifier = new PushoverNotifier(settings.getPushoverRecipients());

        LocalDate start = LocalDate.parse(settings.getStart());
        LocalDate end = LocalDate.parse(settings.getEnd());
        StockDataLoader loader = new YahooStockLoader(settings.getSymbols(), start, end);
        List<Stock> stocks = loader.createStockList();


        StockPredictor predictor = new KNNSimplePredictor();
        StockPredictor predictor1 = new LogisticSimplePredictor();
        StockPredictor predictor2 = new NBayesSimplePredictor();
        StockPredictor predictor3 = new SVMSimplePredictor();
        predictor.setUsedMetrics(settings.getUsedMetrics());
        predictor1.setUsedMetrics(settings.getUsedMetrics());
        predictor2.setUsedMetrics(settings.getUsedMetrics());
        predictor3.setUsedMetrics(settings.getUsedMetrics());
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
