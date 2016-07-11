package com.github.rjbx.demo.stocks.model;

import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the DatabaseStockSymbol class.
 * @author Bob Basmaji
 */
@Immutable
public final class DatabaseStockSymbolTest {
    // fields of this class
    private static final String symbol = "HAMM";
    private static DatabaseStockSymbol stockSymbol;
    private static final int id = 44;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setUp() {
        stockSymbol = new DatabaseStockSymbol(symbol);
        stockSymbol.setId(id);
    }

    /**
     * Verifies that getId retrieves the value that was passed to the set method of the same instance
     */
    @Test
    public final void testGetIdPositive() {
        assertTrue("getId retrieves a value other than the one passed to the set method of the same object",
                stockSymbol.getId() == id);
    }

    /**
     * Verifies that getId retrieves the value that was passed to the set method of the same instance
     */
    @Test
    public final void testGetIdNegative() {
        assertFalse("getId retrieves a value other than the one passed to the set method of the same object",
                stockSymbol.getId() == 25);
    }

    /**
     * Verifies that setId changes the Id
     */
    @Test
    public final void setIdPositive() {
        DatabaseStockSymbol stockSymbol2 = new DatabaseStockSymbol(symbol);
        stockSymbol2.setId(stockSymbol.getId());
        boolean idMatches = false;
        if (stockSymbol.getId() == stockSymbol2.getId()) idMatches = true;
        stockSymbol2.setId(34);
        assertTrue("setId does not change the Id", idMatches && (stockSymbol.getId() != stockSymbol2.getId()));
    }

    /**
     * Verifies that setId changes the Id
     */
    @Test
    public final void testSetIdNegative() {
        DatabaseStockSymbol stockSymbol2 = new DatabaseStockSymbol(symbol);
        stockSymbol2.setId(34);
        assertFalse("setId does not change the Id", (stockSymbol.getId() == stockSymbol2.getId()));
    }

    /**
     * Verifies that getSymbol retrieves the value that was passed to the set method of the same instance
     */
    @Test
    public final void testGetSymbolPositive() {
        assertTrue("getSymbol retrieves a value other than the one passed to the set method of the same object",
                stockSymbol.getSymbol().equals(symbol));
    }

    /**
     * Verifies that getSymbol retrieves the value that was passed to the set method of the same instance
     */
    @Test
    public final void testGetSymbolNegative() {
        assertFalse("getStockSymbol retrieves a value other than the one passed to the set method of the same object",
                stockSymbol.getSymbol().equals("CHEE"));
    }

    /**
     * Verifies that setSymbol changes the symbol
     */
    @Test
    public final void testSetSymbolPositive() {
        DatabaseStockSymbol stockSymbol2 = new DatabaseStockSymbol(symbol);
        boolean stockSymbolMatches = false;
        if (stockSymbol.getSymbol().equals(stockSymbol2.getSymbol())) stockSymbolMatches = true;
        stockSymbol2.setSymbol("CHEE");
        assertTrue("setSymbol does not change the symbol",
                stockSymbolMatches && (!stockSymbol.getSymbol().equals(stockSymbol2.getSymbol())));
    }

    /**
     * Verifies that setSymbol changes the symbol
     */
    @Test
    public final void testSetSymbolNegative() {
        DatabaseStockSymbol stockSymbol2 = new DatabaseStockSymbol(symbol);
        stockSymbol2.setSymbol("CHEE");
        assertFalse("setSymbol does not change the symbol", stockSymbol.getSymbol().equals(stockSymbol2.getSymbol()));
    }
}

