package com.github.rjbx.demo.stocks.model;

import javax.persistence.*;

/**
 * This class models a database table containing stock symbol information
 * as specified by the "name" property of the {@code @Table} annotation.
 * @author Bob Basmaji
 */
@Entity
@Table(name="stock_symbols", catalog="stocks")
public class DatabaseStockSymbol implements DatabaseAccessObject {

    /**
     * Constructs a {@code StockSymbol} that needs to be initialized
     */
    public DatabaseStockSymbol() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid {@code StockSymbol} instance
     * @param symbol
     */
    public DatabaseStockSymbol(String symbol) {
        setSymbol(symbol);
    }

    private int id;
    private String symbol;

    /**
     * @return the id field of this {@code StockSymbol} instance
     */
    @Id
    @Column(name = "id",  nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets the id field of this {@code StockSymbol} instance
     * @param id an int value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the symbol field of this {@code StockSymbol} instance
     */
    @Basic
    @Column(name = "symbol", nullable = false, insertable = true, updatable = true, length = 4)
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol field of this {@code StockSymbol} instance
     * @param symbol a String
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabaseStockSymbol that = (DatabaseStockSymbol) o;

        if (id != that.id) return false;
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31 * (symbol != null ? symbol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "symbol=" + symbol;
    }
}

