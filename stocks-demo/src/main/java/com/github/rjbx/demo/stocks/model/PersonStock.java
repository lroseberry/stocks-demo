package com.github.rjbx.demo.stocks.model;

import javax.persistence.*;
/**
 * This class models the {@code people_stocks} table.
 * @author Bob Basmaji
 */
@Entity
@Table(name="people_stocks",catalog="stocks")
public class PersonStock {
    // private fields of this class
    private int id;
    private Person person;
    private String stockSymbol;

    /**
     * Constructs a PersonStock that needs to be initialized
     */
    public PersonStock() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid PersonStock
     * @param person the person to assign the atock symbol to
     * @param stockSymbol  the stock symbol to associate the person with
     */
    public PersonStock(Person person, String stockSymbol) {
        setPerson(person);
        setStockSymbol(stockSymbol);
    }

    /**
     * Gets the unique ID for this PersonStock instance
     * @return an integer value representing the unique ID for this PersonStock
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID for this PersonStock instance.
     * This method should not be called by client code. The value is managed internally.
     * @param id an integer value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the person associated with this PersonStock instance
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the first name of this PersonStock instance
     * @param person a Person instance
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return the stock symbol of this PersonStock
     */
    @Basic
    @Column(name = "stock_symbol", nullable = false, insertable = true, updatable = true, length = 256)
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Sets the stock symbol of this PersonStock instance
     * @param stockSymbol a String value
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonStock personStock = (PersonStock) o;

        if (id != personStock.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + person.hashCode();
        result = 31 * result + (stockSymbol != null ? stockSymbol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", person='" + person + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                '}';
    }
}
