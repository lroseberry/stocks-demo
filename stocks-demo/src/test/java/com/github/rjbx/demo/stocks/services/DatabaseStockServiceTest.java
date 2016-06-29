package com.github.rjbx.demo.stocks.services;

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
    private DateTime dateRecorded;
    private DateTime startRange;
    private DateTime endRange;
    private String stockSymbol;
    private BasicIntervalEnum intervalEnum;
    private static Connection dbConnection;
    private static final int NUMBER_OF_DAYS = 15;
    private static String queryString;
    private static DateTime firstDateRecorded;
    private static DateTime lastDateRecorded;
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
     * @throws DatabaseConnectionException
     * @throws SQLException
     */
    @Before
    public final void setUp() throws DatabaseConnectionException, SQLException {
        databaseStockService = (DatabaseStockService) ServiceFactory.createStockService("database");
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        dateRecorded = DateTime.now();
        stockSymbol = "AAPL";
        queryString = "select * from quotes where symbol = '" + stockSymbol + "' and time between '" + startRange.toString(StockQuote.DATE_PATTERN) + "' and '" + endRange.toString(StockQuote.DATE_PATTERN) + "'";
        ResultSet resultSet;
        dbConnection = DatabaseUtils.getConnection();
        resultSet = dbConnection.createStatement().executeQuery(queryString);
        resultSet.next();
        firstDateRecorded = new DateTime(resultSet.getTimestamp("time"));
        resultSet.last();
        lastDateRecorded = new DateTime(resultSet.getTimestamp("time"));
        intervalEnum = BasicIntervalEnum.DAY;
    }

    /**
     * Tears down residual, potentially problematic logic after the class runs
     * @throws SQLException
     */
    @AfterClass
    public static final void tearDownClass() throws SQLException {
        dbConnection.createStatement().executeUpdate("DROP TABLE quotes;");
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote does not equal parameter string",
                databaseStockService.getQuote(stockSymbol).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote equals lowercase-coverted parameter string",
                databaseStockService.getQuote(stockSymbol).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgDateRecordedPositive() throws StockServiceException {
        assertTrue("Date recorded returned from return value of getQuote does not equal the last element returned by the database query",
            databaseStockService.getQuote(stockSymbol).getDateRecorded().toString(StockQuote.DATE_PATTERN)
                    .equals(lastDateRecorded.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteSingleArgDateRecordedNegative() throws StockServiceException {
        dateRecorded = dateRecorded.plusDays(1);
        assertFalse("Date recorded returned from return value of getQuote equals the first element returned by the database query",
            databaseStockService.getQuote(stockSymbol).getDateRecorded().toString(StockQuote.DATE_PATTERN)
                    .equals(firstDateRecorded.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote does not equal parameter string",
                databaseStockService.getQuote(stockSymbol, startRange, endRange).get(0).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote equals lowercase-coverted parameter string",
                databaseStockService.getQuote(stockSymbol, startRange, endRange).get(0).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgDateRecordedPositive() throws StockServiceException {
        assertTrue("Date recorded returned from first element of list returned by getQuote does not equal the first element returned by the database query",
                databaseStockService.getQuote(stockSymbol, startRange, endRange).get(0).getDateRecorded().toString(StockQuote.DATE_PATTERN)
                        .equals(firstDateRecorded.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteTripleArgDateRecordedNegative() throws StockServiceException {
        assertFalse("Date recorded returned from second element of list returned by getQuote equals the first element returned by the database query",
                databaseStockService.getQuote(stockSymbol, startRange, endRange).get(1).getDateRecorded().toString(StockQuote.DATE_PATTERN)
                        .equals(firstDateRecorded.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from return value of getQuote does not equal parameter string",
                databaseStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that the return value has an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Stock symbol returned from return value of getQuote equals lowercase-coverted parameter string",
                databaseStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the return value has the correct date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuotesQuadArgDateRecordedPositive() throws StockServiceException {
        assertTrue("Date recorded returned from first element of list returned by getQuote does not equal the first element returned by the database query",
                databaseStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(0).getDateRecorded().toString(StockQuote.DATE_PATTERN)
                        .equals(firstDateRecorded.toString(StockQuote.DATE_PATTERN)));
    }

    /**
     * Verifies that the return value has an incorrect date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetQuoteQuadArgDateRecordedNegative() throws StockServiceException {
        assertFalse("Date recorded returned from second element of list returned by getQuote equals the first element returned by the database query",
                databaseStockService.getQuote(stockSymbol, startRange, endRange, intervalEnum).get(1).getDateRecorded()
                        .equals(firstDateRecorded.toString(StockQuote.DATE_PATTERN)));
    }
}
