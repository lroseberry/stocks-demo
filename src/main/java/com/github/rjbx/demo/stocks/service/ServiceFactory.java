package com.github.rjbx.demo.stocks.service;

import com.github.rjbx.demo.stocks.utility.StockServiceException;
import org.apache.http.annotation.Immutable;

/**
 * This class uses a factory pattern to instantiate service objects.
 * @author Bob Basmaji
 */
@Immutable
public final class ServiceFactory {

    /**
     * Hides the constructor because this class contains only static methods
     */
    private ServiceFactory() {
    }

    /**
     * Constructs a new {@code StockService} instance
     * @return an object implementing the {@code StockService} interface
     */
    public static final StockService createStockService(ServiceType type) throws StockServiceException {
        if (type.equals(ServiceType.BASIC)) {
            return new BasicStockService();
        } else if (type.equals(ServiceType.DATABASE)) {
            return new DatabaseStockService();
        } else if (type.equals(ServiceType.WEB)) {
            return new WebStockService();
        } else {
            throw new StockServiceException("Argument specifies an invalid ServiceType");
        }
    }

    /**
     * Constructs a new {@code PersonService} instance
     * @return an object implementing the {@code PersonService} interface
     */
    public static final PersonService createPersonService() {
        return new DatabasePersonService();
    }
}
