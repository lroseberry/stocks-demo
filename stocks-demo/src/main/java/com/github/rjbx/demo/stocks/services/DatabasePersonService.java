package com.github.rjbx.demo.stocks.services;

import com.github.rjbx.demo.stocks.model.Person;
import com.github.rjbx.demo.stocks.model.PersonStock;
import com.github.rjbx.demo.stocks.utilities.DatabaseUtils;
import com.github.rjbx.demo.stocks.utilities.PersonServiceException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a concrete implementation of PersonService that reads data from a database.
 */
public class DatabasePersonService implements PersonService {
    /**
     * Gets a list of all persons stored in the "people" database
     * @return a list of Person instances
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final List<Person> getPersons() throws PersonServiceException {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        List<Person> persons = null;
        Transaction transaction = null;
        try {
            // assigns all instances of Person within session to Criteria
            // then converts Criteria to a list of the criteria type which is Person.class
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Person.class);

            @SuppressWarnings("unchecked") List<Person> list = criteria.list();
            persons = list;
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new PersonServiceException("Could not get Person data. " + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
        return persons;
    }

    /**
     * Adds a new person or updates the data of an existing person
     * @param person a person object to either update or create
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final void addOrUpdatePerson(Person person) throws PersonServiceException {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of Person if already exists within table
            // or adds as last row of people table
            transaction = session.beginTransaction();
            session.saveOrUpdate(person);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }

    /**
     * Gets a list of all of the stocks a person is interested in
     * @return a list of Strings representing stock symbols
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final List<String> getStockSymbols(Person person) throws PersonServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        List<String> stocks = new ArrayList<>();
        try {
            // assigns from the session to Criteria all instances of PersonStock containing the person parameter value
            // then converts Criteria to a list of the criteria type which is PersonStock.class
            // and gets from each PersonStock instance the associated stock symbol
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonStock.class);
            criteria.add(Restrictions.eq("person", person));

            @SuppressWarnings("unchecked") List<PersonStock> list = criteria.list();
            for (PersonStock personStock : list) {
                stocks.add(personStock.getStockSymbol());
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
        return stocks;
    }

    /**
     * Assigns a stock to a person
     * @param stockSymbol  The stockSymbol to assign
     * @param person The person to whom the stockSymbol is assigned
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final void addStockToPerson(String stockSymbol, Person person) throws PersonServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of PersonStock if already exists within table
            // or adds as last row of people table
            transaction = session.beginTransaction();
            PersonStock personStock = new PersonStock();
            personStock.setStockSymbol(stockSymbol);
            personStock.setPerson(person);
            session.saveOrUpdate(personStock);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }
}
