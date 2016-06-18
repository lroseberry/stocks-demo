package com.github.rjbx.demo.stocks;

import org.junit.Test;
import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the StockServiceFactory class.
 * @author Bob Basmaji
 */
public final class StockServiceFactoryTest {

    /**
     * Verifies that the return value is an instance of the specified class
     */
    @Test
    public final void testCreateStockServicePositive() {
        // compares method return value with expected result
        assertTrue("The value returned from createStockService is not an instance of StockService interface",
                StockServiceFactory.createStockService() instanceof StockService);
    }

    /**
     * Verifies that the return value is not an instance of the specified class
     */
    @Test
    public final void testCreateStockServiceNegative() {
        // compares method return value with expected result
        assertFalse("The value returned from createStockService is an instance of Calendar class",
                StockServiceFactory.createStockService() instanceof Calendar);
    }
}
