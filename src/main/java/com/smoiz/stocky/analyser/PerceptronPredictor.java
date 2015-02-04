package com.smoiz.stocky.analyser;

import com.smoiz.stocky.stock.Stock;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.weka.WekaClassifier;
import weka.classifiers.functions.MultilayerPerceptron;

/**
 * Uses the Multilayer Perceptron

 * Created by erik on 02/10/14.
 */
public class PerceptronPredictor extends WekaPredictor {

    private double momentum = 0.3;
    private double learningRate = 0.2;

    public PerceptronPredictor(String metrics[], Stock stock) {
        super(metrics, stock);
    }

    @Override
    protected String getLongName() {
        return "Multilayer Perceptron";
    }

    /**
     * @return Options: Learning rate, momentum & epochs
     */
    @Override
    protected String getOptions() {
        return String.format("LR: %02f, M: %02f", momentum, learningRate);
    }

    public static String getName() {
        return "mlp";
    }

    @Override
    protected Classifier classifier(Dataset dataset) {
        MultilayerPerceptron mlp = new MultilayerPerceptron();
        mlp.setMomentum(momentum);
        mlp.setLearningRate(learningRate);
        return new WekaClassifier(mlp);
    }
}
