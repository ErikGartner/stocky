package analyser;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.meta.Bagging;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.sampling.Sampling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by erik on 07/03/14.
 */
public class BootstrappingPredictor extends StockPredictor {

    private List<StockPredictor> predictors;
    private Sampling samplingMethod;

    public BootstrappingPredictor(List<StockPredictor> predictors) {
        super();
        this.predictors = new ArrayList<StockPredictor>(predictors);
    }

    @Override
    public void setUsedMetrics(String[] usedMetrics) {
        throw new UnsupportedOperationException("setUsedMetric is illegal on Bootstrapping Predictor.");
    }

    @Override
    protected String getLongName() {
        return "Bootstrapping Predictor";
    }

    @Override
    protected String getOptions() {
        StringBuilder sb = new StringBuilder();
        for (StockPredictor sp : predictors) {
            String words[] = sp.getLongName().split(" ");
            String acronym = "";
            for (String s : words) {
                acronym += s.charAt(0);
            }
            sb.append(acronym).append(", ");
        }
        return String.format("%s from: %s", samplingMethod, sb.substring(0, sb.length() - 2));
    }

    /**
     * Returns the used classifier.
     *
     * @param dataset
     */
    @Override
    protected Classifier classifier(Dataset dataset) {

        Classifier[] classifiers = new Classifier[predictors.size()];
        for (int i = 0; i < classifiers.length; i++) {
            classifiers[i] = predictors.get(i).classifier(dataset);
        }

        Classifier classifier = null;
        double accuracy = 0.0;
        Random rnd = new Random();
        for (Sampling sampling : Sampling.values()) {

            classifier = new Bagging(classifiers, sampling, rnd.nextLong());
            double newAccuracy = crossValidateAccuracy(classifier, dataset);
            if (newAccuracy > accuracy) {
                samplingMethod = sampling;
                accuracy = newAccuracy;
            }

        }

        return classifier;

    }
}
