package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.DatabaseStockSymbol;
import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.utilities.*;
import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import org.joda.time.DateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the DatabaseStockService class.
 * @author Bob Basmaji
 */
@Immutable
public final class DatabaseStockServiceTest {
    // fields of this class
    private DatabaseStockService databaseStockService;
    private DateTime startRange;
    private DateTime endRange;
    private DatabaseStockSymbol stockSymbol;
    private HoursInterval interval;
    private static final int NUMBER_OF_DAYS = 100;

    /**
     * Sets up logic common to each test
     * @throws DatabaseInitializationException
     * @throws DatabaseConnectionException
     * @throws SQLException
     * @throws StockServiceException
     */
    @Before
    public final void setUp() throws DatabaseConnectionException, SQLException, DatabaseInitializationException , StockServiceException{
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        databaseStockService = (DatabaseStockService) ServiceFactory.createStockService(ServiceType.DATABASE);
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        stockSymbol = new DatabaseStockSymbol("AAPL");
        interval = HoursInterval.WEEK;
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote does not equal stock symbol initialized with parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol()).getSymbol().equals(stockSymbol.getSymbol()));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote equals stock symbol initialized with lowercase-coverted parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol()).getSymbol().equals(stockSymbol.getSymbol().toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgtimePositive() throws StockServiceException {
        assertTrue("Date recorded returned from return value of getQuote does not equal the last element returned by the database query",
            databaseStockService.getQuote(stockSymbol.getSymbol()) instanceof StockQuote);
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteSingleArgtimeNegative() throws StockServiceException {
        databaseStockService.getQuote("BLARG");
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote does not equal stock symbol initialized with parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange).get(0).getSymbol()
                        .equals(stockSymbol.getSymbol()));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote equals stock symbol initialized with lowercase-coverted parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange).get(0).getSymbol().equals(stockSymbol.getSymbol().toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgtimePositive() throws StockServiceException {
        assertTrue("Date recorded returned from first element of list returned by getQuote does not equal the first element returned by the database query",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange).get(0) instanceof StockQuote);
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteTripleArgtimeNegative() throws StockServiceException {
        databaseStockService.getQuote(stockSymbol.getSymbol(), endRange, startRange);
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote does not equal stock symbol initialized with parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange, interval).get(0).getSymbol()
                        .equals(stockSymbol.getSymbol()));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote equals stock symbol initialized with lowercase-coverted parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange, interval).get(0).getSymbol()
                        .equals(stockSymbol.getSymbol().toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuotesQuadArgtimePositive() throws StockServiceException {
        assertTrue("Date recorded returned from first element of list returned by getQuote does not equal the first element returned by the database query",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange, interval).get(0) instanceof StockQuote);
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void timeNegative() throws StockServiceException {
                databaseStockService.getQuote(stockSymbol.getSymbol(), endRange, startRange, interval);
    }
}
