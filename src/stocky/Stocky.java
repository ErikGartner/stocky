package stocky;

import analyser.SimplePredictor;
import analyser.StockPredictor;
import dataloader.StockDataLoader;
import dataloader.YahooStockLoader;
import org.joda.time.LocalDate;
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
        LocalDate start = LocalDate.parse("2000-01-01");
        LocalDate end = LocalDate.parse("2013-12-30");
        StockDataLoader loader = new YahooStockLoader(symbols, start, end);
        List<Stock> stocks = loader.getStockList();
        for (Stock stock : stocks) {
            StockPredictor predictor = new SimplePredictor(stock);
            System.out.println(stock.getName() + ": " + predictor.prediction());
        }

    }

}
