package stock;

import metrics.StockMetric;
import metrics.derived.*;

import java.util.*;

/**
 * Immutable class containing N Quotes.
 * Created by erik on 09/03/14.
 */
public class NQuotes implements Comparable<NQuotes> {

    private List<Quote> quotes;
    private Map<String, NQStockMetric> metrics;
    private int first, last;

    public NQuotes(List<Quote> quotes, int first, int last) {
        this.quotes = quotes;
        this.first = first;
        this.last = last;
        metrics = new HashMap<String, NQStockMetric>();
        computeMetrics();
    }

    public List<Quote> getQuotes() {
        return new ArrayList<Quote>(quotes.subList(first, last));
    }

    public List<Quote> getAllQuotes() {
        return new ArrayList<Quote>(quotes);
    }

    public String toString() {
        return String.format("%dQuotes %s->%s with %s", getN(),
                quotes.get(first).getDate(),
                quotes.get(last).getDate(),
                metrics.values());
    }

    public int getFirst() {
        return first;
    }

    public int getLast() {
        return last;
    }

    public int getN(){
        return last - first;
    }

    public StockMetric getMetric(String name) {
        return metrics.get(name);
    }

    public List<StockMetric> getMetrics() {
        return new ArrayList<StockMetric>(metrics.values());
    }

    @Override
    public int compareTo(NQuotes nQuotes) {
        return quotes.get(first).compareTo(nQuotes.quotes.get(nQuotes.first));
    }

    private void computeMetrics() {
        metrics.put(MeanCloseMetric.NAME, MeanCloseMetric.createMetric(this));
        metrics.put(MeanVolumeMetric.NAME, MeanVolumeMetric.createMetric(this));
        metrics.put(MeanChangeMetric.NAME, MeanChangeMetric.createMetric(this));
        metrics.put(HighToMeanMetric.NAME, HighToMeanMetric.createMetric(this));
        metrics.put(VolatilityMetric.NAME, VolatilityMetric.createMetric(this));
        metrics.put(ADXMetric.NAME, ADXMetric.createMetric(this));
    }

    public static List<NQuotes> createNQuotes(List<Quote> quotes, int n) {
        List<NQuotes> nQuotesList = new ArrayList<NQuotes>(quotes.size());
        for (int i = 0; i + n < quotes.size(); i = i + n) {
            nQuotesList.add(new NQuotes(quotes, i, i + n));
        }
        Collections.sort(nQuotesList);
        return nQuotesList;
    }
}
