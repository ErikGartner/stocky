package metrics.derived;

import metrics.HighMetric;
import metrics.LowMetric;
import stock.NQuotes;
import stock.Quote;

import java.util.List;

/**
 * Average directional movement index
 * http://fxtrade.oanda.com/learn/forex-indicators/average-directional-index
 * Created by erik on 11/03/14.
 */
public class ADXMetric extends NQStockMetric {

    public static final String NAME = "adx";

    private ADXMetric(double value) {
        super(NAME, value);
    }

    public static NQStockMetric createMetric(NQuotes nQuotes) {

        double expMovingAvgMult = 2.0 / (nQuotes.getN() + 1);
        double emaTrueRange = 0.0;
        double emaPosDM = 0.0;
        double emaNegDM = 0.0;

        List<Quote> allQuotes = nQuotes.getAllQuotes();
        for (int i = nQuotes.getFirst(); i <= nQuotes.getLast(); i++) {

            int j;
            if (i > 1) {
                j = i - 1;   //   the index of the previous day
            } else {
                j = i;
            }

            double yHigh = allQuotes.get(j).getMetric(HighMetric.NAME).getValue();
            double yLow = allQuotes.get(j).getMetric(LowMetric.NAME).getValue();

            double upMove = allQuotes.get(i).getMetric(HighMetric.NAME).getValue() - yHigh;
            double downMove = yLow - allQuotes.get(i).getMetric(LowMetric.NAME).getValue();

            double posDM = upMove > downMove && upMove > 0 ? upMove : 0;
            double negDM = downMove > upMove && downMove > 0 ? downMove : 0;

            emaTrueRange = (trueRange(allQuotes.get(i), allQuotes.get(j)) - emaTrueRange) * expMovingAvgMult + emaTrueRange;
            emaPosDM = (posDM - emaPosDM) * expMovingAvgMult + emaPosDM;
            emaNegDM = (negDM - emaNegDM) * expMovingAvgMult + emaNegDM;

        }

        double posDI = emaPosDM / emaTrueRange;
        double negDI = emaNegDM / emaTrueRange;

        double directionIndex = Math.abs(posDI - negDI) / (posDI + negDI);

        //here we should calculate the EMA of the dx, but since our values are sort of discrete, it is
        //hard. Instead we just return the DX.

        return new ADXMetric(directionIndex);
    }

}
