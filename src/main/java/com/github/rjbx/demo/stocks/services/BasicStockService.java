package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.StockQuote;
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

    // private fields of this class
    BigDecimal startPrice = new BigDecimal(100);

    // hides the constructor so that new instances are build through the factory class
    protected BasicStockService() {
    }

    /**
     * Gets today's {@code StockQuote} instance for the given symbol
     * @param stockSymbol symbol for the company issuing the stock
     * @return a {@code StockQuote} instance
     */
    public final StockQuote getQuote(String stockSymbol) {
        return new StockQuote(DateTime.now(), startPrice, stockSymbol);
    }

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol and date range
     * @param stockSymbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @return a {@code List} of {@code StockQuote} instances
     */
    public final List<StockQuote> getQuote(String stockSymbol, DateTime startRange, DateTime endRange) {
        // defensively copies parameter values of mutable objects
        List<StockQuote> quotes = new ArrayList<StockQuote>();
        DateTime dateRecorded = new DateTime(startRange);
        // fetch data request is source agnostic
        while (dateRecorded.isBefore(endRange)) {
            quotes.add(new StockQuote(dateRecorded, startPrice, stockSymbol));
            startPrice = startPrice.add(new BigDecimal(new Random().nextInt(10) - 3.37));
            dateRecorded = dateRecorded.plusDays(1);
        }
        return quotes;
    }

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol, date range and interval
     * @param stockSymbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a {@code List} of {@code StockQuote} instances
     */
    public final List<StockQuote> getQuote(String stockSymbol, DateTime startRange, DateTime endRange, IntervalEnum interval) {
        // defensively copies parameter values of mutable objects
        List<StockQuote> quotes = new ArrayList<StockQuote>();
        DateTime dateRecorded = new DateTime(startRange);
        // fetch data request is source agnostic
        while (dateRecorded.isBefore(endRange)) {
            quotes.add(new StockQuote(dateRecorded, startPrice, stockSymbol));
            startPrice = startPrice.add(new BigDecimal(new Random().nextInt(10) - 3.37));
            dateRecorded = dateRecorded.plusDays(1);
        }
        return quotes;
    }
}
