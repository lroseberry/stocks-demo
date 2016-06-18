package com.github.rjbx.demo.stocks;

import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the StockQuote class.
 * @author Bob Basmaji
 */
public final class StockQuoteTest {
    // fields of this class
    private String stockSymbol;
    private BigDecimal stockPrice;
    private Calendar dateRecorded;
    private StockQuote stockQuote;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setup() {
        // initialize field variables
        stockSymbol = "APPL";
        stockPrice = new BigDecimal(100);
        dateRecorded = Calendar.getInstance();
        stockQuote = new StockQuote(dateRecorded, stockPrice, stockSymbol);
    }

    /**
     * Verifies that a RuntimeException is thrown when a StockQuote object is improperly constructed
     */
    @Test(expected = RuntimeException.class)
    public final void testStockQuoteConstructionDateRecorded() {
        // pass a null Date argument into StockQuote constructor
        Calendar nullDate = null;
        new StockQuote(nullDate, stockPrice, stockSymbol);
    }

    /**
     * Verifies that a RuntimeException is thrown when a StockQuote object is improperly constructed
     */
    @Test(expected = RuntimeException.class)
    public final void testStockQuoteConstructionStockPrice() {
        // pass a null BigDecimal argument into StockQuote constructor
        BigDecimal nullNumber = null;
        new StockQuote(dateRecorded, nullNumber, stockSymbol);
    }

    /**
     * Verifies that a RuntimeException is thrown when a StockQuote object is improperly constructed
     */
    @Test(expected = RuntimeException.class)
    public final void testStockQuoteConstructionStockSymbol() {
        // pass a null BigDecimal argument into StockQuote constructor
        String nullString = null;
        new StockQuote(dateRecorded, stockPrice, nullString);
    }

    /**
     * Verifies that the correct stock symbol is returned
     */
    @Test
    public final void testGetStockSymbolPositive() {
        // compares method return value with expected result
        assertTrue("Value returned from getStockSymbol() equals parameter string",
                stockQuote.getStockSymbol().equals(stockSymbol));
    }

    /**
     * Verifies that an incorrect stock symbol is returned
     */
    @Test
    public final void testGetStockSymbolNegative() {
        // compares method return value with unexpected result
        assertFalse("Value returned from getStockSymbol() equals lowercase-converted parameter string",
                stockQuote.getStockSymbol().equals(stockSymbol.toLowerCase()));
    }

    /**
     * Verifies that the correct stock price is returned
     */
    @Test
    public final void testGetStockPricePositive() {
        // compares method return value with expected result
        assertTrue("Value returned from getStockPrice() equals the parameter value",
                stockQuote.getStockPrice() == stockPrice);
    }

    /**
     * Verifies that an incorrect stock price is returned
     */
    @Test
    public final void testGetStockPriceNegative() {
        // compares method return value with unexpected result
        assertFalse("Value returned from getStockPrice() equals the parameter value with decimal moved left by one point",
                stockQuote.getStockPrice() == stockPrice.movePointLeft(1));
    }

    /**
     * Verifies that the correct date recorded is returned
     */
    @Test
    public final void testGetDateRecordedPositive() {
        // compares method return value with expected result
        assertTrue("Value returned from getDateRecorded() equals parameter date",
                stockQuote.getDateRecorded().equals(dateRecorded));
    }

    /**
     * Verifies that an incorrect date recorded is returned
     */
    @Test
    public final void testGetDateRecordedNegative() {
        // compares method return value with unexpected result
        Calendar dateNotRecorded = (Calendar) dateRecorded.clone();
        dateNotRecorded.add(Calendar.DAY_OF_YEAR, 1);
        assertFalse("Value returned from getDateRecorded() equals day after parameter date",
                stockQuote.getDateRecorded().equals(dateNotRecorded));
    }
}
