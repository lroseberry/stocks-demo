package com.github.rjbx.demo.stocks.service;

import com.github.rjbx.demo.stocks.utility.StockServiceException;
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
     * @throws StockServiceException
     */
    @Test
    public final void testCreateBasicStockServicePositive() throws StockServiceException {
        // compares method return value with expected result
        assertTrue("The value returned from createStockService is not an instance of StockService interface",
                ServiceFactory.createStockService(ServiceType.BASIC) instanceof StockService);
    }

    /**
     * Verifies that the return value is not an instance of the specified class
     * @throws StockServiceException
     */
    @Test
    public final void testCreateBasicStockServiceNegative() throws StockServiceException {
        // compares method return value with expected result
        assertFalse("The value returned from createStockService is an instance of Calendar class",
                ServiceFactory.createStockService(ServiceType.BASIC) instanceof Calendar);
    }

    /**
     * Verifies that the return value is an instance of the specified class
     * @throws StockServiceException
     */
    @Test
    public final void testCreateDatabaseStockServicePositive() throws StockServiceException {
        // compares method return value with expected result
        assertTrue("The value returned from createStockService is not an instance of StockService interface",
                ServiceFactory.createStockService(ServiceType.DATABASE) instanceof StockService);
    }

    /**
     * Verifies that the return value is not an instance of the specified class
     * @throws StockServiceException
     */
    @Test
    public final void testCreateDatabaseStockServiceNegative() throws StockServiceException {
        // compares method return value with expected result
        assertFalse("The value returned from createStockService is an instance of Calendar class",
                ServiceFactory.createStockService(ServiceType.DATABASE) instanceof Calendar);
    }


    /**
     * Verifies that the return value is an instance of the specified class
     * @throws StockServiceException
     */
    @Test
    public final void testCreateWebStockServicePositive() throws StockServiceException {
        // compares method return value with expected result
        assertTrue("The value returned from createStockService is not an instance of StockService interface",
                ServiceFactory.createStockService(ServiceType.WEB) instanceof StockService);
    }

    /**
     * Verifies that the return value is not an instance of the specified class
     * @throws StockServiceException
     */
    @Test
    public final void testCreateWebStockServiceNegative() throws StockServiceException {
        // compares method return value with expected result
        assertFalse("The value returned from createStockService is an instance of Calendar class",
                ServiceFactory.createStockService(ServiceType.WEB) instanceof Calendar);
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
