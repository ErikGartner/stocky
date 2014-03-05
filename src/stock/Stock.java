package stock;

import metrics.StockMetric;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by erik on 04/03/14.
 */
public class Stock {

    private String symbol;
    private String name;
    private Map<String, List<StockMetric>> metrics;

    public Stock(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        metrics = new HashMap<String, List<StockMetric>>();
    }

    public Stock(String symbol) {
        this(symbol, symbol);
    }

    public void addMetric(StockMetric sm) {
        List<StockMetric> list = metrics.get(sm.getName());
        if (list == null) {
            list = new LinkedList<StockMetric>();
            metrics.put(sm.getName(), list);
        }
        list.add(sm);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        for (List<StockMetric> lsm : metrics.values()) {
            sb.append(lsm);
            sb.append('\n');
        }
        return sb.toString();
    }


}
