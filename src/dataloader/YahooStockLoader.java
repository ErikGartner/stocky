package dataloader;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import stock.Quote;
import stock.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by erik on 04/03/14.
 */
public class YahooStockLoader extends StockDataLoader {

    private static final String URL = "http://ichart.finance.yahoo.com/table.csv?";
    private static final String options = "s=%s&a=%d&b=%d&c=%d&d=%d&e=%d&f=%d&g=%s&ignore=.csv";
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    public YahooStockLoader(List<String> symbols, LocalDate start, LocalDate end) {
        super(symbols, start, end);
    }

    @Override
    public List<Stock> getStockList() {
        List<Stock> stocks = new ArrayList<Stock>(symbols.size());
        for (String symbol : symbols) {
            stocks.add(getStock(symbol));
        }
        return stocks;
    }

    /**
     * Downloads the history for one stock.
     */
    private Stock getStock(String symbol) {

        Stock stock = new Stock(symbol);

        String data = readFromURL(queryUrl(symbol));
        Scanner scan = new Scanner(data);
        scan.nextLine();    //skip header line
        scan.useDelimiter(",|\\n");

        while (scan.hasNext()) {
            LocalDate date = LocalDate.parse(scan.next(), formatter);
            double open = Double.parseDouble(scan.next());
            double high = Double.parseDouble(scan.next());
            double low = Double.parseDouble(scan.next());
            double close = Double.parseDouble(scan.next());
            int volume = Integer.parseInt(scan.next());
            double adjustedClose = Double.parseDouble(scan.next());
            Quote q = new Quote(date, open, close, high, low, volume);
            stock.addQuote(q);
        }
        return stock;
    }

    /**
     * Generates the appropriate yahoo request URL.
     */
    private String queryUrl(String symbol) {
        return URL + String.format(options, symbol, start.getMonthOfYear() - 1, start.getDayOfMonth(), start.getYear(),
                end.getMonthOfYear() - 1, end.getDayOfMonth(), end.getYear(), "d");
    }

}
