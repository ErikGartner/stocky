package analyser;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.weka.WekaClassifier;
import stock.Stock;
import stock.StockTrend;
import weka.classifiers.bayes.NaiveBayes;

/**
 * Created by erik on 06/03/14.
 */
public class NBayesSimplePredictor extends SimplePredictor {

    public NBayesSimplePredictor(Stock stock, String[] usedMetrics) {
        super(stock, usedMetrics);
    }

    public NBayesSimplePredictor(Stock stock) {
        super(stock);
    }

    @Override
    protected StockTrend classify(Instance target, Dataset dataset) {

        NaiveBayes nb = new NaiveBayes();
        Classifier classifier = new WekaClassifier(nb);
        accuracy = crossValidateAccuracy(classifier, dataset);
        classifier.buildClassifier(dataset);
        return (StockTrend) classifier.classify(target);

    }

    @Override
    protected String getName() {
        return "Native Bayes Simple Predictor";
    }

    @Override
    protected String getOptions() {
        return "";
    }

}
