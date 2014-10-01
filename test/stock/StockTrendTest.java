package stock;

import metrics.CloseMetric;
import metrics.OpenMetric;
import metrics.StockMetric;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by erik on 07/03/14.
 */
public class StockTrendTest {


    Quote low;
    Quote high;

    @Before
    public void setUp() throws Exception {

        List<StockMetric> lowsMetric = new ArrayList<StockMetric>();
        List<StockMetric> highsMetric = new ArrayList<StockMetric>();

        lowsMetric.add(new OpenMetric(100));
        lowsMetric.add(new CloseMetric(50));
        low = new Quote(LocalDate.now(), lowsMetric);

        highsMetric.add(new OpenMetric(200));
        highsMetric.add(new CloseMetric(300));
        high = new Quote(LocalDate.now(), highsMetric);

    }

    @Test
    public void testTrendFromOpenClose() throws Exception {
        assertEquals(StockTrend.BEAR, StockTrend.trendFromOpenClose(low));
        assertEquals(StockTrend.BULL, StockTrend.trendFromOpenClose(high));
    }

    @Test
    public void testTrendFromCloseClose() throws Exception {
        assertEquals(StockTrend.BEAR, StockTrend.trendFromCloseClose(high, low));
        assertEquals(StockTrend.BULL, StockTrend.trendFromCloseClose(low, high));
    }

    @Test
    public void trendFromNQuotesAverage() throws Exception {

    }

}
