package stocky;

import analyser.*;
import dataloader.StockDataLoader;
import dataloader.YahooStockLoader;
import org.joda.time.LocalDate;
import stock.NQuotes;
import stock.Quote;
import stock.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik on 04/03/14.
 */
public class Stocky {

    public static final boolean DEBUG = true;

    public static void main(String[] args) {

        List<String> symbols = new ArrayList<String>();
        symbols.add("AAPL");
        symbols.add("%5EOMX");
        symbols.add("%5EBSESN");
        LocalDate start = LocalDate.parse("2000-01-01");
        LocalDate end = LocalDate.parse("2014-03-09");
        StockDataLoader loader = new YahooStockLoader(symbols, start, end);
        List<Stock> stocks = loader.createStockList();

        StockPredictor predictor = new KNNSimplePredictor();
        StockPredictor predictor1 = new LogisticSimplePredictor();
        StockPredictor predictor2 = new NBayesSimplePredictor();
        StockPredictor predictor3 = new SVMSimplePredictor();
        List<StockPredictor> predictors = new ArrayList<StockPredictor>();
        predictors.add(predictor);
        predictors.add(predictor1);
        predictors.add(predictor2);
        predictors.add(predictor3);

        for(StockPredictor sp : predictors){
            for (Stock stock : stocks) {

                sp.buildPredictor(stock);

                System.out.println(sp.toString());
                System.out.println(stock.getName() + ": " + sp.prediction());

            }
        }

    }

}
