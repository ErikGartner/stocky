package analyser;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.weka.WekaClassifier;
import weka.classifiers.functions.Logistic;

/**
 * Created by erik on 06/03/14.
 */
public class LogisticSimplePredictor extends StockPredictor {

    public LogisticSimplePredictor() {
        super();
    }

    @Override
    protected Classifier classifier(Dataset dataset) {
        Logistic lr = new Logistic();
        return new WekaClassifier(lr);

    }

    @Override
    protected String getLongName() {
        return "Logistic Regression Simple Predictor";
    }

    @Override
    protected String getOptions() {
        return "";
    }

    public static String getName() {
        return "log";
    }

}
