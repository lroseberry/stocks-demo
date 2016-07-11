package com.github.rjbx.demo.stocks.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Models the quote table
 */
@Entity
@Table(name="quotes", catalog="stocks")
public class DatabaseStockQuote implements DatabaseAccessObject {
    private int id;
    private Timestamp time;
    private BigDecimal price;
    private DatabaseStockSymbol symbol;

    /**
     * Constructs a {@code StockQuote} that needs to be initialized
     */
    public DatabaseStockQuote() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid {@code StockQuote}
     * @param time the time to assign to this instance
     * @param price  the price to assign to this instance
     * @param symbol the symbol to assign to this instance
     */
    public DatabaseStockQuote(DateTime time, BigDecimal price, DatabaseStockSymbol symbol) {
        setTime(time);
        setPrice(price);
        setStockSymbol(symbol);
    }

    /**
     * @return the id field of this {@code StockQuote} instance
     */
    @Id
    @Column(name = "id",  nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets the id field of this user to the parameter value
     * @param id an integer value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the time field of this {@code StockQuote} instance
     */
    @Basic
    @Column(name = "time", nullable = false, insertable = true, updatable = true)
    public Timestamp getTime() {
        return time;
    }

    /**
     * Sets the time field of this user to the parameter value
     * @param time a Timestamp object
     */
    public void setTime(DateTime time) {
        this.time = new Timestamp(time.getMillis());
    }

    /**
     * @return the price field of this {@code StockQuote} instance
     */
    @Basic
    @Column(name = "price", nullable = false, insertable = true, updatable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price field of this {@code StockQuote} instance
     * @param price a BigDecimal object
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the stockSymbol field of this {@code StockQuote} instance
     */
    @ManyToOne
    @JoinColumn(name = "symbol_id", referencedColumnName = "id",nullable = false)
    public DatabaseStockSymbol getStockSymbol() {
        return symbol;
    }

    /**
     * Sets the stockSymbol field of this {@code StockQuote} instance
     * @param symbol a DatabaseStockSymbol object
     */
    public void setStockSymbol(DatabaseStockSymbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabaseStockQuote stockQuote = (DatabaseStockQuote) o;

        if (id != stockQuote.id) return false;
        if (price != stockQuote.price) return false;
        if (time != null ? !time.equals(stockQuote.time) : stockQuote.time != null) return false;
        if (!symbol.equals(stockQuote.symbol)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + price.hashCode();
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StockQuote{" +
                "id =" + id +
                ", symbol=" + symbol +
                ", date='" + new DateTime(time).toString(StockQuote.getDateFormatter()) + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}