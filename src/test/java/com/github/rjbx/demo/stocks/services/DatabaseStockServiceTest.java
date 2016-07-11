package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.DatabaseStockQuote;
import com.github.rjbx.demo.stocks.model.DatabaseStockSymbol;
import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.utilities.DatabaseConnectionException;
import com.github.rjbx.demo.stocks.utilities.DatabaseInitializationException;
import com.github.rjbx.demo.stocks.utilities.DatabaseUtils;
import com.github.rjbx.demo.stocks.utilities.StockServiceException;
import org.apache.http.annotation.Immutable;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    private BasicIntervalEnum intervalEnum;
    private static final int NUMBER_OF_DAYS = 100;

    /**
     * Sets up logic common to each test
     * @throws DatabaseInitializationException
     * @throws DatabaseConnectionException
     * @throws SQLException
     */
    @Before
    public final void setUp() throws DatabaseConnectionException, SQLException, DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        databaseStockService = (DatabaseStockService) ServiceFactory.createStockService("database");
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        stockSymbol = new DatabaseStockSymbol("AAPL");
        intervalEnum = BasicIntervalEnum.WEEK;
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote does not equal stock symbol initialized with parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol()).getStockSymbol().equals(stockSymbol.getSymbol()));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote equals stock symbol initialized with lowercase-coverted parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol()).getStockSymbol().equals(stockSymbol.getSymbol().toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgDateRecordedPositive() throws StockServiceException {
        assertTrue("Date recorded returned from return value of getQuote does not equal the last element returned by the database query",
            databaseStockService.getQuote(stockSymbol.getSymbol()) instanceof StockQuote);
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteSingleArgDateRecordedNegative() throws StockServiceException {
        databaseStockService.getQuote("BLARG");
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote does not equal stock symbol initialized with parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange).get(0).getStockSymbol()
                        .equals(stockSymbol.getSymbol()));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote equals stock symbol initialized with lowercase-coverted parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange).get(0).getStockSymbol().equals(stockSymbol.getSymbol().toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgDateRecordedPositive() throws StockServiceException {
        assertTrue("Date recorded returned from first element of list returned by getQuote does not equal the first element returned by the database query",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange).get(0) instanceof StockQuote);
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void testGetQuoteTripleArgDateRecordedNegative() throws StockServiceException {
        databaseStockService.getQuote(stockSymbol.getSymbol(), endRange, startRange);
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote does not equal stock symbol initialized with parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange, intervalEnum).get(0).getStockSymbol()
                        .equals(stockSymbol.getSymbol()));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote equals stock symbol initialized with lowercase-coverted parameter string",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange, intervalEnum).get(0).getStockSymbol()
                        .equals(stockSymbol.getSymbol().toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuotesQuadArgDateRecordedPositive() throws StockServiceException {
        assertTrue("Date recorded returned from first element of list returned by getQuote does not equal the first element returned by the database query",
                databaseStockService.getQuote(stockSymbol.getSymbol(), startRange, endRange, intervalEnum).get(0) instanceof StockQuote);
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     * @throws StockServiceException
     */
    @Test(expected=StockServiceException.class)
    public final void DateRecordedNegative() throws StockServiceException {
                databaseStockService.getQuote(stockSymbol.getSymbol(), endRange, startRange, intervalEnum);
    }
}
