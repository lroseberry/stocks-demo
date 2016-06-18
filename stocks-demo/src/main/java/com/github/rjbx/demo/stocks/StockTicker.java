package com.github.rjbx.demo.stocks;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

/**
 * This class gets information about a stock for a range of dates.
 * @author Bob Basmaji
 */
@Immutable
public final class StockTicker {
    // fields of this class
    private final StockService stockService;

    /**
     * Constructs a new {@code StockTicker} instance
     * @param stockService used to get actual stock data (external dependency)
     */
    public StockTicker(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Gets a {@code List} of {@code StockQuote} instances for the specified date range
     * @param stockSymbol  symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange   end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a {@code List} containing one {@code StockQuote} instance per interval in the specified date range
     */
    public List<StockQuote> getStockHistory(String stockSymbol, Calendar startRange, Calendar endRange, IntervalEnum interval) {
        // set up variables for getting and storing {@code StockQuotes}
        List<StockQuote> returnValue = new ArrayList();
        returnValue.addAll(stockService.getQuote(stockSymbol, startRange, endRange, interval));
        return returnValue;
    }

    /**
     * A main method which enables program execution
     * @param args an array that should contain as elements:
     *             {@code String} representations of a valid stock symbol, start date and end date
     * @throws ParseException
     */
    public static void main(String[] args) {
        StockService service = StockServiceFactory.createStockService();
        StockQuote singleQuote = service.getQuote(args[0]);
        System.out.println(singleQuote);
        SimpleDateFormat formatter = StockQuote.getDateFormatter();
        Calendar startRange = Calendar.getInstance();
        Calendar endRange = Calendar.getInstance();
        try {
            startRange.setTime(formatter.parse((args[1])));
            endRange.setTime(formatter.parse((args[2])));
        } catch (ParseException e) {
            new RuntimeException(e.getMessage());
        }
        IntervalEnum interval = BasicIntervalEnum.valueOf(args[3]);
        List<StockQuote> listQuotes = service.getQuote(args[0], startRange, endRange, interval);
        System.out.println(listQuotes);
    }
}
