package analyser;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.meta.Bagging;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.sampling.Sampling;
import stock.Stock;
import stock.StockTrend;

import java.util.*;

/**
 * Created by erik on 07/03/14.
 */
public class BootstrappingPredictor extends StockPredictor {

    private List<StockPredictor> predictors;

    public BootstrappingPredictor(List<StockPredictor> predictors) {
        super();
        this.predictors = predictors;
    }

    @Override
    protected String getName() {
        return "Bootstrapping Predictor";
    }

    @Override
    protected String getOptions() {
        StringBuilder sb = new StringBuilder();
        for(StockPredictor sp : predictors){
            String words[] = sp.getName().split(" ");
            String acronym = "";
            for(String s : words){
                acronym += s.charAt(0);
            }
            sb.append(acronym).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    /**
     * Returns the used classifier.
     *
     * @param dataset
     */
    @Override
    protected Classifier classifier(Dataset dataset) {
        Classifier[] classifiers = new Classifier[predictors.size()];
        for(int i = 0; i < classifiers.length; i++){
            classifiers[i] = predictors.get(i).classifier(dataset);
        }
        Random rnd = new Random(4325435);
        return new Bagging(classifiers, Sampling.StratifiedNormalBootstrapping, rnd.nextLong());
    }
}
