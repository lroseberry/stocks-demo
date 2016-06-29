package com.github.rjbx.demo.stocks.services;

import org.apache.http.annotation.Immutable;

/**
 * This class uses a factory pattern to instantiate service objects.
 * @author Bob Basmaji
 */
@Immutable
public final class ServiceFactory {
    /**
     * Constructs a new {@code StockService} instance
     * @return an object implementing the {@code StockService} interface
     */
    public static final StockService createStockService(String type) {
        switch(type) {
        case("basic"):
            return new BasicStockService();
        case("database"):
            return new DatabaseStockService();
        default:
            throw new RuntimeException();
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
