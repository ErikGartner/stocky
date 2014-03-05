package analyser;

import metrics.*;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import stock.Quote;
import stock.Stock;
import stock.StockTrend;

import java.util.Collections;
import java.util.List;

/**
 * Created by erik on 05/03/14.
 */
public class SimplePredictor extends StockPredictor {

    public static final String[] DEFAULT_METRICS = {OpenMetric.NAME, CloseMetric.NAME, HighMetric.NAME, LowMetric.NAME, VolumeMetric.NAME};
    private String[] usedMetrics;

    public SimplePredictor(Stock stock) {
        this(stock, DEFAULT_METRICS);
    }

    public SimplePredictor(Stock stock, String[] usedMetrics) {
        super(stock);
        this.usedMetrics = usedMetrics;
    }

    public StockTrend prediction() {

        List<Quote> quotes = stock.getQuotes();
        Collections.sort(quotes);
        Dataset dataset = new DefaultDataset();

        for (int i = 0; i < quotes.size() - 1; i++) {

            Quote q_prev = quotes.get(i);
            Quote q_next = quotes.get(i + 1);
            Instance instance = getInstance(q_prev);
            instance.setClassValue(StockTrend.stockTrend(q_next));
            dataset.add(instance);

        }

        Instance target = getInstance(quotes.get(quotes.size() - 1));

        Classifier classifier = new KNearestNeighbors(5);
        classifier.buildClassifier(dataset);
        return (StockTrend) classifier.classify(target);

    }

    private Instance getInstance(Quote quote) {
        double values[] = new double[usedMetrics.length];
        for (int i = 0; i < usedMetrics.length; i++) {
            values[i] = quote.getMetric(usedMetrics[i]).getValue();
        }
        return new DenseInstance(values);
    }


}
