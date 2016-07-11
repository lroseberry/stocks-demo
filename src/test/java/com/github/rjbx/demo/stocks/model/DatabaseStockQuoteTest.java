package com.github.rjbx.demo.stocks.model;

import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the StockQuote class.
 * @author Bob Basmaji
 */
@Immutable
public final class DatabaseStockQuoteTest {
    // private fields of this class
    private static final DateTime time = new DateTime(2015, 12, 25, 18, 0, 0);
    private static final BigDecimal price = new BigDecimal(35.64);
    private static final String symbolStr = "HAMM";
    private static DatabaseStockSymbol symbol;
    private static DatabaseStockQuote quote;
    private static final int id = 55;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setUp() {
        symbol = new DatabaseStockSymbol(symbolStr);
        quote = new DatabaseStockQuote(time, price, symbol);
        quote.setId(id);
    }

    /**
     * Verifies that getId retrieves the value passed to the corresponding setter method of the same instance
     */
    @Test
    public final void testGetIdPositive() {
        assertTrue("getId retrieves a value other than the one passed to the set method of the same object",
                quote.getId() == id);
    }

    /**
     * Verifies that getId retrieves the value passed to the corresponding setter method of the same instance
     */
    @Test
    public final void testGetIdNegative() {
        assertFalse("getId retrieves a value other than the one passed to the set method of the same object",
                quote.getId() == 25);
    }

    /**
     * Verifies that setId changes the Id
     */
    @Test
    public final void setIdPositive() {
        DatabaseStockQuote quote2 = new DatabaseStockQuote(time, price, symbol);
        quote2.setId(quote.getId());
        boolean idMatches = false;
        if (quote.getId() == quote2.getId()) idMatches = true;
        quote2.setId(34);
        assertTrue("setId does not change the Id", idMatches && (quote.getId() != quote2.getId()));
    }

    /**
     * Verifies that setId changes the Id
     */
    @Test
    public final void testSetIdNegative() {
        DatabaseStockQuote quote2 = new DatabaseStockQuote(time, price, symbol);
        quote2.setId(34);
        assertFalse("setId does not change the Id", (quote.getId() == quote2.getId()));
    }

    /**
     * Verifies that getTime retrieves the value passed to the corresponding setter method of the same instance
     */
    @Test
    public final void testGetTimePositive() {
        assertTrue("getTime retrieves a value other than the one passed to the set method of the same object",
                new DateTime(quote.getTime()).equals(time));
    }

    /**
     * Verifies that getTime retrieves the value passed to the corresponding setter method of the same instance
     */
    @Test
    public final void testGetTimeNegative() {
        assertFalse("getTime retrieves a value other than the one passed to the set method of the same object",
                quote.getTime().equals(DateTime.now()));
    }

    /**
     * Verifies that setTime changes the time
     */
    @Test
    public final void setTimePositive() {
        DatabaseStockQuote quote2 = new DatabaseStockQuote(time, price, symbol);
        boolean timeMatches = false;
        if (quote.getTime().equals(quote2.getTime())) timeMatches = true;
        quote2.setTime(DateTime.now());
        assertTrue("setTime does not change the time", timeMatches && (!quote.getTime().equals(quote2.getTime())));
    }

    /**
     * Verifies that setTime changes the time
     */
    @Test
    public final void testSetTimeNegative() {
        DatabaseStockQuote quote2 = new DatabaseStockQuote(time, price, symbol);
        quote2.setTime(time.minusDays(4));
        assertFalse("setTime does not change the time", (quote.getTime().equals(quote2.getTime())));
    }

    /**
     * Verifies that getPrice retrieves the value passed to the corresponding setter method of the same instance
     */
    @Test
    public final void testGetPricePositive() {
        assertTrue("getPrice retrieves a value other than the one passed to the set method of the same object",
                quote.getPrice().equals(price));
    }

    /**
     * Verifies that getPrices retrieves the value passed ot the corresponding setter method of the same instance
     */
    @Test
    public final void testGetPriceNegative() {
        assertFalse("getPrice retrieves a value other than the one passed to the set method of the same object",
                quote.getPrice().equals(new BigDecimal(32.65)));
    }

    /**
     * Verifies that setPrice changes the price
     */
    @Test
    public final void testSetPricePositive() {
        DatabaseStockQuote quote2 = new DatabaseStockQuote(time, price, symbol);
        boolean priceMatches = false;
        if (quote.getPrice().equals(quote2.getPrice())) priceMatches = true;
        quote2.setPrice(new BigDecimal(23.45));
        assertTrue("setPrice does not change the price", priceMatches && (!quote.getPrice().equals(quote2.getPrice())));
    }

    /**
     * Verifies that setPrice changes the price
     */
    @Test
    public final void testSetPriceNegative() {
        DatabaseStockQuote quote2 = new DatabaseStockQuote(time, price, symbol);
        quote2.setPrice(new BigDecimal(23.45));
        assertFalse("setPrice does not change the Id", (quote.getPrice().equals(quote2.getPrice())));
    }

    /**
     * Verifies that getStockSymbol retrieves the value passed to the corresponding setter method of the same object
     */
    @Test
    public final void testGetStockSymbolPositive() {
        assertTrue("getStockSymbol retrieves a value other than the one passed to the set method of the same object",
                quote.getStockSymbol().equals(symbol));
    }

    /**
     * Verifies that getStockSymbol retrieves the value passed to the corresponding setter method of the same object
     */
    @Test
    public final void testGetStockSymbolNegative() {
        assertFalse("getStockSymbol retrieves a value other than the one passed to the set method of the same object",
                quote.getStockSymbol().equals(new DatabaseStockSymbol("CHEE")));
    }

    /**
     * Verifies the setStockSymbol changes the stockSymbol
     */
    @Test
    public final void testSetStockSymbolPositive() {
        DatabaseStockQuote quote2 = new DatabaseStockQuote(time, price, symbol);
        boolean stockSymbolMatches = false;
        if (quote.getStockSymbol().equals(quote2.getStockSymbol())) stockSymbolMatches = true;
        quote2.setStockSymbol(new DatabaseStockSymbol("CHEE"));
        assertTrue("setStockSymbol does not change the stockSymbol",
                stockSymbolMatches && (!quote.getStockSymbol().equals(quote2.getStockSymbol())));
    }

    /**
     * Verifies that setStockSymbol changes the stockSymbol
     */
    @Test
    public final void testSetStockSymbolNegative() {
        DatabaseStockQuote quote2 = new DatabaseStockQuote(time, price, symbol);
        quote2.setStockSymbol(new DatabaseStockSymbol("CHEE"));
        assertFalse("setStockSymbol does not change the stockSymbol",
                (quote.getStockSymbol().equals(quote2.getStockSymbol())));
    }
}
