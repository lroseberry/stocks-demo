package com.github.rjbx.demo.stocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the Stockticker class.
 * This class mocks the external dependency (StockService) relied upon by
 * the StockTicker class.
 * @author Bob Basmaji
 */
public final class StockTickerTest {
    // fields of this class
    private StockService stockServiceMock;
    private Calendar startRange;
    private Calendar endRange;
    private String stockSymbol;
    private BigDecimal expectedPrice;
    private StockTicker stockTicker;
    private BasicIntervalEnum intervalEnum;
    private static final int NUMBER_OF_DAYS = 15;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setup() {
        // mock the external dependency
        stockServiceMock = Mockito.mock(StockService.class);

        // create the data we expect the service to return
        stockSymbol = "APPL";
        startRange = Calendar.getInstance();
        endRange = Calendar.getInstance();
        endRange.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        expectedPrice = new BigDecimal(100);
        intervalEnum = BasicIntervalEnum.DAY;

        // tell the mock service to return a StockQuote with a specific price and symbol when getQuote() is called
        when(stockServiceMock.getQuote(any(String.class)))
                .thenReturn(new StockQuote(Calendar.getInstance(), expectedPrice, stockSymbol));

        // create the StickTicker instance to test
        stockTicker = new StockTicker(stockServiceMock);
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetStockHistoryDayRecordedPositive() {
        // compares method return values with expected results
        List<StockQuote> stockHistory = stockTicker.getStockHistory(stockSymbol, startRange, endRange, intervalEnum);
        Calendar dateRecorded = Calendar.getInstance();
        dateRecorded.setTime(startRange.getTime());
        for (StockQuote quote : stockHistory) {
            assertTrue("Date recorded returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getDateRecorded().equals(dateRecorded));
            dateRecorded.add(intervalEnum.unit(), intervalEnum.amount());
        }
    }

    /**
     * Verifies that the return value as an incorrect date recorded
     */
    @Test
    public final void testGetStockHistoryDayRecordedNegative() {
        // compares method return values with unexpected results
        List<StockQuote> stockHistory = stockTicker.getStockHistory(stockSymbol, endRange, startRange, intervalEnum);
        Calendar dateRecorded = Calendar.getInstance();
        dateRecorded.setTime(startRange.getTime());
        for (StockQuote quote : stockHistory) {
            assertFalse("Date recorded returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getDateRecorded().equals(dateRecorded));
            dateRecorded.add(intervalEnum.unit(), intervalEnum.amount());
        }
    }

    /**
     * Verifies that the return value has the correct stock symbol
     */
    @Test
    public final void testGetStockHistoryStockSymbolPositive() {
        // compares method return values with expected results
        List<StockQuote> stockHistory = stockTicker.getStockHistory(stockSymbol, startRange, endRange, intervalEnum);
        for (StockQuote quote : stockHistory) {
            assertTrue("Stock symbol returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getStockSymbol().equals(stockSymbol));
        }
    }

    /**
     * Verifies that the return value as an incorrect stock symbol
     */
    @Test
    public final void testGetStockHistoryStockSymbolNegative() {
        // compares method return values with unexpected results
        List<StockQuote> stockHistory = stockTicker.getStockHistory(stockSymbol, startRange, endRange, intervalEnum);
        for (StockQuote quote : stockHistory) {
            assertFalse("Stock symbol returned from return value of getStockHistory() equals lowercase-converted parameter string",
                    quote.getStockSymbol().equals(stockSymbol.toLowerCase()));
        }
    }

    /**
     * Verifies that, when passed valid arguments, the main function executes without generating NullPointerException
     */
    @Test
    public void testMainPositive() {
        SimpleDateFormat formatter = StockQuote.getDateFormatter();
        String start = formatter.format(startRange.getTime());
        String end = formatter.format(endRange.getTime());
        String interval = intervalEnum.toString();
        String[] args = { stockSymbol, start, end, interval };
        try {
            StockTicker.main(args);
        } catch (NullPointerException e) {
            fail("Passing valid arguments to main generates a NullPointerException");
        }
    }

    /**
     * Verifies that the main function, when passed an invalid argument, generates a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void testMainNegative() {
        StockTicker.main(null);
    }
}
