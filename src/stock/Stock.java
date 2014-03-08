package stock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by erik on 04/03/14.
 */
public class Stock {

    private String symbol;
    private String name;
    private List<Quote> quotes;

    public Stock(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        quotes = new LinkedList<Quote>();
    }

    public Stock(String symbol, List<Quote> quotes){
        this.name = symbol;
        this.symbol = symbol;
        this.quotes = new LinkedList<Quote>(quotes);
    }

    public Stock(String symbol) {
        this(symbol, symbol);
    }

    public void addQuote(Quote q) {
        quotes.add(q);
    }

    public List<Quote> getQuotes() {
        return new ArrayList<Quote>(quotes);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        for (Quote q : quotes) {
            sb.append('\n');
            sb.append('\t');
            sb.append(q);
        }
        return sb.toString();
    }

    public String getSymbol() {
        return symbol;
    }
}
