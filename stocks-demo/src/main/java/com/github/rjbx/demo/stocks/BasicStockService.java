package com.github.rjbx.demo.stocks;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Calendar;
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

    /**
     * Gets today's {@code StockQuote} instance for the given symbol
     * @param stockSymbol symbol for the company issuing the stock
     * @return a {@code StockQuote} instance
     */
    public final StockQuote getQuote(String stockSymbol) {
        // fetch data request is source agnostic
        return new StockQuote(Calendar.getInstance(), startPrice, stockSymbol);
    }

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol and date range
     * @param stockSymbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a {@code List} of {@code StockQuote} instances
     */
    public final List<StockQuote> getQuote(String stockSymbol, Calendar startRange, Calendar endRange, IntervalEnum interval) {
        // defensively copies parameter values of mutable objects
        List<StockQuote> quotes = new ArrayList<StockQuote>();
        Calendar dateRecorded = Calendar.getInstance();
        dateRecorded.setTime(startRange.getTime());
        // fetch data request is source agnostic
        while (dateRecorded.before(endRange)) {
            quotes.add(new StockQuote((Calendar) dateRecorded.clone(), startPrice, stockSymbol));
            startPrice = startPrice.add(new BigDecimal(new Random().nextInt(10) - 3.37));
            dateRecorded.add(interval.unit(), interval.amount());
        }
        return quotes;
    }
}
