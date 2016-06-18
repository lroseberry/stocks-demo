package com.github.rjbx.demo.stocks;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Calendar;
import java.util.List;

/**
 * This interface requires that implementing classes define methods for getting quotes.
 * Classes containing methods for getting stock quotes may implement this interface.
 * @author Bob Basmaji
 */
@Immutable
public interface StockService {
    /**
     * Gets today's {@code StockQuote} instance for the given symbol
     * @param stockSymbol symbol for the company issuing the stock
     * @return a {@code StockQuote} instance
     */
    StockQuote getQuote(String stockSymbol);

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol and date range
     * @param stockSymbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange   end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a {@code List} of {@code StockQuote} instances
     */
    List<StockQuote> getQuote(String stockSymbol, Calendar startRange, Calendar endRange, IntervalEnum interval);
}
