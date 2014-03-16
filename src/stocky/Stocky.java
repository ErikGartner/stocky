package stocky;

import analyser.PredictorFactory;
import analyser.StockPredictor;
import dataloader.StockDataLoader;
import dataloader.YahooStockLoader;
import org.joda.time.LocalDate;
import stock.Stock;

import java.util.List;

/**
 * Created by erik on 04/03/14.
 */
public class Stocky {

    private Settings settings;
    private PushoverNotifier notifier;
    private LocalDate start;
    private LocalDate end;
    private List<StockPredictor> stockPredictors;
    private List<Stock> stocks;

    public Stocky(Settings settings) {

        this.settings = settings;
        notifier = new PushoverNotifier(settings.getPushoverRecipients());

        start = LocalDate.parse(settings.getStart());
        end = LocalDate.parse(settings.getEnd());
        StockDataLoader loader = new YahooStockLoader(settings.getSymbols(), start, end);
        stocks = loader.createStockList();
        stockPredictors = PredictorFactory.createPredictors(settings);

    }

    public void predictStocks() {

        for (Stock stock : stocks) {

            System.out.println(stock.getName());
            for (StockPredictor predictor : stockPredictors) {

                predictor.buildPredictor(stock);
                System.out.printf("\t%s: %s with accuracy %f\n", predictor, predictor.prediction(), predictor.accuracy());

            }

        }

    }

}
