package analyser;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.weka.WekaClassifier;
import stock.Stock;
import stock.StockTrend;
import weka.classifiers.functions.Logistic;

/**
 * Created by erik on 06/03/14.
 */
public class LogisticSimplePredictor extends SimplePredictor {

    public LogisticSimplePredictor(Stock stock, String[] usedMetrics) {
        super(stock, usedMetrics);
    }

    public LogisticSimplePredictor(Stock stock) {
        super(stock);
    }

    @Override
    protected StockTrend classify(Instance target, Dataset dataset) {

        Logistic lr = new Logistic();
        Classifier classifier = new WekaClassifier(lr);
        accuracy = crossValidateAccuracy(classifier, dataset);
        classifier.buildClassifier(dataset);
        return (StockTrend) classifier.classify(target);

    }

    @Override
    protected String getName() {
        return "Logistic Regression Simple Predictor";
    }

    @Override
    protected String getOptions() {
        return "";
    }

}
