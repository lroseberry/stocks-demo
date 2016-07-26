package com.github.rjbx.demo.stocks.model;

import org.apache.http.annotation.Immutable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the Person class.
 * @author Bob Basmaji
 */
@Immutable
public final class DatabasePersonTest {
    // private fields of this class
    private static final String firstName = "Bob";
    private static final String lastName = "Basmaji";
    private static final int id = 10;
    private static DatabasePerson person;

    /**
     * Sets up the logic common to each test
     */
    @Before
    public final void setUp() {
        person = new DatabasePerson();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setId(id);
    }

    /**
     * Verifies that first name matches the value passed to the set method
     */
    @Test
    public final void testGetSetFirstNamePositive() {
        assertTrue("First name does not match the value passed to the set method", 
                firstName.equals(person.getFirstName()));
    }

    /**
     * Verifies that first name does not match the inverse of the value passed to the set method
     */
    @Test
    public final void testGetSetFirstNameNegative() {
        assertFalse("First name matches the inverse of the value passed to the set method",
                new StringBuilder(firstName).reverse().toString().equals(person.getFirstName()));
    }

    /**
    * Verifies that last name matches the value passed to the set method
    */
    @Test
    public final void testGetSetLastNamePositive() {
        assertTrue("Last name does not match the value passed to the set method",
                lastName.equals(person.getLastName()));
    }

    /**
     * Verifies that last name does not match the inverse of the value passed to the set method
     */
    @Test
    public final void testGetSetLastNameNegative() {
        assertFalse("Last name matches the inverse of the value passed to the set method",
                new StringBuilder(lastName).reverse().toString().equals(person.getFirstName()));
    }

    /**
     * Verifies that ID matches value passed to set method
     */
    @Test
    public final void testGetIdPositive() {
        assertTrue("ID does not match value passed to set method", id == person.getId());
    }

    /**
     * Verifies that ID does not match different value from that passed to set method
     */
    @Test
    public final void testGetIdNegative() {
        assertFalse("ID matches a different value from that passed to set method", (id + id * 37) == person.getId());
    }
}
