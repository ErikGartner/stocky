package com.smoiz.stocky.analyser;

import com.smoiz.stocky.stock.Stock;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KDtreeKNN;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;

/**
 * Created by erik on 06/03/14.
 */
public class KNNSimplePredictor extends WekaPredictor {

    private int bestK = 2;

    public KNNSimplePredictor(String metrics[], Stock stock) {
        super(metrics, stock);
    }

    public static String getName() {
        return "knn";
    }

    @Override
    protected Classifier classifier(Dataset dataset) {
        Classifier classifier = null;
        double accuracy = 0.0;
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
    protected String getLongName() {
        return "K-Nearest Neighbours Simple Predictor";
    }

    @Override
    protected String getOptions() {
        return "k=" + bestK;
    }

}
