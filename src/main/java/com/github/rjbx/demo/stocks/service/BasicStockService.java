package com.github.rjbx.demo.stocks.service;

import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.utility.Interval;
import org.apache.http.annotation.Immutable;

import org.joda.time.DateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.math.BigDecimal;

/**
 * This class defines methods for getting stock quotes, and implements the {@code StockService} interface.
 * @author Bob Basmaji
 */
@Immutable
public final class BasicStockService implements StockService {
    // fields of this class
    public static final BasicStockService INSTANCE = new BasicStockService();
    private static BigDecimal startPrice = new BigDecimal(100);

    // hides the constructor so that new instances are build through the factory class
    protected BasicStockService() {
    }

    /**
     * Gets today's {@code StockQuote} instance for the given symbol
     * @param symbol symbol for the company issuing the stock
     * @return a {@code StockQuote} instance
     */
    public final StockQuote getQuote(String symbol) {
        return new StockQuote(DateTime.now(), startPrice, symbol);
    }

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol and date range
     * @param symbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @return a {@code List} of {@code StockQuote} instances
     */
    public final List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange) {
        // defensively copies parameter values of mutable objects
        List<StockQuote> quotes = new ArrayList<StockQuote>();
        DateTime time = new DateTime(startRange);
        // fetch data request is source agnostic
        while (time.isBefore(endRange)) {
            quotes.add(new StockQuote(time, startPrice, symbol));
            startPrice = startPrice.add(new BigDecimal(new Random().nextInt(10) - 3.37));
            time = time.plusDays(1);
        }
        return quotes;
    }

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol, date range and interval
     * @param symbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a {@code List} of {@code StockQuote} instances
     */
    public final List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange, Interval interval) {
        // defensively copies parameter values of mutable objects
        List<StockQuote> quotes = new ArrayList<StockQuote>();
        DateTime time = new DateTime(startRange);
        // fetch data request is source agnostic
        while (time.isBefore(endRange)) {
            quotes.add(new StockQuote(time, startPrice, symbol));
            startPrice = startPrice.add(new BigDecimal(new Random().nextInt(10) - 3.37));
            time = time.plusDays(1);
        }
        return quotes;
    }
}
