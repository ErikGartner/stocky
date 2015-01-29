package com.smoiz.stocky.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Make the class immutable.
 * Created by erik on 04/03/14.
 */
public class Stock {

    private String symbol;
    private String name;
    private List<Quote> quotes;

    public Stock(String symbol, List<Quote> quotes) {
        this(symbol, symbol, quotes);
    }

    public Stock(String symbol, String name, List<Quote> quotes) {
        this.name = name;
        this.symbol = symbol;
        this.quotes = new ArrayList<Quote>(quotes);
        Collections.sort(this.quotes);
    }

    public List<Quote> getQuotes() {
        return new ArrayList<Quote>(quotes);
    }

    public List<NQuotes> getNQuotes(int n) {
        return NQuotes.createNQuotes(getQuotes(), n);
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
