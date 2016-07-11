package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.DatabasePerson;
import com.github.rjbx.demo.stocks.model.DatabaseStockSymbol;
import com.github.rjbx.demo.stocks.utilities.*;
import org.apache.http.annotation.Immutable;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the PersonService class.
 * This class utilizes parameters defined with the {@code Parameterized} tag.
 * @author Bob Basmaji
 */
@Immutable
@RunWith(Parameterized.class)
public final class PersonServiceTest {
    // fields of this class
    private PersonService personService;
    private static DatabaseStockSymbol stockSymbol;
    private static DatabasePerson person;

    /**
     * Constructs a new StockServiceTest instance with the specified parameter
     * @param personService parameter defined with Parameterized tag
     */
    public PersonServiceTest(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Sets up logic common to each test
     * @throws DatabaseInitializationException
     */
    @Before
    public final void setUp() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        stockSymbol = new DatabaseStockSymbol("AAPL");
        person = new DatabasePerson();
        person.setFirstName("Grover");
        person.setLastName("Cleveland");
    }

    /**
     * Verifies that the return value contains valid {@code Person} instances
     * @throws PersonServiceException
     */
    @Test
    public final void testGetPersonsPositive() throws PersonServiceException {
        assertTrue("First element of list returned by getPersons() is not a person",
                personService.getPersons().get(0) instanceof DatabasePerson);
    }

    /**
     * Verifies that the return value is not an empty list
     * @throws PersonServiceException
     */
    @Test
    public final void testGetPersonsNegative() throws PersonServiceException {
        assertFalse("Return value of getPersons() is an empty list",
                personService.getPersons().isEmpty());
    }

    /**
     * Verifies that the first element of list returned by getPersons() equals the parameter value of addOrUpdatePerson() called on same instance
     * @throws PersonServiceException
     */
    @Test
    public final void testAddOrUpdatePersonPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        List<DatabasePerson> persons = personService.getPersons();
        assertTrue("First element of list returned by getPersons() does not equal the parameter value of addOrUpdatePerson() called on same instance",
                        persons.get(persons.size() - 1).equals(person));
    }

    /**
     * Verifies that the first element of list returned by getPersons() contains an element other than that which was not added
     * @throws PersonServiceException
     */
    @Test
    public final void testAddOrUpdatePersonNegative() throws PersonServiceException {
        List<DatabasePerson> persons = personService.getPersons();
        assertFalse("First element of list returned by getPersons() equals the parameter value of addOrUpdatePerson() called on same instance",
                persons.get(persons.size() - 1).equals(person));
    }

    /**
     * Verifies that the return value contains the correct number of elemenets
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolsPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        personService.addStockToPerson(new DatabaseStockSymbol("LPAA"), person);
        assertTrue("The first element getStockSymbols does not equal the correct stock symbol",
                personService.getStockSymbols(person).size() == 2);
    }

    /**
     * Verifies that the first element of the return value is not an invalid value
     * @throws PersonServiceException
     */
    @Test
    public final void testGetStockSymbolsNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        personService.addStockToPerson((new DatabaseStockSymbol("LPAA")), person);
        assertFalse("The first element getStockSymbols does not equal the correct stock symbol",
                personService.getStockSymbols(person).size() == 1);
    }

    /**
     * Verifies that the first element of the return value is the correct stock symbol
     * @throws PersonServiceException
     */
    @Test
    public final void testAddStockToPersonPositive() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        assertTrue("The first element getStockSymbols does not equal the correct stock symbol",
                personService.getStockSymbols(person).get(0).equals(stockSymbol));
    }

    /**
     * Verifies that the first element of the return value is not an invalid value
     * @throws PersonServiceException
     */
    @Test
    public final void testAddStockToPersonNegative() throws PersonServiceException {
        personService.addOrUpdatePerson(person);
        personService.addStockToPerson(stockSymbol, person);
        assertFalse("The first element getStockSymbols equals an invalid value",
                personService.getStockSymbols(person).get(0).equals("banana"));
    }

    /**
     * Defines parameters to be used in each test
     * @return collection of parameters
     */
    @Parameterized.Parameters
    public static final Collection<Object[]> instancesToTest() {
        return Arrays.asList(
                new Object[]{ServiceFactory.createPersonService()},
                new Object[]{ServiceFactory.createPersonService()} // placeholder
        );
    }
}
