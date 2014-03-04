package dataloader;

import org.joda.time.LocalDate;
import stock.Stock;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by erik on 04/03/14.
 */
public abstract class StockDataLoader {

    protected LocalDate start;
    protected LocalDate end;
    protected List<String> symbols;

    public StockDataLoader(List<String> symbols, LocalDate start, LocalDate end) {
        this.symbols = symbols;
        this.start = start;
        this.end = end;
    }

    protected String readFromURL(String url) {
        try {
            return new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Failed to from url: " + url);
        }
    }

    public abstract List<Stock> getStockList();

}
