package com.smoiz.stocky;

import com.smoiz.stocky.analyser.PredictorFactory;
import com.smoiz.stocky.analyser.StockPredictor;
import com.smoiz.stocky.analyser.WekaPredictor;
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
        debug = this.settings.debug();

        StockDataLoader loader = new YahooStockLoader(settings.getSymbols(), start, end);
        stocks = loader.createStockList();
        stockPredictors = PredictorFactory.createPredictors(settings, stocks);

    }

    public void predictStocks() {

        for (StockPredictor predictor : stockPredictors) {

            notifier.send(predictor.getStock().getName(), predictor.prediction().toString());
            System.out.printf("\t%s: %s with accuracy %f\n", predictor, predictor.prediction(), predictor.accuracy());

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
