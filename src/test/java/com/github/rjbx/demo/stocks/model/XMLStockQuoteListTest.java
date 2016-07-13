package com.github.rjbx.demo.stocks.model;

import com.github.rjbx.demo.stocks.utilities.XMLUnmarshalException;
import com.github.rjbx.demo.stocks.utilities.XMLUtils;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the StockQuoteList class.
 * @author Bob Basmaji
 */
public final class XMLStockQuoteListTest {

    /**
     * Verifies that getStocks returns a list of XMLStockQuote instances
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testGetStocksPositive() throws XMLUnmarshalException {
        XMLStockQuoteList quoteList = XMLUtils.unmarshal(XMLUtils.xmlFilePath);
        assertTrue("getStocks does not return a list of XMLStockQuote instances",
                quoteList.getStock() instanceof List && quoteList.getStock().get(0) instanceof XMLStockQuote);
    }

    /**
     * Verifies that passing a nonexistant file path reference to unmarshal() generates an XMLUnmarshalException
     * @throws XMLUnmarshalException
     */
    @Test(expected=XMLUnmarshalException.class)
    public final void testGetStocksNegative() throws XMLUnmarshalException {
        XMLStockQuoteList quoteList = XMLUtils.unmarshal("Hamm N Ch");
    }

    /**
     * Verifies that toString returns a String instance
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testToStringPositive() throws XMLUnmarshalException {
        XMLStockQuoteList quoteList = XMLUtils.unmarshal(XMLUtils.xmlFilePath);
        assertTrue("toString does not return a String instance", quoteList.toString() instanceof String);
    }

    /**
     * Verifies that toString does not return a String other than one which is derived from XML data
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testToStringNegative() throws XMLUnmarshalException {
        XMLStockQuoteList quoteList = XMLUtils.unmarshal(XMLUtils.xmlFilePath);
        assertFalse("toString returns a String containing nonsense", quoteList.toString().equals("StringCheese"));
    }
}
