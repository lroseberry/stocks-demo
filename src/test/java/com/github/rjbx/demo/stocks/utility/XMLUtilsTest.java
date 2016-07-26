package com.github.rjbx.demo.stocks.utility;

import com.github.rjbx.demo.stocks.model.XMLStockQuoteList;
import org.junit.Test;

import javax.xml.transform.sax.SAXResult;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the XMLUtils class
 * @author Bob Basmaji
 */
public final class XMLUtilsTest {

    /**
     * Verifies that unmarshal() return value is XMLStockQuote instance
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testUnmarshalPositive() throws XMLUnmarshalException {
        XMLStockQuoteList quoteList = XMLUtils.unmarshal(XMLUtils.xmlFilePath);
        assertTrue("unmarshal() return value is not an XMLStockQuoteList instance", quoteList instanceof XMLStockQuoteList);
    }

    /**
     * Verifies that passing an invalid file path reference to unmarshal() throws an XMLUnarshalException
     * @throws XMLUnmarshalException
     */
    @Test(expected=XMLUnmarshalException.class)
    public final void testUnmarshalNegative() throws XMLUnmarshalException {
        XMLStockQuoteList quoteList = XMLUtils.unmarshal("invalid path");
    }

    /**
     * Verifies that marshal() return value is SAXResult instance
     * @throws XMLMarshalException
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testMarshalPositive() throws XMLMarshalException, XMLUnmarshalException {
        XMLStockQuoteList quoteList = XMLUtils.unmarshal(XMLUtils.xmlFilePath);
        SAXResult result = XMLUtils.marshal(quoteList);
        assertTrue("marshal() return value is not an SAXResult instance", result instanceof SAXResult);
    }

    /**
     * Verifies that passing a null XMLStockQuoteList to marshal throws an XMLMarhshalException
     * @throws XMLMarshalException
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testMarshalNegative() throws XMLMarshalException {
        XMLStockQuoteList quoteList = null;
        XMLUtils.marshal(quoteList);
    }
}
