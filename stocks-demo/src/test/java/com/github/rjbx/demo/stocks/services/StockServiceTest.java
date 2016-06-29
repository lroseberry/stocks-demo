package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.utilities.DatabaseConnectionException;
import com.github.rjbx.demo.stocks.utilities.DatabaseInitializationException;
import com.github.rjbx.demo.stocks.utilities.DatabaseUtils;
import com.github.rjbx.demo.stocks.utilities.StockServiceException;
import org.apache.http.annotation.Immutable;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.joda.time.DateTime;
import java.sql.SQLException;
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
        stockSymbol = "AAPL";
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        intervalEnum = BasicIntervalEnum.DAY;
    }

    /**
     * Tears down residual, potentially problematic logic after the class runs
     * @throws DatabaseInitializationException
     * @throws SQLException
     */
    @AfterClass
    public static final void tearDownClass() throws DatabaseConnectionException, SQLException {
        DatabaseUtils.getConnection().createStatement().executeUpdate("DROP TABLE quotes;");
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote(String) does not equal parameter string",
                stockService.getQuote(stockSymbol).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote(String) equals lowercase-coverted parameter string",
                stockService.getQuote(stockSymbol).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote(String, DateTime, DateTime) does not equal parameter string",
                stockService.getQuote(stockSymbol, startRange, endRange).get(0).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote(String, DateTime, DateTime) equals lowercase-coverted parameter string",
                stockService.getQuote(stockSymbol, startRange, endRange).get(0).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote(String, DateTime, DateTime) does not equal parameter string",
                stockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote(String, DateTime, DateTime) equals lowercase-coverted parameter string",
                stockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Defines parameters to be used in each test
     * @return collection of parameters
     */
    @Parameterized.Parameters
    public static final Collection<Object[]> instancesToTest() {
        return Arrays.asList(
            new Object[]{ServiceFactory.createStockService("basic")},
            new Object[]{ServiceFactory.createStockService("database")}
        );
    }
}
