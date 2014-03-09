package stock;

import metrics.StockMetric;
import metrics.derived.AverageCloseMetric;
import metrics.derived.AverageVolumeMetric;
import metrics.derived.NQStockMetric;

import java.util.*;

/**
 * Created by erik on 09/03/14.
 */
public class NQuotes implements Comparable<NQuotes> {

    private List<Quote> quotes;
    private Map<String, NQStockMetric> metrics;

    public NQuotes(List<Quote> quotes) {
        this.quotes = quotes;
        Collections.sort(quotes);
        metrics = new HashMap<String, NQStockMetric>();
        computeMetrics();
    }

    public List<Quote> getQuotes() {
        return new ArrayList<Quote>(quotes);
    }

    public String toString() {
        return String.format("%dQuotes %s->%s with %s", quotes.size(),
                quotes.get(0).getDate(),
                quotes.get(quotes.size() - 1).getDate(),
                metrics.values());
    }

    public Set<String> availableMetrics() {
        return metrics.keySet();
    }

    public StockMetric getMetric(String name) {
        return metrics.get(name);
    }

    public List<StockMetric> getMetrics() {
        return new ArrayList<StockMetric>(metrics.values());
    }

    private void computeMetrics() {
        metrics.put(AverageCloseMetric.NAME, AverageCloseMetric.createMetric(this));
        metrics.put(AverageVolumeMetric.NAME, AverageVolumeMetric.createMetric(this));
    }

    public static List<NQuotes> createNQuotes(List<Quote> quotes, int n) {
        List<NQuotes> nQuotesList = new ArrayList<NQuotes>(quotes.size());
        for (int i = 0; i + n < quotes.size(); i = i + n) {
            nQuotesList.add(new NQuotes(quotes.subList(i, i + n)));
        }
        Collections.sort(nQuotesList);
        return nQuotesList;
    }

    @Override
    public int compareTo(NQuotes nQuotes) {
        return quotes.get(0).compareTo(nQuotes.quotes.get(0));
    }
}
