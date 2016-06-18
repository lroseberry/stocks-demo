package com.github.rjbx.demo.stocks;

import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the BasicStockService class.
 * @author Bob Basmaji
 */
public final class BasicStockServiceTest {
    // fields of this class
    private BasicStockService basicStockService;
    private Calendar dateRecorded;
    private Calendar startRange;
    private Calendar endRange;
    private String stockSymbol;
    private BasicIntervalEnum intervalEnum;
    private static final int NUMBER_OF_DAYS = 15;

    /**
     * Sets up logic common to each test
     */
    @Before
    public final void setup() {
        basicStockService = (BasicStockService) StockServiceFactory.createStockService();
        startRange = Calendar.getInstance();
        endRange = Calendar.getInstance();
        endRange.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        dateRecorded = Calendar.getInstance();
        stockSymbol = "APPL";
        intervalEnum = BasicIntervalEnum.DAY;
    }

    /**
     * Verifies that the return value has the correct stock symbol
     */
    @Test
    public final void testGetSingleQuoteStockSymbolPositive() {
        assertTrue("Stock symbol returned from return value of getQuote(String) does not equal parameter string",
                basicStockService.getQuote(stockSymbol).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     */
    @Test
    public final void testGetSingleQuoteStockSymbolNegative() {
        assertFalse("Stock symbol returned from return value of getQuote(String) equals lowercase-coverted parameter string",
                basicStockService.getQuote(stockSymbol).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetSingleQuoteDateRecordedPositive() {
        assertTrue("Date recorded returned from return value of getQuote(String) does not equal today's date",
                basicStockService.getQuote(stockSymbol).getDateRecorded().getTime().toString().equals(dateRecorded.getTime().toString()));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     */
    @Test
    public final void testGetSingleQuoteDateRecordedNegative() {
        dateRecorded.add(Calendar.DAY_OF_YEAR, 1);
        assertFalse("Date recorded returned from return value of getQuote(String) equals tomorrow's date",
                basicStockService.getQuote(stockSymbol).getDateRecorded().getTime().toString().equals(dateRecorded.getTime().toString()));
    }

    /**
     * Verifies that the return value has the correct stock symbol
     */
    @Test
    public final void testGetMultipleQuotesStockSymbolPositive() {
        assertTrue("Stock symbol returned from return value of getQuote(String, Calendar, Calendar) does not equal parameter string",
                basicStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     */
    @Test
    public final void testGetMultipleQuotesStockSymbolNegative() {
        assertFalse("Stock symbol returned from return value of getQuote(String, Calendar, Calendar) equals lowercase-coverted parameter string",
                basicStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetMultipleQuotesDateRecordedPositive() {
        assertTrue("Date recorded returned from first element of list returned by getQuote(String, Calendar, Calendar) does not equal the parameter date",
                basicStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getDateRecorded().getTime().toString().equals(startRange.getTime().toString()));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     */
    @Test
    public final void testGetMultipleQuotesDateRecordedNegative() {
        assertFalse("Date recorded returned from first element of list returned by getQuote(String, Calendar, Calendar) equals the date of the parameter value of the end date",
                basicStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getDateRecorded().getTime().toString().equals(endRange.getTime().toString()));
    }
}
