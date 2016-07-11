package com.github.rjbx.demo.stocks.services;

import org.apache.http.annotation.Immutable;
import org.junit.Test;
import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the ServiceFactory class.
 * @author Bob Basmaji
 */
@Immutable
public final class ServiceFactoryTest {

    /**
     * Verifies that the return value is an instance of the specified class
     */
    @Test
    public final void testCreateBasicStockServicePositive() {
        // compares method return value with expected result
        assertTrue("The value returned from createStockService is not an instance of StockService interface",
                ServiceFactory.createStockService("basic") instanceof StockService);
    }

    /**
     * Verifies that the return value is not an instance of the specified class
     */
    @Test
    public final void testCreateBasicStockServiceNegative() {
        // compares method return value with expected result
        assertFalse("The value returned from createStockService is an instance of Calendar class",
                ServiceFactory.createStockService("basic") instanceof Calendar);
    }

    /**
     * Verifies that the return value is an instance of the specified class
     */
    @Test
    public final void testCreateDatabaseStockServicePositive() {
        // compares method return value with expected result
        assertTrue("The value returned from createStockService is not an instance of StockService interface",
                ServiceFactory.createStockService("database") instanceof StockService);
    }

    /**
     * Verifies that the return value is not an instance of the specified class
     */
    @Test
    public final void testCreateDatabaseStockServiceNegative() {
        // compares method return value with expected result
        assertFalse("The value returned from createStockService is an instance of Calendar class",
                ServiceFactory.createStockService("database") instanceof Calendar);
    }

    /**
     * Verifies that the return value is an instance of the specified class
     */
    @Test
    public final void testCreatePersonServicePositive() {
        // compares method return value with expected result
        assertTrue("The value returned from createPersonService() is not an instance of StockService interface",
                ServiceFactory.createPersonService() instanceof PersonService);
    }

    /**
     * Verifies that the return value is not an instance of the specified class
     */
    @Test
    public final void testCreatePersonServiceNegative() {
        // compares method return value with expected result
        assertFalse("The value returned from createPersonService() is an instance of Calendar class",
                ServiceFactory.createPersonService() instanceof Calendar);
    }
}
