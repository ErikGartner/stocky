package analyser;

import libsvm.LibSVM;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import stock.StockTrend;

/**
 * Created by erik on 09/03/14.
 */
public class SVMSimplePredictor extends SimplePredictor {

    public SVMSimplePredictor(String[] usedMetrics) {
        super(usedMetrics);
    }

    public SVMSimplePredictor() {
        super();
    }

    @Override
    protected StockTrend classify(Instance target, Dataset dataset) {
        Classifier classifier = new LibSVM();
        accuracy = crossValidateAccuracy(classifier, dataset);
        classifier.buildClassifier(dataset);
        return (StockTrend) classifier.classify(target);
    }

    @Override
    protected String getName() {
        return "Support Vector Simple Predictor";
    }

    @Override
    protected String getOptions() {
        return "";
    }
}
