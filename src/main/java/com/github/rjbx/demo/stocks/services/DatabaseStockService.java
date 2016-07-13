package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.DatabaseStockQuote;
import com.github.rjbx.demo.stocks.model.DatabaseStockSymbol;
import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.utilities.*;
import org.apache.http.annotation.Immutable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines methods for getting stock quotes from a database, and implements the {@code StockService} interface.
 * @author Bob Basmaji
 */
@Immutable
public class DatabaseStockService implements StockService {

    // hides the constructor so that new instances are build through the factory class
    protected DatabaseStockService() {
    }

    /**
     * Gets today's {@code StockQuote} instance for the given symbol
     * @param symbol the symbol for the company issuing the stock
     * @return a {@code StockQuote} instance
     * @throws StockServiceException
     */
    public final StockQuote getQuote(String symbol) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select id from stock_symbols where symbol = '" + symbol + "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            resultSet.next();
            queryString = "select * from quotes where symbol_id = '" + resultSet.getInt("id") + "'";
            resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<StockQuote>(resultSet.getFetchSize());
            while(resultSet.next()) {
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp timestamp = resultSet.getTimestamp("time");
                DateTime time = new DateTime(timestamp);
                stockQuotes.add(new StockQuote(time, price, symbol));
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("No instances of " + symbol + " found in the selected range");
        }
        return stockQuotes.get(stockQuotes.size() - 1);
    }

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol and date range
     * @param symbol abbreviation for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @return a {@code List} of {@code StockQuote} instances
     * @throws StockServiceException
     */
    public final List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select id from stock_symbols where symbol = '" + symbol + "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            resultSet.next();
            queryString = "select * from quotes where symbol_id = '" + resultSet.getInt("id") + "' and time between '" + startRange.toString(StockQuote.DATE_PATTERN) + "' and '" + endRange.toString(StockQuote.DATE_PATTERN)+ "'";
            resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<StockQuote>(resultSet.getFetchSize());
            while(resultSet.next()) {
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp timestamp = resultSet.getTimestamp("time");
                DateTime time = new DateTime(timestamp);
                stockQuotes.add(new StockQuote(time, price, symbol));
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("No instances of " + symbol + " found in the selected range");
        }
        return stockQuotes;
    }

    /**
     * Gets the {@code List} of {@code StockQuote} instances for the given symbol, date range and interval
     * @param symbol abbreviation for the company issuing the stock
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @param interval time elapsed between stockQuote instances
     * @return a {@code List} of {@code StockQuote} instances
     * @throws StockServiceException
     */
    public final List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange, Interval interval) throws StockServiceException{
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select id from stock_symbols where symbol = '" + symbol + "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            resultSet.next();
            queryString = "select * from quotes where symbol_id = '" + resultSet.getInt("id") + "' and time between '" + startRange.toString(StockQuote.DATE_PATTERN) + "' and '" + endRange.toString(StockQuote.DATE_PATTERN) + "'";
            resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<StockQuote>(resultSet.getFetchSize());
            DateTime intervalEnd = new DateTime(startRange);
            while (resultSet.next()) {
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp timestamp = resultSet.getTimestamp("time");
                DateTime time = new DateTime(timestamp);
                if (!time.isBefore(intervalEnd)) {
                    stockQuotes.add(new StockQuote((DateTime) time, price, symbol));
                    intervalEnd.plusHours(interval.amount());
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("No instances of " + symbol + " found in the selected range");
        }
        return stockQuotes;
    }

    /**
     * Adds a new quote or updates the data of an existing quote
     * @param time a {@code DateTime} object representing the time of the quote
     * @param price the price of the quote
     * @param stockSymbol the symbol of the quote
     * @throws StockServiceException if a service can not perform the requested operation
     */
    public final void addOrUpdateQuote(DateTime time, BigDecimal price, DatabaseStockSymbol stockSymbol) throws StockServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of StockQuote if already exists within table
            // or adds as last row of personStock table
            transaction = session.beginTransaction();
            session.saveOrUpdate(stockSymbol);
            DatabaseStockQuote dbQuote = new DatabaseStockQuote(time, price, stockSymbol);
            session.saveOrUpdate(dbQuote);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
                throw new RuntimeException(e.getMessage());
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }
}

