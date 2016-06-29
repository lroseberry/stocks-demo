package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.StockQuote;
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
    private DateTime dateRecorded;
    private DateTime startRange;
    private DateTime endRange;
    private String stockSymbol;
    private BasicIntervalEnum intervalEnum;
    private static final int NUMBER_OF_DAYS = 15;

    /**
     * Sets up logic common to each test
     */
    @Before
    public final void setUp() {
        basicStockService = (BasicStockService) ServiceFactory.createStockService("basic");
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        dateRecorded = DateTime.now();
        stockSymbol = "APPL";
        intervalEnum = BasicIntervalEnum.DAY;
    }

    /**
     * Verifies that the return value has the correct stock symbol
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolPositive() {
        assertTrue("Stock symbol returned from return value of getQuote does not equal parameter string",
                basicStockService.getQuote(stockSymbol).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolNegative() {
        assertFalse("Stock symbol returned from return value of getQuote equals lowercase-coverted parameter string",
                basicStockService.getQuote(stockSymbol).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetQuoteSingleArgDateRecordedPositive() {
        assertTrue("Date recorded returned from return value of getQuote does not equal today's date",
                basicStockService.getQuote(stockSymbol).getDateRecorded().toString(StockQuote.DATE_PATTERN).equals(dateRecorded.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     */
    @Test
    public final void testGetQuoteSingleArgDateRecordedNegative() {
        dateRecorded = dateRecorded.plusDays(1);
        assertFalse("Date recorded returned from return value of getQuote equals tomorrow's date",
                basicStockService.getQuote(stockSymbol).getDateRecorded().toString(StockQuote.DATE_PATTERN).equals(dateRecorded.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has a correct stock symbol
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolPositive() {
        assertTrue("Stock symbol returned from return value of getQuote does not equal parameter string",
                basicStockService.getQuote(stockSymbol, startRange, endRange).get(0).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolNegative() {
        assertFalse("Stock symbol returned from return value of getQuote equals lowercase-coverted parameter string",
                basicStockService.getQuote(stockSymbol, startRange, endRange).get(0).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetQuoteTripleArgDateRecordedPositive() {
        assertTrue("Date recorded returned from first element of list returned by getQuote does not equal the parameter date",
                basicStockService.getQuote(stockSymbol, startRange, endRange).get(0).getDateRecorded().toString(StockQuote.DATE_PATTERN).equals(startRange.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     */
    @Test
    public final void testGetQuoteTripleArgDateRecordedNegative() {
        assertFalse("Date recorded returned from first element of list returned by getQuote equals the date of the parameter value of the end date",
                basicStockService.getQuote(stockSymbol, startRange, endRange).get(0).getDateRecorded().toString(StockQuote.DATE_PATTERN).equals(endRange.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has the correct stock symbol
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolPositive() {
        assertTrue("Stock symbol returned from return value of getQuote does not equal parameter string",
                basicStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolNegative() {
        assertFalse("Stock symbol returned from return value of getQuote equals lowercase-coverted parameter string",
                basicStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetQuoteQuadArgDateRecordedPositive() {
        assertTrue("Date recorded returned from first element of list returned by getQuote does not equal the parameter date",
                basicStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getDateRecorded().toString(StockQuote.DATE_PATTERN).equals(startRange.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     */
    @Test
    public final void testGetQuoteQuadArgDateRecordedNegative() {
        assertFalse("Date recorded returned from first element of list returned by getQuote equals the date of the parameter value of the end date",
                basicStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getDateRecorded().toString(StockQuote.DATE_PATTERN).equals(endRange.toString(StockQuote.DATE_PATTERN)));
    }
}
