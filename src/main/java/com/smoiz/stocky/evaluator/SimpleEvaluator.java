package com.smoiz.stocky.evaluator;

import com.smoiz.stocky.Stocky;
import com.smoiz.stocky.analyser.StockPredictor;
import com.smoiz.stocky.metrics.AdjustedCloseMetric;
import org.joda.time.LocalDate;
import com.smoiz.stocky.stock.Quote;
import com.smoiz.stocky.stock.Stock;
import com.smoiz.stocky.stock.StockTrend;

import java.util.List;

/**
 * Created by erik on 30/09/14.
 */
public class SimpleEvaluator {

    private Stock stock;
    private LocalDate start;
    private StockPredictor predictor;
    private double performance = -1;
    private int periodSize;
    private String detailedPerf;

    public SimpleEvaluator(StockPredictor predictor, Stock stock, LocalDate start) {
        this.stock = stock;
        this.start = start;
        this.predictor = predictor;
        this.periodSize = periodSize;
    }

    /**
     * Calculates the performance resulting of following the predictor.
     *
     * @return The growth from buying/keeping every BULL and selling every BEAR day.
     */
    public double performance() {
        if (performance == -1) {
            calculatePerformance();
        }
        return performance;
    }

    public String detailedPerformance(){

        if(detailedPerf != null){
            return detailedPerf;
        }

        performance();

        List<Quote> quoteList = stock.getQuotes();
        int startIndex = 0;
        while (quoteList.get(startIndex).getDate().isBefore(start)) {
            startIndex++;
        }

        double buy = quoteList.get(startIndex).getMetric(AdjustedCloseMetric.NAME).getValue();
        double close = quoteList.get(quoteList.size()-1).getMetric(AdjustedCloseMetric.NAME).getValue();
        double perf = close / buy;

        detailedPerf = String.format("%s on %s yielded %f when following advice as compared to %f", predictor.toString(),
                stock.getName(), performance, perf);
        return detailedPerf;

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
            //predictor.buildPredictor(s
            StockTrend st = predictor.prediction();
            Stocky.dbgf("%s: %s -> %s", s.getSymbol(), corpus.get(corpus.size() - 1).getDate().toString(), st.name());
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
            startIndex += periodSize;
        }

        if (buy > 0) {
            performance *= lastClose / buy;
            buy = -1;
        }

    }


}
