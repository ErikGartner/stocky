package evaluator;

import analyser.StockPredictor;
import metrics.AdjustedCloseMetric;
import org.joda.time.LocalDate;
import stock.Quote;
import stock.Stock;
import stock.StockTrend;

import java.util.List;

/**
 * Created by erik on 30/09/14.
 */
public class SimpleEvaluator {

    private Stock stock;
    private LocalDate start;
    private StockPredictor predictor;
    private double performance = -1;

    public SimpleEvaluator(StockPredictor predictor, Stock stock, LocalDate start) {
        this.stock = stock;
        this.start = start;
        this.predictor = predictor;
    }

    /**
     * Calculates the performance resulting of following the predictor.
     *
     * @return The growth from buying/keeping every BEAR and selling every BULL day.
     */
    public double performance() {
        if (performance == -1) {
            calculatePerformance();
        }
        return performance;
    }

    private void calculatePerformance() {

        performance = 1;
        List<Quote> quoteList = stock.getQuotes();

        int startIndex = 0;
        while (quoteList.get(startIndex).getDate().isBefore(start)) {
            startIndex++;
        }

        double buy = -1;
        double lastClose = -1;
        while (startIndex < quoteList.size()) {
            List<Quote> corpus = quoteList.subList(0, startIndex);
            Stock s = new Stock(stock.getSymbol(), corpus);
            predictor.buildPredictor(s);
            StockTrend st = predictor.prediction();
            lastClose = corpus.get(corpus.size() - 1).getMetric(AdjustedCloseMetric.NAME).getValue();
            if (st == StockTrend.BEAR) {
                if (buy > 0) {
                    performance *= (lastClose / buy);
                    buy = -1;
                }
            } else if (st == StockTrend.BULL) {
                if (buy < 0) {
                    buy = lastClose;
                }
            }
            startIndex++;
        }

        if (buy > 0) {
            performance *= lastClose / buy;
            buy = -1;
        }

    }


}
