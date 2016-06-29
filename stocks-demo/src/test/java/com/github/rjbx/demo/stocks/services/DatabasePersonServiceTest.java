package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.Person;
import com.github.rjbx.demo.stocks.model.PersonStock;
import com.github.rjbx.demo.stocks.utilities.DatabaseConnectionException;
import com.github.rjbx.demo.stocks.utilities.DatabaseInitializationException;
import com.github.rjbx.demo.stocks.utilities.DatabaseUtils;
import com.github.rjbx.demo.stocks.utilities.PersonServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the DatabasePersonService class.
 * @author Bob Basmaji
 */
public class DatabasePersonServiceTest {
    // private fields of this class
    private static final String firstName = "Bob";
    private static final String lastName = "Basmaji";
    private static final int id = 10;
    private static Person person;
    private static final String stockSymbol = "AAPL";
    private static PersonStock personStock;
    private PersonService personService;

    /**
     * Sets up logic common to each test
     * @throws DatabaseInitializationException
     */
    @Before
    public void setUp() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        personService = ServiceFactory.createPersonService();
        person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setId(id);
    }

    /**
     * Tears down residual, potentially problematic logic after each test runs
     * @throws DatabaseInitializationException
     * @throws SQLException
     */
    @After
    public final void tearDownClass() throws DatabaseConnectionException, SQLException {
        DatabaseUtils.getConnection().createStatement().executeUpdate("DROP TABLE people_stocks, people;");
    }

    /**
     * Verifies that person service is available
     */
    @Test
    public void testGetInstance() {
        assertNotNull("Person service is not available", personService);
    }

    /**
     * Verifies that getPersons returns person objects
     * @throws PersonServiceException
     */
    @Test
    public void testGetPersonsPositive() throws PersonServiceException {
        List<Person> personList = personService.getPersons();
        assertTrue("Value returned from getPersons contains objects other than persons",
                personList.get(0) instanceof Person);
    }

    /**
     * Verifies that getPersons does not return an empty List
     * @throws PersonServiceException
     */
    @Test
    public void testGetPersonsNegative() throws PersonServiceException {
        List<Person> personList = personService.getPersons();
        assertFalse("getPersons returns an empty List", personList.isEmpty());
    }

    /**
     * Verifies that object passed addOrUpdatePerson is included in list of persons
     * @throws PersonServiceException
     */
    @Test
    public void testAddOrUpdatePersonPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        List<Person> personList = personService.getPersons();
        boolean found = false;
        for (Person person : personList) {
            if (    person.getLastName().equals(lastName)
                    &&
                    person.getFirstName().equals(firstName)) {
                found = true;
                break;
            }
        }
        assertTrue("Person object passed to addOrUpdatePerson was not found in personService", found);
    }

    /**
     * Verifies that PersonService instance person list was not larger before AddorUpdatePerson was called
     * @throws PersonServiceException
     */
    @Test
    public void testAddOrUpdatePersonNegative() throws PersonServiceException {
        List<Person> personList = personService.getPersons();
        int listSize1 = personList.size();
        personService.addOrUpdatePerson(person);
        int listSize2 = personList.size();
        assertFalse("PersonService instance person list was larger before AddorUpdatePerson was called", listSize1 > listSize2);
    }

    /**
     * Verifies that multiple calls to getStockSymbol returns the same values
     * @throws PersonServiceException
     */
    @Test
    public void testGetStockSymbolsConsistencyPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        List<String> stockSymbols = personService.getStockSymbols(person);
        List<String> stockSymbols2 = personService.getStockSymbols(person);
        for (String stockSymbol : stockSymbols) {
            boolean removed = stockSymbols2.remove(stockSymbol);
            assertTrue("getStockSymbol does not return the same values", removed);
        }
        assertTrue("getStockSymbol does not return the same values", stockSymbols2.isEmpty());
    }

    /**
     * Verifies that multiple calls to getStockSymbol do not return different values
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolsConsistencyNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        List<String> stockSymbols = personService.getStockSymbols(person);
        List<String> stockSymbols2 = personService.getStockSymbols(person);
        for (String stockSymbol : stockSymbols) {
            boolean removed = stockSymbols2.remove(new StringBuilder(stockSymbol).reverse().toString());
            assertFalse("getStockSymbol returns different values", removed);
        }
        assertFalse("getStockSymbol returns different values", stockSymbols2.isEmpty());
    }

    /**
     * Verifies that getStockSymbol returns the correct values
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolAccuracyPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        List<String> stockSymbols = personService.getStockSymbols(person);
        assertTrue("getStockSymbol does not return the correct value", stockSymbols.get(0).equals(stockSymbol));
    }

    /**
     * Verifies that getStockSymbol does not return invalid values
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolAccuracyNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        List<String> stockSymbols = personService.getStockSymbols(person);
        String symbol = stockSymbols.get(0);
        assertFalse("getStockSymbol returns an invalid value", symbol.equals("sauerkraut^_^"));
    }
}
