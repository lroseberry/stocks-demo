package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.DatabasePerson;
import com.github.rjbx.demo.stocks.model.DatabasePersonStock;
import com.github.rjbx.demo.stocks.model.DatabaseStockSymbol;
import com.github.rjbx.demo.stocks.utilities.DatabaseConnectionException;
import com.github.rjbx.demo.stocks.utilities.DatabaseInitializationException;
import com.github.rjbx.demo.stocks.utilities.DatabaseUtils;
import com.github.rjbx.demo.stocks.utilities.PersonServiceException;
import org.hibernate.metamodel.relational.Database;
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
public final class DatabasePersonServiceTest {
    // private fields of this class
    private static final String firstName = "Bob";
    private static final String lastName = "Basmaji";
    private static DatabasePerson person;
    private static DatabaseStockSymbol stockSymbol;
    private PersonService personService;

    /**
     * Sets up logic common to each test
     * @throws DatabaseInitializationException
     */
    @Before
    public void setUp() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        personService = ServiceFactory.createPersonService();
        stockSymbol = new DatabaseStockSymbol("AAPL");
        person = new DatabasePerson();
        person.setFirstName(firstName);
        person.setLastName(lastName);
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
        List<DatabasePerson> personList = personService.getPersons();
        assertTrue("Value returned from getPersons contains objects other than persons",
                personList.get(0) instanceof DatabasePerson);
    }

    /**
     * Verifies that getPersons does not return an empty List
     * @throws PersonServiceException
     */
    @Test
    public void testGetPersonsNegative() throws PersonServiceException {
        List<DatabasePerson> personList = personService.getPersons();
        assertFalse("getPersons returns an empty List", personList.isEmpty());
    }

    /**
     * Verifies that object passed to addOrUpdatePerson is included in list of persons
     * @throws PersonServiceException
     */
    @Test
    public void testAddOrUpdatePersonPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        List<DatabasePerson> personList = personService.getPersons();
        boolean found = false;
        for (DatabasePerson person : personList) {
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
     * Verifies that PersonService instance person list was smaller before AddorUpdatePerson was called
     * @throws PersonServiceException
     */
    @Test
    public void testAddOrUpdatePersonNegative() throws PersonServiceException {
        List<DatabasePerson> personList = personService.getPersons();
        int listSize1 = personList.size();
        personService.addOrUpdatePerson(person);
        personList = personService.getPersons();
        int listSize2 = personList.size();
        assertFalse("PersonService instance person list was not smaller before addOrUpdatePerson was called", listSize1 >= listSize2);
    }

    /**
     * Verifies that objects passed to addstockToPerson are included in their respective database lists
     * @throws PersonServiceException
     */
    @Test
    public void testAddStockToPersonPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        boolean foundSymbol = false;
        List<DatabaseStockSymbol> symbolList = personService.getStockSymbols(person);
        for (DatabaseStockSymbol symbol : symbolList) {
            if (symbol.equals(stockSymbol)) {
                foundSymbol = true;
                break;
            }
        }
        assertTrue("StockSymbol object passed to addStockSymbolToPerson was not found in personService", foundSymbol);
    }

    /**
     * Verifies that person's stock list was smaller before addStockToPerson was called
     * @throws PersonServiceException
     */
    @Test
    public void testAddStockToPersonNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        List<DatabaseStockSymbol> symbolList = personService.getStockSymbols(person);
        int listSize1 = symbolList.size();
        personService.addStockToPerson(stockSymbol, person);
        symbolList = personService.getStockSymbols(person);
        int listSize2 = symbolList.size();
        assertFalse("Person's stock list was not smaller before addStockUpdatePerson was called", listSize1 >= listSize2);
    }

    /**
     * Verifies that multiple calls to getStockSymbol returns the same values
     * @throws PersonServiceException
     */
    @Test
    public void testGetStockSymbolsConsistencyPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        List<DatabaseStockSymbol> stockSymbols = personService.getStockSymbols(person);
        List<DatabaseStockSymbol> stockSymbols2 = personService.getStockSymbols(person);
        for (DatabaseStockSymbol stockSymbol : stockSymbols) {
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
        List<DatabaseStockSymbol> stockSymbols = personService.getStockSymbols(person);
        DatabaseStockSymbol symbol2 = new DatabaseStockSymbol("BEEF PALLELLA");
        for (DatabaseStockSymbol stockSymbol : stockSymbols) {
            boolean removed = stockSymbols.remove(symbol2);
            assertFalse("getStockSymbol returns different values", removed);
        }
        assertFalse("getStockSymbol returns different values", stockSymbols.isEmpty());
    }

    /**
     * Verifies that getStockSymbol returns the correct values
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolAccuracyPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        List<DatabaseStockSymbol> stockSymbols = personService.getStockSymbols(person);
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
        List<DatabaseStockSymbol> stockSymbols = personService.getStockSymbols(person);
        DatabaseStockSymbol symbol = stockSymbols.get(0);
        assertFalse("getStockSymbol returns an invalid value", symbol.getSymbol().equals("sauerkraut^_^"));
    }
}
