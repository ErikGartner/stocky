package dataloader;

import org.joda.time.LocalDate;
import stock.Stock;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

/**
 * Created by erik on 04/03/14.
 */
public abstract class StockDataLoader {

    protected LocalDate start;
    protected LocalDate end;
    protected List<String> symbols;

    protected StockDataLoader(List<String> symbols, LocalDate start, LocalDate end) {
        this.symbols = symbols;
        this.start = start;
        this.end = end;
    }

    protected String readFromURL(String url) {
        try {
            String encodedUrl = URLEncoder.encode(url, "UTF-8");
            Scanner scan = new Scanner(new URL(url).openStream(), "UTF-8");
            scan.useDelimiter("\\A");
            String data = scan.next();
            scan.close();
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from url: " + url);
        }
    }

    public abstract List<Stock> createStockList();

}
