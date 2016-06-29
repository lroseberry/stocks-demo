package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.utilities.DatabaseConnectionException;
import com.github.rjbx.demo.stocks.utilities.DatabaseUtils;
import com.github.rjbx.demo.stocks.utilities.StockServiceException;
import org.apache.http.annotation.Immutable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines methods for getting stock quotes from a database, and implements the {@code StockService} interface.
 * @author Bob Basmaji
 */
@Immutable
public final class DatabaseStockService implements StockService {

    /**
     * Gets today's {@code StockQuote} instance for the given symbol
     * @param stockSymbol symbol for the company issuing the stock
     * @return a {@code StockQuote} instance
     * @throws StockServiceException
     */
    public final StockQuote getQuote(String stockSymbol) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + stockSymbol + "'";

            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<StockQuote>(resultSet.getFetchSize());
            while(resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp time = resultSet.getTimestamp("time");
                DateTime dateRecorded = new DateTime(time);
                stockQuotes.add(new StockQuote(dateRecorded, price, symbolValue));
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("No instances of " + stockSymbol + " found");
        }
        return stockQuotes.get(stockQuotes.size() - 1);
    }

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol and date range
     * @param stockSymbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @return a {@code List} of {@code StockQuote} instances
     * @throws StockServiceException
     */
    public final List<StockQuote> getQuote(String stockSymbol, DateTime startRange, DateTime endRange) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + stockSymbol + "' and time between '" + startRange.toString(StockQuote.DATE_PATTERN) + "' and '" + endRange.toString(StockQuote.DATE_PATTERN)+ "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<StockQuote>(resultSet.getFetchSize());
            while(resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp time = resultSet.getTimestamp("time");
                DateTime dateRecorded = new DateTime(time.getTime());
                stockQuotes.add(new StockQuote(dateRecorded, price, symbolValue));
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("No instances of " + stockSymbol + " found in the selected range");
        }
        return stockQuotes;
    }

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol, date range and interval
     * @param stockSymbol symbol for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a {@code List} of {@code StockQuote} instances
     * @throws StockServiceException
     */
    public final List<StockQuote> getQuote(String stockSymbol, DateTime startRange, DateTime endRange, IntervalEnum interval) throws StockServiceException{
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + stockSymbol + "' and time between '" + startRange.toString(StockQuote.DATE_PATTERN) + "' and '" + endRange.toString(StockQuote.DATE_PATTERN) + "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<StockQuote>(resultSet.getFetchSize());
            DateTime intervalEnd = new DateTime(startRange);
            while (resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp time = resultSet.getTimestamp("time");
                DateTime dateRecorded = new DateTime(time);
                if (!dateRecorded.isBefore(intervalEnd)) {
                    stockQuotes.add(new StockQuote((DateTime) dateRecorded, price, symbolValue));
                    intervalEnd.plusDays(interval.amount());
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("No instances of " + stockSymbol + " found in the selected range");
        }
        return stockQuotes;
    }
}

