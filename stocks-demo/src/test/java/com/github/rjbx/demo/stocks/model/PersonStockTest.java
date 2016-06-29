package com.github.rjbx.demo.stocks.model;

import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the PersonStock class.
 * @author Bob Basmaji
 */
@Immutable
public class PersonStockTest {
    // private fields of this class
    private static final String firstName = "Bob";
    private static final String lastName = "Basmaji";
    private static final int id = 10;
    private static Person person;
    private static final String stockSymbol = "AAPL";
    private static PersonStock personStock;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setUp() {
        person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setId(id);
        personStock = new PersonStock();
        personStock.setId(id);
        personStock.setPerson(person);
        personStock.setStockSymbol(stockSymbol);
    }

    /**
     * Verifies that getPerson return value matches the value passed into the corresponding setter method
     */
    @Test
    public final void testGetSetPersonPositive() {
        assertTrue("getPerson return value does not match the value passed into the setter method", person.equals(personStock.getPerson()));
    }

    /**
     * Verifies that getPerson return value does not match a value other than that which was passed into setter method
     */
    @Test
    public final void testGetSetPersonNegative() {
        Person person2 = new Person();
        assertFalse("getPerson return value matches a value other than that which was passed into setter method", person2.equals(personStock.getPerson()));
    }

    /**
     * Verifies that getStockSymbol return value matches the value passed into the corresponding setter method
     */
    @Test
    public final void testGetSetStockSymbolPositive() {
        assertTrue("getStockSymbol return value does not match the value passed into the setter method", stockSymbol.equals(personStock.getStockSymbol()));
    }

    /**
     * Verifies that getStockSymbol return value does not match a value other than that which was passed into setter method
     */
    @Test
    public final void testGetSetStockSymbolNegative() {
        assertFalse("getStockSymbol return value matches a value other than that which was passed into setter method", "banana".equals(personStock.getStockSymbol()));
    }

    /**
     * Verifies that getId return value matches the value passed into the corresponding setter method
     */
    @Test
    public final void testGetSetIdPositive() {
        assertTrue("getId return value does not match the value passed into the setter method", id == personStock.getId());
    }

    /**
     * Verifies that getId return value does not match a value other than that which was passed into setter method
     */
    @Test
    public final void testGetSetIdNegative() {
        assertFalse("getId return value matches a value other than that which was passed into setter method", (id + id *37) == personStock.getId());
    }

    /**
     * Verifies that same objects are considered equal
     */
    @Test
    public final void testEqualsPositive() {
        PersonStock personStock2 = new PersonStock(person, stockSymbol);
        personStock2.setId(id);
        assertTrue("Same objects are not considered equal", personStock.equals(personStock2));
    }

    /**
     * Verifies that different objects are not considered equal
     */
    @Test
    public final void testEqualsNegative() {
        PersonStock personStock2 = new PersonStock(person, stockSymbol);
        assertFalse("Different objects are considered equal", personStock.equals(personStock2));
    }

    /**
     * Verifies that toString includes the field values of the object
     */
    @Test
    public final void testToStringPositive() {
        assertTrue("toString does not include the field values of the object",
                personStock.toString().equals("Person{" +
                "id=" + personStock.getId() +
                ", person='" + personStock.getPerson() + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                '}'));
    }

    /**
     * Verifies that toString does not include the inverse of each field value
     */
    @Test
    public final void testToStringNegative() {
        assertFalse("toString includes the inverse of each field value",
                personStock.toString().equals("Person{" +
                        "id=" + new StringBuilder(personStock.getId()).reverse().toString() +
                        ", person='" + new StringBuilder(personStock.getPerson().toString()).reverse().toString() + '\'' +
                        ", stockSymbol='" + new StringBuilder(stockSymbol).reverse().toString() + '\'' +
                        '}'));
    }
}
