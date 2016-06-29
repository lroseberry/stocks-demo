package com.github.rjbx.demo.stocks.application;

import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.services.*;
import com.github.rjbx.demo.stocks.utilities.DatabaseConnectionException;
import com.github.rjbx.demo.stocks.utilities.DatabaseInitializationException;
import com.github.rjbx.demo.stocks.utilities.DatabaseUtils;
import com.github.rjbx.demo.stocks.utilities.StockServiceException;
import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;
import org.junit.*;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.sql.SQLException;
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
@Immutable
public final class StockTickerTest {
    // fields of this class
    private StockService stockServiceMock;
    private DateTime startRange;
    private DateTime endRange;
    private String stockSymbol;
    private BigDecimal expectedPrice;
    private StockTicker stockTicker;
    private BasicIntervalEnum intervalEnum;
    private static final int NUMBER_OF_DAYS = 15;

    /**
     * Sets up the logic common to each test
     * @throws StockServiceException
     */
    @Before
    public final void setUp() throws DatabaseInitializationException, StockServiceException {
        // initialize database
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);

        // mock the external dependency
        stockServiceMock = Mockito.mock(StockService.class);

        // create the data we expect the service to return
        stockSymbol = "AAPL";
        startRange = DateTime.now().minusDays(NUMBER_OF_DAYS);
        endRange = DateTime.now();
        expectedPrice = new BigDecimal(100);
        intervalEnum = BasicIntervalEnum.WEEK;

        // tell the mock service to return a StockQuote with a specific price and symbol when getQuote() is called
        when(stockServiceMock.getQuote(any(String.class)))
                .thenReturn(new StockQuote(DateTime.now(), expectedPrice, stockSymbol));

        // create the StickTicker instance to test
        stockTicker = new StockTicker(stockServiceMock);
    }

    /**
     * Tears down residual, potentially problematic logic after each test runs
     * @throws DatabaseConnectionException
     * @throws SQLException
     */
    @After
    public final void tearDown() throws DatabaseConnectionException, SQLException {
        DatabaseUtils.getConnection().createStatement().executeUpdate("DROP TABLE quotes;");
    }

    /**
     * Verifies that the return value has the correct date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistoryDayRecordedPositive() throws StockServiceException {
        // compares method return values with expected results
        List<StockQuote> stockHistory = stockTicker.getStockHistory(stockSymbol, startRange, endRange, intervalEnum);
        DateTime dateRecorded = new DateTime(startRange);
        for (StockQuote quote : stockHistory) {
            assertTrue("Date recorded returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getDateRecorded().equals(dateRecorded));
            dateRecorded.plusDays(intervalEnum.amount());
        }
    }

    /**
     * Verifies that the return value as an incorrect date recorded
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistoryDayRecordedNegative() throws StockServiceException {
        // compares method return values with unexpected results
        List<StockQuote> stockHistory = stockTicker.getStockHistory(stockSymbol, endRange, startRange, intervalEnum);
        DateTime dateRecorded = new DateTime(startRange);
        for (StockQuote quote : stockHistory) {
            assertFalse("Date recorded returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getDateRecorded().equals(dateRecorded));
            dateRecorded.plusDays(intervalEnum.amount());
        }
    }

    /**
     * Verifies that the return value has the correct stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistoryStockSymbolPositive() throws StockServiceException {
        // compares method return values with expected results
        List<StockQuote> stockHistory = stockTicker.getStockHistory(stockSymbol, startRange, endRange, intervalEnum);
        for (StockQuote quote : stockHistory) {
            assertTrue("Stock symbol returned from return value of getStockHistory() does not equal the parameter string",
                    quote.getStockSymbol().equals(stockSymbol));
        }
    }

    /**
     * Verifies that the return value as an incorrect stock symbol
     * @throws StockServiceException
     */
    @Test
    public final void testGetStockHistoryStockSymbolNegative() throws StockServiceException {
        // compares method return values with unexpected results
        List<StockQuote> stockHistory = stockTicker.getStockHistory(stockSymbol, startRange, endRange, intervalEnum);
        for (StockQuote quote : stockHistory) {
            assertFalse("Stock symbol returned from return value of getStockHistory() equals lowercase-converted parameter string",
                    quote.getStockSymbol().equals(stockSymbol.toLowerCase()));
        }
    }

    /**
     * Verifies that, when passed valid arguments, the main function executes without generating NullPointerException
     * @throws StockServiceException
     */
    @Test
    public void testMainPositive() throws StockServiceException {
        String start = startRange.toString(StockQuote.DATE_PATTERN);
        String end = endRange.toString(StockQuote.DATE_PATTERN);
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
     * @throws StockServiceException
     */
    @Test(expected = NullPointerException.class)
    public void testMainNegative() throws StockServiceException {
        StockTicker.main(null);
    }
}
