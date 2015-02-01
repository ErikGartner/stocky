package com.smoiz.stocky;

import com.smoiz.stocky.analyser.PredictorFactory;
import com.smoiz.stocky.analyser.StockPredictor;
import com.smoiz.stocky.dataloader.StockDataLoader;
import com.smoiz.stocky.dataloader.YahooStockLoader;
import com.smoiz.stocky.evaluator.SimpleEvaluator;
import com.smoiz.stocky.stock.Stock;
import com.smoiz.stocky.utils.PushoverNotifier;
import com.smoiz.stocky.utils.Settings;
import org.joda.time.LocalDate;

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

    private static boolean debug;

    public Stocky(Settings settings) {

        this.settings = settings;
        notifier = new PushoverNotifier(settings.pushoverKey(), settings.getPushoverRecipients());

        start = LocalDate.parse(settings.getStart());
        end = LocalDate.parse(settings.getEnd());
        StockDataLoader loader = new YahooStockLoader(settings.getSymbols(), start, end);
        stocks = loader.createStockList();
        stockPredictors = PredictorFactory.createPredictors(settings);
        debug = this.settings.debug();

    }

    public void predictStocks() {

        for (Stock stock : stocks) {

            System.out.println(stock.getName());
            for (StockPredictor predictor : stockPredictors) {

                predictor.buildPredictor(stock);
                SimpleEvaluator se = new SimpleEvaluator(predictor, stock, LocalDate.parse("2014-01-01"), settings.getPeriodSize());
                notifier.send(stock.getName(), predictor.prediction().toString());
                System.out.printf("\t%s: %s with accuracy %f. Performance: %f\n", predictor, predictor.prediction(), predictor.accuracy(), se.performance());
                System.out.println(se.detailedPerformance());

            }

        }

    }

    public static void dbg(String msg){
        dbgf(msg);
    }

    public static void dbgf(String msg, Object... args){
        if(debug)
            System.out.println(String.format(msg, args));
    }

}
