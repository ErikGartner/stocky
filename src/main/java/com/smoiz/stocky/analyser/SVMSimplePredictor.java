package com.smoiz.stocky.analyser;

import com.smoiz.stocky.stock.Stock;
import libsvm.LibSVM;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;

/**
 * Created by erik on 09/03/14.
 */
public class SVMSimplePredictor extends WekaPredictor {

    public SVMSimplePredictor(String metrics[], Stock stock) {
        super(metrics, stock);
    }

    /**
     * Returns the used classifier.
     *
     * @param dataset
     */
    @Override
    protected Classifier classifier(Dataset dataset) {
        return new LibSVM();
    }

    @Override
    protected String getLongName() {
        return "Support Vector Simple Predictor";
    }

    @Override
    protected String getOptions() {
        return "";
    }

    public static String getName() {
        return "svm";
    }
}
