package dataloader;

import metrics.*;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import stock.Quote;
import stock.Stock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by erik on 04/03/14.
 */
public class YahooStockLoader extends StockDataLoader {

    private static final String dataURL = "http://ichart.finance.yahoo.com/table.csv?";
    private static final String dataURLOptions = "s=%s&a=%d&b=%d&c=%d&d=%d&e=%d&f=%d&g=%s&ignore=.csv";
    private static final String nameURL = "http://finance.yahoo.com/d/quotes.csv?s=%s&f=n";
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

        List<Quote> quotes = new LinkedList<Quote>();

        String data = readFromURL(queryUrl(symbol));
        Scanner scan = new Scanner(data);

        if (!"Date,Open,High,Low,Close,Volume,Adj Close".equalsIgnoreCase(scan.nextLine())) {
            throw new RuntimeException("Yahoo header was unexpected!");
        }

        scan.useDelimiter(",|\\n");

        while (scan.hasNext()) {
            LocalDate date = LocalDate.parse(scan.next(), formatter);
            List<StockMetric> metricList = new LinkedList<StockMetric>();

            metricList.add(new OpenMetric(Double.parseDouble(scan.next())));
            metricList.add(new HighMetric(Double.parseDouble(scan.next())));
            metricList.add(new LowMetric(Double.parseDouble(scan.next())));
            metricList.add(new CloseMetric(Double.parseDouble(scan.next())));
            metricList.add(new VolumeMetric(Double.parseDouble(scan.next())));
            metricList.add(new AdjustedCloseMetric(Double.parseDouble(scan.next())));

            quotes.add(new Quote(date, metricList));
        }

        String name = fetchName(symbol);
        Stock stock;
        if (name.isEmpty()) {
            stock = new Stock(symbol, quotes);
        } else {
            stock = new Stock(symbol, name, quotes);
        }

        return stock;
    }

    /**
     * Generates the appropriate yahoo request dataURL.
     */
    private String queryUrl(String symbol) {
        return dataURL + String.format(dataURLOptions, symbol, start.getMonthOfYear() - 1, start.getDayOfMonth(), start.getYear(),
                end.getMonthOfYear() - 1, end.getDayOfMonth(), end.getYear(), "d");
    }

    /**
     * Downloads the user friendly name from yahoo.
     */
    private String fetchName(String symbol) {

        String url = String.format(nameURL, symbol);
        String data = readFromURL(url);

        if (data.length() < 3) {
            return "";
        } else {
            return data.substring(1, data.length() - 3);
        }

    }

}
