package com.github.rjbx.demo.stocks;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class stores information about a stock of a particular symbol, date and price.
 * @author Bob Basmaji
 */
@Immutable
public final class StockQuote {
    // private fields of this class
    private final String stockSymbol;
    private final BigDecimal stockPrice;
    private final Calendar dateRecorded;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy hh:mm a");

    /**
     * Constructs a new {@code StockQuote} instance
     * @param dateRecorded date the stock info was recorded
     * @param stockPrice price of the stock on the provided date
     * @param stockSymbol symbol for the company issuing the stock
     */
    public StockQuote(Calendar dateRecorded, BigDecimal stockPrice, String stockSymbol) {
        // if any parameter values are null, throw exception; otherwise, initialize fields
        if ((dateRecorded == null) || (stockPrice == null) || (stockSymbol == null)) {
            throw new RuntimeException();
        }
        this.dateRecorded = dateRecorded;
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
    public final Calendar getDateRecorded() {
        return dateRecorded;
    }

    /**
     * @return an instance of an object for formatting all {@code StockQuote} dates according to the specified date pattern
     */
    public static final SimpleDateFormat getDateFormatter() { return dateFormatter; }

    /**
     * @return a {@code String} containing the formatted values of the fields of this instance
     */
    @Override
    public String toString() {
        return " [ " + getStockSymbol() + " " + dateFormatter.format(getDateRecorded().getTime()) + " " + NumberFormat.getCurrencyInstance().format(getStockPrice()) + " ] ";
    }
}
