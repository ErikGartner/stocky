package analyser;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KDtreeKNN;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;

/**
 * Created by erik on 06/03/14.
 */
public class KNNSimplePredictor extends SimplePredictor {

    private int bestK = 2;

    public KNNSimplePredictor() {
        super();
    }

    @Override
    protected Classifier classifier(Dataset dataset) {
        Classifier classifier = null;
        accuracy = 0.0;
        for (int i = 2; i <= 10; i++) {

            classifier = new KDtreeKNN(i);
            double newAccuracy = crossValidateAccuracy(classifier, dataset);
            if (newAccuracy > accuracy) {
                bestK = i;
                accuracy = newAccuracy;
            }

        }

        return new KNearestNeighbors(bestK);
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
