package com.github.rjbx.demo.stocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Unit tests for the StockService class.
 * This class utilzes parameters defined with the {@code Parameterized} tag.
 * @author Bob Basmaji
 */
@RunWith(Parameterized.class)
public final class StockServiceTest {
    // fields of this class
    private StockService stockService;
    private Calendar dateRecorded;
    private Calendar startRange;
    private Calendar endRange;
    private String stockSymbol;
    private BasicIntervalEnum intervalEnum;
    private static final int NUMBER_OF_DAYS = 15;

    /**
     * Constructs a new StockServiceTest instance with the specified parameter
     * @param stockService parameter defined with Parameterized tag
     */
    public StockServiceTest(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Sets up logic common to each test
     */
    @Before
    public final void setup() {
        stockSymbol = "APPL";
        startRange = Calendar.getInstance();
        endRange = Calendar.getInstance();
        endRange.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        dateRecorded = Calendar.getInstance();
        intervalEnum = BasicIntervalEnum.DAY;
    }

    /**
     * Verifies that the return value has the correct stock symbol
     */
    @Test
    public final void testGetSingleQuoteStockSymbolPositive() {
        assertTrue("Stock symbol returned from return value of getQuote(String) does not equal parameter string",
                stockService.getQuote(stockSymbol).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     */
    @Test
    public final void testGetSingleQuoteStockSymbolNegative() {
        assertFalse("Stock symbol returned from return value of getQuote(String) equals lowercase-coverted parameter string",
                stockService.getQuote(stockSymbol).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetSingleQuoteDateRecordedPositive() {
        assertTrue("Date recorded returned from return value of getQuote(String) does not equal today's date",
                stockService.getQuote(stockSymbol).getDateRecorded().getTime().toString().equals(dateRecorded.getTime().toString()));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     */
    @Test
    public final void testGetSingleQuoteDateRecordedNegative() {
        dateRecorded.add(Calendar.DAY_OF_YEAR, 1);
        assertFalse("Date recorded returned from return value of getQuote(String) equals tomorrow's date",
                stockService.getQuote(stockSymbol).getDateRecorded().getTime().toString().equals(dateRecorded.getTime().toString()));
    }

    /**
     * Verifies that the return value has the correct stock symbol
     */
    @Test
    public final void testGetMultipleQuotesStockSymbolPositive() {
        assertTrue("Stock symbol returned from return value of getQuote(String, Calendar, Calendar) does not equal parameter string",
                stockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     */
    @Test
    public final void testGetMultipleQuotesStockSymbolNegative() {
        assertFalse("Stock symbol returned from return value of getQuote(String, Calendar, Calendar) equals lowercase-coverted parameter string",
                stockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     */
    @Test
    public final void testGetMultipleQuotesDateRecordedPositive() {
        assertTrue("Date recorded returned from first element of list returned by getQuote(String, Calendar, Calendar) does not equal parameter date",
                stockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getDateRecorded().getTime().toString().equals(startRange.getTime().toString()));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     */
    @Test
    public final void testGetMultipleQuotesDateRecordedNegative() {
        assertFalse("Date recorded returned from first element of list returned by getQuote(String, Calendar, Calendar) equals the date of parameter value of end date",
                stockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getDateRecorded().getTime().toString().equals(endRange.getTime().toString()));
    }

    /**
     * Defines parameters to be used in each test
     * @return collection of parameters
     */
    @Parameterized.Parameters
    public static final Collection<Object[]> instancesToTest() {
        return Arrays.asList(
            new Object[]{StockServiceFactory.createStockService()},
            new Object[]{StockServiceFactory.createStockService()} // placeholder value
        );
    }
}
