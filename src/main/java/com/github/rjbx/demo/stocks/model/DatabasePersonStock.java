package com.github.rjbx.demo.stocks.model;

import javax.persistence.*;
/**
 * This class models the {@code people_stocks} table.
 * @author Bob Basmaji
 */
@Entity
@Table(name="person_stocks", catalog="stocks")
public class DatabasePersonStock implements DatabaseAccessObject {
    // private fields of this class
    private int id;
    private DatabasePerson person;
    private DatabaseStockSymbol symbol;

    /**
     * Constructs a {@code PersonStock} that needs to be initialized
     */
    public DatabasePersonStock() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid {@code PersonStock} instance
     * @param person the person to assign the stock symbol to
     * @param symbol  the stock symbol to associate the person with
     */
    public DatabasePersonStock(DatabasePerson person, DatabaseStockSymbol symbol) {
        setPerson(person);
        setStockSymbol(symbol);
    }

    /**
     * Gets the unique ID for this {@code PersonStock} instance
     * @return an integer value representing the unique ID for this PersonStock
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID for this {@code PersonStock} instance.
     * This method should not be called by client code. The value is managed internally.
     * @param id an integer value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the person associated with this {@code PersonStock} instance
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public DatabasePerson getPerson() {
        return person;
    }

    /**
     * Sets the first name of this {@code PersonStock} instance
     * @param person a Person instance
     */
    public void setPerson(DatabasePerson person) {
        this.person = person;
    }

    /**
     * @return the stock symbol of this {@code PersonStock}
     */
    @ManyToOne
    @JoinColumn(name = "symbol_id", referencedColumnName = "id", nullable = false)
    public DatabaseStockSymbol getStockSymbol() {
        return symbol;
    }

    /**
     * Sets the stock symbol of this {@code PersonStock} instance
     * @param symbol a String value
     */
    public void setStockSymbol(DatabaseStockSymbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabasePersonStock personStock = (DatabasePersonStock) o;

        if (id != personStock.id) return false;
        if (!person.equals(personStock.person)) return false;
        if (!symbol.equals(personStock.symbol)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + person.hashCode();
        result = 31 * result + symbol.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PersonStock{" +
                "id=" + id +
                ", person='" + person + '\'' +
                ", stockSymbol='" + symbol + '\'' +
                '}';
    }
}
