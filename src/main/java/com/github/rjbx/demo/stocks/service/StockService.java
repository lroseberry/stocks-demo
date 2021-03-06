package com.github.rjbx.demo.stocks.service;

import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.utility.Interval;
import com.github.rjbx.demo.stocks.utility.StockServiceException;
import org.apache.http.annotation.Immutable;

import org.joda.time.DateTime;
import java.util.List;

/**
 * This interface requires that implementing classes define methods for getting stock quotes.
 * Classes containing methods for getting stock quotes may implement this interface.
 * @author Bob Basmaji
 */
@Immutable
public interface StockService {
    /**
     * Gets today's {@code StockQuote} instance for the given symbol
     * @param symbol symbol for the company issuing the stock
     * @return a {@code StockQuote} instance
     * @throws StockServiceException
     */
    StockQuote getQuote(String symbol) throws StockServiceException ;

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol and date range
     * @param symbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange   end of the date range
     * @return a {@code List} of {@code StockQuote} instances
     * @throws StockServiceException
     */
    List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange) throws StockServiceException ;

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol and date range
     * @param symbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange   end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a {@code List} of {@code StockQuote} instances
     * @throws StockServiceException
     */
    List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange, Interval interval) throws StockServiceException;
}
