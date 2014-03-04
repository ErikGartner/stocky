package dataloader;

import org.joda.time.DateTime;
import stock.Stock;

import java.util.List;

/**
 * Created by erik on 04/03/14.
 */
public class YahooStockLoader implements StockDataLoader {

    private final String url = "http://ichart.finance.yahoo.com/table.csv?s=%s&a=%d&b=%d&c=%d&d=%d&e=%d&f=%d&g=%s&ignore=.csv";
    private List<String> symbols;

    public YahooStockLoader(List<String> symbol) {
        this.symbols = symbol;
    }

    @Override
    public List<Stock> getStockList() {

        return null;
    }

    private Stock getStock(String symbol) {
        return null;
    }

    /**
     * Generates the appropriate yahoo request url.
     */
    private String queryUrl(String symbol, DateTime start, DateTime end) {
        return String.format(url, start.getMonthOfYear(), start.dayOfMonth(), start.getYear(),
                end.getMonthOfYear(), end.getDayOfMonth(), end.getYear(), "d");
    }


}
