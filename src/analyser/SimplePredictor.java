package analyser;

import metrics.*;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import stock.Quote;
import stock.Stock;
import stock.StockTrend;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by erik on 05/03/14.
 */
public abstract class SimplePredictor extends StockPredictor {

    public static final String[] DEFAULT_METRICS = {OpenMetric.NAME, CloseMetric.NAME, HighMetric.NAME,
            LowMetric.NAME, VolumeMetric.NAME};

    private String[] usedMetrics;
    private StockTrend predicted;
    protected double accuracy;

    protected SimplePredictor(Stock stock) {
        this(stock, DEFAULT_METRICS);
    }

    protected SimplePredictor(Stock stock, String[] usedMetrics) {
        super(stock);
        this.usedMetrics = usedMetrics;
    }

    public double accuracy() {
        return accuracy;
    }

    protected StockTrend computePrediction() {

        Dataset dataset = createDataset();
        Instance target = getTarget();
        return classify(target, dataset);

    }

    protected Dataset createDataset() {

        List<Quote> quotes = stock.getQuotes();
        Collections.sort(quotes);
        Dataset dataset = new DefaultDataset();

        for (int i = 0; i < quotes.size() - 1; i++) {

            Quote q_prev = quotes.get(i);
            Quote q_next = quotes.get(i + 1);
            Instance instance = getInstance(q_prev);
            instance.setClassValue(StockTrend.trendFromCloseClose(q_prev, q_next));
            dataset.add(instance);

        }

        return dataset;

    }

    protected Instance getInstance(Quote quote) {

        double values[] = new double[usedMetrics.length];
        for (int i = 0; i < usedMetrics.length; i++) {
            values[i] = quote.getMetric(usedMetrics[i]).getValue();
        }
        return new DenseInstance(values);

    }

    protected Instance getTarget() {

        List<Quote> quotes = stock.getQuotes();
        Collections.sort(quotes);
        return getInstance(quotes.get(quotes.size() - 1));

    }

    /**
     * Computes the accuracy of an classifier on the dataset
     */
    protected double crossvalidateAccuracy(Classifier classifier, Dataset dataset) {

        CrossValidation cv = new CrossValidation(classifier);
        Map<Object, PerformanceMeasure> performances = cv.crossValidation(dataset, 5);

        double newAccuracy = 0.0;
        for (PerformanceMeasure pm : performances.values()) {
            newAccuracy += pm.getAccuracy();
        }
        return newAccuracy / performances.size();
    }

    /**
     * Should set the accuracy variable.
     */
    protected abstract StockTrend classify(Instance target, Dataset dataset);

}
