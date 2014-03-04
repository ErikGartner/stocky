package stock;

/**
 * Created by erik on 04/03/14.
 */
public class Stock {

    private String symbol;
    private String name;

    public Stock(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public Stock(String symbol) {
        this(symbol, symbol);
    }


}
