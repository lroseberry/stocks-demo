package com.github.rjbx.demo.stocks.model;

import org.apache.http.annotation.Immutable;

import java.math.BigDecimal;
import java.text.NumberFormat;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This class stores information about a stock of a particular symbol, date and price.
 * @author Bob Basmaji
 */
@Immutable
public final class StockQuote {
    // private fields of this class
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(DATE_PATTERN);
    private final String stockSymbol;
    private final BigDecimal stockPrice;
    private final DateTime dateRecorded;

    /**
     * Constructs a new {@code StockQuote} instance
     * @param dateRecorded date the stock info was recorded
     * @param stockPrice price of the stock on the provided date
     * @param stockSymbol symbol for the company issuing the stock
     */
    public StockQuote(DateTime dateRecorded, BigDecimal stockPrice, String stockSymbol) {
        // if any parameter values are null, throw exception; otherwise, initialize fields
        if ((dateRecorded == null) || (stockPrice == null) || (stockSymbol == null)) {
            throw new RuntimeException();
        }
        this.dateRecorded = new DateTime(dateRecorded);
        this.stockPrice = stockPrice;
        this.stockSymbol = stockSymbol;
    }

    /**
     * @return the symbol that represents the company issuing this stock
     */
    public final String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * @return the price of one share of this stock
     */
    public final BigDecimal getStockPrice() {
        return stockPrice;
    }

    /**
     * @return the date that the info for this stock was recorded
     */
    public final DateTime getDateRecorded() {
        return dateRecorded;
    }

    /**
     * @return an instance of an object for formatting all {@code StockQuote} dates according to the specified date pattern
     */
    public static final DateTimeFormatter getDateFormatter() { return dateFormatter; }

    /**
     * @return a String containing the formatted values of the fields of this instance
     */
    @Override
    public String toString() {
        return " [ " + getStockSymbol() + " " + dateRecorded.toString(dateFormatter) + " " + NumberFormat.getCurrencyInstance().format(getStockPrice()) + " ] ";
    }
}
