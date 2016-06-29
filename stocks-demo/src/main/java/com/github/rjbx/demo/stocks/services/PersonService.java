package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.Person;

import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.utilities.PersonServiceException;
import java.util.List;

/**
 * This interface requires that implementing classes define methods for retrieving and altering a list of people.
 * Classes containing methods for retrieving and altering a list of people may implement this interface.
 * @author Bob Basmaji
 */
public interface PersonService {
    /**
     * Get a list of all people
     * @return a list of Person instances
     * @throws PersonServiceException if a service cannot perform the requested operation
     */
    List<Person> getPersons() throws PersonServiceException;

    /**
     * Add a new person or update the data of an existing person
     * @param person a person object to either update or create
     * @throws PersonServiceException if a service cannot perform the requested operation
     */
    void addOrUpdatePerson(Person person) throws PersonServiceException;

    /**
     * Get a list of all of the stocks a person is interested in
     * @return a list of Strings representing stock symbols
     * @throws PersonServiceException if a service cannot perform the requested operation
     */
    List<String> getStockSymbols(Person person) throws PersonServiceException;

    /**
     * Assign a hobby to a person
     * @param stockSymbol  The stockSymbol to assign
     * @param person The person to whom the stockSymbol is assigned
     * @throws PersonServiceException if a service cannot perform the requested operation
     */
    public void addStockToPerson(String stockSymbol, Person person) throws PersonServiceException;
}
