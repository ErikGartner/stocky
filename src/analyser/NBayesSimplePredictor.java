package analyser;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.weka.WekaClassifier;
import weka.classifiers.bayes.NaiveBayes;

/**
 * Created by erik on 06/03/14.
 */
public class NBayesSimplePredictor extends StockPredictor {

    public NBayesSimplePredictor() {
        super();
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
