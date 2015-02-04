package com.smoiz.stocky.analyser;

import com.smoiz.stocky.stock.Stock;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.weka.WekaClassifier;
import weka.classifiers.bayes.NaiveBayes;

/**
 * Created by erik on 06/03/14.
 */
public class NBayesSimplePredictor extends WekaPredictor {

    public NBayesSimplePredictor(String metrics[], Stock stock) {
        super(metrics, stock);
    }

    @Override
    protected Classifier classifier(Dataset dataset) {
        NaiveBayes nb = new NaiveBayes();
        return new WekaClassifier(nb);
    }

    @Override
    protected String getLongName() {
        return "Naive Bayes Simple Predictor";
    }

    @Override
    protected String getOptions() {
        return "";
    }

    public static String getName() {
        return "nbay";
    }

}
