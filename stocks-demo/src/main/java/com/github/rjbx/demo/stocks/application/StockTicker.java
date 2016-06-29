package com.github.rjbx.demo.stocks.application;

import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.services.*;
import com.github.rjbx.demo.stocks.utilities.StockServiceException;
import org.apache.http.annotation.Immutable;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
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
     * @throws StockServiceException
     */
    public List<StockQuote> getStockHistory(String stockSymbol, DateTime startRange, DateTime endRange, IntervalEnum interval) throws StockServiceException {
        // set up variables for getting and storing {@code StockQuotes}
        List<StockQuote> returnValue = new ArrayList();
        returnValue.addAll(stockService.getQuote(stockSymbol, startRange, endRange, interval));
        return returnValue;
    }

    /**
     * A main method which enables program execution
     * @param args an array that should contain as elements:
     *             {@code String} representations of a valid stock symbol, start date and end date
     * @throws StockServiceException
     */
    public static void main(String[] args) throws StockServiceException {
        StockService basicService = ServiceFactory.createStockService("basic");
        StockService databaseService = ServiceFactory.createStockService("database");
        System.out.println(basicService.getQuote(args[0]));
        System.out.println(databaseService.getQuote(args[0]));

        DateTimeFormatter formatter = StockQuote.getDateFormatter();
        DateTime startRange = DateTime.parse(args[1], formatter);
        DateTime endRange = DateTime.parse(args[2], formatter);;

        System.out.println(basicService.getQuote(args[0], startRange, endRange));
        System.out.println(databaseService.getQuote(args[0], startRange, endRange));

        IntervalEnum interval = BasicIntervalEnum.valueOf(args[3]);
        System.out.println(basicService.getQuote(args[0], startRange, endRange, interval));
        System.out.println(databaseService.getQuote(args[0], startRange, endRange, interval));
    }
}
