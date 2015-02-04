package com.smoiz.stocky.analyser;

import com.smoiz.stocky.metrics.StockMetric;
import com.smoiz.stocky.metrics.derived.*;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.featureselection.scoring.GainRatio;
import com.smoiz.stocky.stock.NQuotes;
import com.smoiz.stocky.stock.Stock;
import com.smoiz.stocky.stock.StockTrend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by erik on 05/03/14.
 */
public abstract class WekaPredictor extends StockPredictor {

    private Map<String, Double> featureScores;
    private StockTrend predicted;
    private String[] usedMetrics;
    private double accuracy;
    private int scope;

    protected WekaPredictor(String[] metrics, Stock stock) {
        super(stock);
        accuracy = -1;
        usedMetrics = metrics;
        scope = 5;
    }

    public StockTrend prediction() {
        if (predicted == null) {
            predicted = computePrediction();
        }
        return predicted;
    }

    public double accuracy() {
        if (accuracy == -1) {
            computePrediction();
        }
        return accuracy;
    }

    public Map<String, Double> featureScores() {
        if (featureScores == null) {
            computePrediction();
        }
        return featureScores;
    }

    protected StockTrend computePrediction() {
        List<NQuotes> nQuotesList = stock.getNQuotes(scope);
        Dataset dataset = createDataset(nQuotesList);
        Instance target = getTarget(nQuotesList);
        Classifier classifier = classifier(dataset);
        accuracy = crossValidateAccuracy(classifier, dataset);
        featureScores = scoreFeatures(dataset);
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

    protected Map<String, Double> scoreFeatures(Dataset dataset) {
        Map<String, Double> scores = new HashMap<String, Double>();
        GainRatio gainRatio = new GainRatio();
        gainRatio.build(dataset);
        for (int i = 0; i < usedMetrics.length; i++) {
            scores.put(usedMetrics[i], gainRatio.score(i));
        }
        return scores;
    }

    /**
     * Returns the used classifier.
     *
     * @param dataset
     */
    protected abstract Classifier classifier(Dataset dataset);
}
