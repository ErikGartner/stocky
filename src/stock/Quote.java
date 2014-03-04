package stock;

import org.joda.time.LocalDate;

/**
 * Created by erik on 04/03/14.
 */
public class Quote {

    private LocalDate date;
    private double openPrice, closePrice, high, low;
    private int volume;

    public Quote(LocalDate d, double o, double c, double h, double l, int v) {
        date = d;
        openPrice = o;
        closePrice = c;
        high = h;
        low = l;
        volume = v;
    }

    public String toString() {
        return String.format("%s : %f %f %f %f %d", date, openPrice, low, high, closePrice, volume);
    }


}
