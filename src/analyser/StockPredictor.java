package analyser;

import metrics.StockMetric;
import metrics.derived.AverageCloseMetric;
import metrics.derived.AverageVolumeMetric;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import stock.NQuotes;
import stock.Stock;
import stock.StockTrend;

import java.util.List;
import java.util.Map;

/**
 * Created by erik on 05/03/14.
 */
public abstract class StockPredictor {

    public static final String[] DEFAULT_METRICS = {AverageCloseMetric.NAME, AverageVolumeMetric.NAME};

    protected Stock stock;
    protected StockTrend predicted;
    protected String[] usedMetrics;
    protected double accuracy;

    protected StockPredictor() {
        accuracy = -1;
        usedMetrics = DEFAULT_METRICS;
    }

    public String toString() {
        return String.format("%s[%s] with accuracy %f", getName(), getOptions(), accuracy());
    }

    /**
     * Need to be called before using the predictor.
     */
    public void buildPredictor(Stock stock) {
        this.stock = stock;
        predicted = computePrediction();
    }

    public StockTrend prediction() {
        if (predicted == null) {
            throw new RuntimeException("Predictor not built.");
        }
        return predicted;
    }

    public double accuracy() {
        return accuracy;
    }

    public void setUsedMetrics(String[] metricNames) {
        usedMetrics = metricNames;
    }

    protected StockTrend computePrediction() {
        List<NQuotes> nQuotesList = stock.getNQuotes(5);
        Dataset dataset = createDataset(nQuotesList);
        Instance target = getTarget(nQuotesList);
        Classifier classifier = classifier(dataset);
        accuracy = crossValidateAccuracy(classifier, dataset);
        classifier.buildClassifier(dataset);
        return (StockTrend) classifier.classify(target);
    }

    protected Dataset createDataset(List<NQuotes> nQuotesList) {
        Dataset dataset = new DefaultDataset();

        for (int i = 0; i < nQuotesList.size() - 1; i++) {

            NQuotes q_prev = nQuotesList.get(i);
            NQuotes q_next = nQuotesList.get(i + 1);
            Instance instance = getInstance(q_prev);
            instance.setClassValue(StockTrend.trendFromNQuotesClose(q_prev, q_next));
            dataset.add(instance);

        }

        return dataset;
    }

    protected Instance getInstance(NQuotes nQuotes) {

        List<StockMetric> stockMetrics = nQuotes.getMetrics();
        double values[] = new double[usedMetrics.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = stockMetrics.get(i).getValue();
        }
        return new DenseInstance(values);

    }

    protected Instance getTarget(List<NQuotes> nQuotesList) {
        return getInstance(nQuotesList.get(nQuotesList.size() - 1));
    }

    /**
     * Computes the accuracy of an classifier on the dataset
     */
    protected double crossValidateAccuracy(Classifier classifier, Dataset dataset) {
        CrossValidation cv = new CrossValidation(classifier);
        Map<Object, PerformanceMeasure> performances = cv.crossValidation(dataset, 10);

        double newAccuracy = 0.0;
        for (PerformanceMeasure pm : performances.values()) {
            newAccuracy += pm.getAccuracy();
        }
        return newAccuracy / performances.size();
    }

    protected abstract String getName();

    protected abstract String getOptions();

    /**
     * Returns the used classifier.
     *
     * @param dataset
     */
    protected abstract Classifier classifier(Dataset dataset);
}
