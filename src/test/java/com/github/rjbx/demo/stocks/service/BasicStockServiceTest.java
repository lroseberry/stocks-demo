package com.github.rjbx.demo.stocks.service;

import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.utility.HoursInterval;
import com.github.rjbx.demo.stocks.utility.StockServiceException;
import org.joda.time.DateTime;
import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the BasicStockService class.
 * @author Bob Basmaji
 */
@Immutable
public final class BasicStockServiceTest {
    // fields of this class
    private BasicStockService basicStockService;
    private DateTime time;
    private DateTime startRange;
    private DateTime endRange;
    private String symbol;
    private HoursInterval interval;
    private static final int NUMBER_OF_DAYS = 15;

    /**
     * Sets up logic common to each test
     * @throws StockServiceException
     */
    @Before
    public final void setUp() throws StockServiceException{
        basicStockService = (BasicStockService) ServiceFactory.createStockService(ServiceType.BASIC);
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        time = DateTime.now();
        symbol = "APPL";
        interval = HoursInterval.DAY;
    }

    /**
     * Verifies that the return value has the correct stock symbol
     */
    @Test
    public final void testGetQuoteSingleArgSymbolPositive() {
        assertTrue("Stock symbol returned from return value of getQuote does not equal parameter string",
                basicStockService.getQuote(symbol).getSymbol().equals(symbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     */
    @Test
    public final void testGetQuoteSingleArgSymbolNegative() {
        assertFalse("Stock symbol returned from return value of getQuote equals lowercase-coverted parameter string",
                basicStockService.getQuote(symbol).getSymbol().equals(symbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetQuoteSingleArgTimePositive() {
        assertTrue("Date recorded returned from return value of getQuote does not equal today's date",
                basicStockService.getQuote(symbol).getTime().toString(StockQuote.DATE_PATTERN).equals(time.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     */
    @Test
    public final void testGetQuoteSingleArgTimeNegative() {
        time = time.plusDays(1);
        assertFalse("Date recorded returned from return value of getQuote equals tomorrow's date",
                basicStockService.getQuote(symbol).getTime().toString(StockQuote.DATE_PATTERN).equals(time.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has a correct stock symbol
     */
    @Test
    public final void testGetQuoteTripleArgSymbolPositive() {
        assertTrue("Stock symbol returned from return value of getQuote does not equal parameter string",
                basicStockService.getQuote(symbol, startRange, endRange).get(0).getSymbol().equals(symbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     */
    @Test
    public final void testGetQuoteTripleArgSymbolNegative() {
        assertFalse("Stock symbol returned from return value of getQuote equals lowercase-coverted parameter string",
                basicStockService.getQuote(symbol, startRange, endRange).get(0).getSymbol().equals(symbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetQuoteTripleArgTimePositive() {
        assertTrue("Date recorded returned from first element of list returned by getQuote does not equal the parameter date",
                basicStockService.getQuote(symbol, startRange, endRange).get(0).getTime().toString(StockQuote.DATE_PATTERN).equals(startRange.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     */
    @Test
    public final void testGetQuoteTripleArgTimeNegative() {
        assertFalse("Date recorded returned from first element of list returned by getQuote equals the date of the parameter value of the end date",
                basicStockService.getQuote(symbol, startRange, endRange).get(0).getTime().toString(StockQuote.DATE_PATTERN).equals(endRange.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has the correct stock symbol
     */
    @Test
    public final void testGetQuoteQuadArgSymbolPositive() {
        assertTrue("Stock symbol returned from return value of getQuote does not equal parameter string",
                basicStockService.getQuote(symbol, startRange, endRange, interval).get(0).getSymbol().equals(symbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     */
    @Test
    public final void testGetQuoteQuadArgSymbolNegative() {
        assertFalse("Stock symbol returned from return value of getQuote equals lowercase-coverted parameter string",
                basicStockService.getQuote(symbol, startRange, endRange, interval).get(0).getSymbol().equals(symbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetQuoteQuadArgTimePositive() {
        assertTrue("Date recorded returned from first element of list returned by getQuote does not equal the parameter date",
                basicStockService.getQuote(symbol, startRange, endRange, interval).get(0).getTime().toString(StockQuote.DATE_PATTERN).equals(startRange.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     */
    @Test
    public final void testGetQuoteQuadArgTimeNegative() {
        assertFalse("Date recorded returned from first element of list returned by getQuote equals the date of the parameter value of the end date",
                basicStockService.getQuote(symbol, startRange, endRange, interval).get(0).getTime().toString(StockQuote.DATE_PATTERN).equals(endRange.toString(StockQuote.DATE_PATTERN)));
    }
}
