package com.github.rjbx.demo.stocks.model;

import com.github.rjbx.demo.stocks.utilities.XMLUnmarshalException;
import com.github.rjbx.demo.stocks.utilities.XMLUtils;
import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the XMLStockQuote class.
 * @author Bob Basmaji
 */
@Immutable
public final class XMLStockQuoteTest {
    // fields of this class
    private static XMLStockQuote quote;
    private static final String time = "2005-12-25 18:00:00";
    private static final String price = "15.26";
    private static final String symbol = "FRAN";

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setUp() {
        quote = new XMLStockQuote();
        quote.setSymbol(symbol);
        quote.setPrice(price);
        quote.setTime(time);
    }

    /**
     * Verifies that getSymbol returns the value passed to the setter method of the same object
     */
    @Test
    public final void testGetSymbolPositive() {
        assertTrue("getSymbol does not return the value passed to the setter method of the same object", 
                quote.getSymbol().equals(symbol));
    }

    /**
     * Verifies that getSymbol returns the value passed to the setter method of the same object
     */
    @Test
    public final void testGetSymbolNegative() {
        assertFalse("getSymbol does not return the value passed to the setter method of the same object",
                quote.getSymbol().equals("NARF"));
    }

    /**
     * Verifies that setSymbol changes this symbol
     */
    @Test
    public final void testSetSymbolPositive() {
        XMLStockQuote quote2 = new XMLStockQuote();
        quote2.setSymbol(symbol);
        boolean symbolMatches = false;
        if (quote.getSymbol().equals(quote2.getSymbol())) symbolMatches = true;
        quote2.setSymbol("NARF");
        assertTrue("setSymbol does not change this symbol", symbolMatches && (!quote.getSymbol().equals(quote2.getSymbol())));
    }

    /**
     * Verifies that setSymbol changes this symbol
     */
    @Test
    public final void testSetSymbolNegative() {
        XMLStockQuote quote2 = new XMLStockQuote();
        quote2.setSymbol(symbol);
        quote2.setSymbol("NARF");
        assertFalse("setSymbol does not change this symbol", quote.getSymbol().equals(quote2.getSymbol()));
    }

    /**
     * Verifies that getPrice returns the value passed to the setter method of the same object
     */
    @Test
    public final void testGetPricePositive() {
        assertTrue("getPrice does not return the value passed to the setter method of the same object",
                quote.getPrice().equals(price));
    }

    /**
     * Verifies that getPrice returns the value passed to the setter method of the same object
     */
    @Test
    public final void testGetPriceNegative() {
        assertFalse("getPrice does not return the value passed to the setter method of the same object",
                quote.getPrice().equals("234.23"));
    }

    /**
     * Verifies that setPrice changes this price
     */
    @Test
    public final void testSetPricePositive() {
        XMLStockQuote quote2 = new XMLStockQuote();
        quote2.setPrice(price);
        boolean priceMatches = false;
        if (quote.getPrice().equals(quote2.getPrice())) priceMatches = true;
        quote2.setPrice("234.23");
        assertTrue("setPrice does not change this price",
                priceMatches && (!quote.getPrice().equals(quote2.getPrice())));
    }

    /**
     * Verifies that setPrice changes this price
     */
    @Test
    public final void testSetPriceNegative() {
        XMLStockQuote quote2 = new XMLStockQuote();
        quote2.setPrice(price);
        quote2.setPrice("234.23");
        assertFalse("setPrice does not change this price",
                quote.getPrice().equals(quote2.getPrice()));
    }

    /**
     * Verifies that getTime returns the value passed to the setter method of the same object
     */
    @Test
    public final void testGetTimePositive() {
        assertTrue("getTime does not return the value passed to the setter method of the same object",
                quote.getTime().equals(time));
    }

    /**
     * Verifies that getTime returns the value passed to the setter method of the same object
     */
    @Test
    public final void testGetTimeNegative() {
        assertFalse("getTime does not return the value passed to the setter method of the same object",
                quote.getTime().equals("2005-01-01 00:00:00"));
    }

    /**
     * Verifies that getTime changes this time
     */
    @Test
    public final void testSetTimePositive() {
        XMLStockQuote quote2 = new XMLStockQuote();
        quote2.setTime(time);
        boolean timeMatches = false;
        if (quote.getTime().equals(quote2.getTime())) timeMatches = true;
        quote2.setTime("2005-01-01 00:00:00");
        assertTrue("setTime does not change this time", timeMatches && (!quote.getTime().equals(quote2.getTime())));
    }

    /**
     * Verifies that getTime changes this time
     */
    @Test
    public final void testSetTimeNegative() {
        XMLStockQuote quote2 = new XMLStockQuote();
        quote2.setTime(time);
        quote2.setTime("2005-01-01 00:00:00");
        assertFalse("setTime does not change this time", quote.getTime().equals(quote2.getTime()));
    }

    /**
     * Verifies that toString returns an instance of String
     */
    @Test
    public final void testToStringPositive() {
        assertTrue("toString returns a String instance", quote.toString() instanceof String);
    }

    /**
     * Verifies that toString does not return a String other than one which is derived from XML data
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testToStringNegative() throws XMLUnmarshalException {
        assertFalse("toString returns a String containing nonsense", quote.toString().equals("StringCheese"));
    }
}
