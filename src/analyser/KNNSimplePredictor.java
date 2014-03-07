package analyser;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KDtreeKNN;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import stock.Stock;
import stock.StockTrend;

/**
 * Created by erik on 06/03/14.
 */
public class KNNSimplePredictor extends SimplePredictor {

    private int bestK = 2;

    public KNNSimplePredictor(Stock stock, String[] usedMetrics) {
        super(stock, usedMetrics);
    }

    public KNNSimplePredictor(Stock stock) {
        super(stock);
    }

    protected StockTrend classify(Instance target, Dataset dataset) {

        Classifier classifier = null;
        accuracy = 0.0;
        for (int i = 2; i < 10; i++) {

            classifier = new KDtreeKNN(i);
            double newAccuracy = crossvalidateAccuracy(classifier, dataset);
            if (newAccuracy > accuracy) {
                bestK = i;
                accuracy = newAccuracy;
            }

        }

        classifier = new KNearestNeighbors(bestK);
        classifier.buildClassifier(dataset);
        return (StockTrend) classifier.classify(target);
    }

    @Override
    protected String getName() {
        return "K-Nearest Neighbours Simple Predictor";
    }

    @Override
    protected String getOptions() {
        return "k=" + bestK;
    }

}
