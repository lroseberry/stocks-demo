package com.github.rjbx.demo.stocks.service;

import com.github.rjbx.demo.stocks.utility.DatabaseInitializationException;
import com.github.rjbx.demo.stocks.utility.DatabaseUtils;
import com.github.rjbx.demo.stocks.utility.HoursInterval;
import com.github.rjbx.demo.stocks.utility.StockServiceException;
import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Unit tests for the StockService class.
 * This class utilizes parameters defined with the {@code Parameterized} tag.
 * @author Bob Basmaji
 */
@Immutable
@RunWith(Parameterized.class)
public final class StockServiceTest {
    // fields of this class
    private StockService stockService;
    private DateTime startRange;
    private DateTime endRange;
    private String symbol;
    private HoursInterval interval;
    private static final int NUMBER_OF_DAYS = 100;

    /**
     * Constructs a new StockServiceTest instance with the specified parameter
     * @param stockService parameter defined with Parameterized tag
     */
    public StockServiceTest(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Sets up logic to be used by entire class
     * @throws DatabaseInitializationException
     */
    @BeforeClass
    public static final void setUpClass() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
    }

    /**
     * Sets up logic common to each test
     */
    @Before
    public final void setUp() {
        symbol = "AAPL";
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        interval = HoursInterval.DAY;
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote(String) does not equal parameter string",
                stockService.getQuote(symbol).getSymbol().equals(symbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote(String) equals lowercase-coverted parameter string",
                stockService.getQuote(symbol).getSymbol().equals(symbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote(String, DateTime, DateTime) does not equal parameter string",
                stockService.getQuote(symbol, startRange, endRange).get(0).getSymbol().equals(symbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote(String, DateTime, DateTime) equals lowercase-coverted parameter string",
                stockService.getQuote(symbol, startRange, endRange).get(0).getSymbol().equals(symbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote(String, DateTime, DateTime) does not equal parameter string",
                stockService.getQuote(symbol, startRange, endRange, interval).get(0).getSymbol().equals(symbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote(String, DateTime, DateTime) equals lowercase-coverted parameter string",
                stockService.getQuote(symbol, startRange, endRange, interval).get(0).getSymbol().equals(symbol.toLowerCase()));
    }

    /**
     * Defines parameters to be used in each test
     * @return collection of parameters
     * @throws StockServiceException
     */
    @Parameterized.Parameters
    public static final Collection<Object[]> instancesToTest() throws StockServiceException {
        return Arrays.asList(
            new Object[]{ServiceFactory.createStockService(ServiceType.BASIC)},
            new Object[]{ServiceFactory.createStockService(ServiceType.DATABASE)},
            new Object[]{ServiceFactory.createStockService(ServiceType.WEB)}
        );
    }
}
