package dataloader;

import metrics.*;
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
    public List<Stock> createStockList() {
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

        if (!"Date,Open,High,Low,Close,Volume,Adj Close".equalsIgnoreCase(scan.nextLine())) {
            throw new RuntimeException("Yahoo header was unexpected!");
        }

        scan.useDelimiter(",|\\n");

        while (scan.hasNext()) {
            LocalDate date = LocalDate.parse(scan.next(), formatter);
            Quote q = new Quote(date);

            q.addMetric(new OpenMetric(Double.parseDouble(scan.next())));
            q.addMetric(new HighMetric(Double.parseDouble(scan.next())));
            q.addMetric(new LowMetric(Double.parseDouble(scan.next())));
            q.addMetric(new CloseMetric(Double.parseDouble(scan.next())));
            q.addMetric(new VolumeMetric(Double.parseDouble(scan.next())));
            q.addMetric(new AdjustedCloseMetric(Double.parseDouble(scan.next())));

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
