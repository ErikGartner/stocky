package stock;

import metrics.CloseMetric;
import metrics.OpenMetric;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by erik on 07/03/14.
 */
public class StockTrendTest {

    Quote low;
    Quote high;

    @Before
    public void setUp() throws Exception {
        low = new Quote(LocalDate.now());
        high = new Quote(LocalDate.now());

        low.addMetric(new OpenMetric(100));
        low.addMetric(new CloseMetric(50));

        high.addMetric(new OpenMetric(200));
        high.addMetric(new CloseMetric(300));
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
