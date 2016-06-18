package com.github.rjbx.demo.stocks;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * This class uses a factory pattern to instantiate {@code BasicStockService} objects.
 * @author Bob Basmaji
 */
@Immutable
public final class StockServiceFactory {
    /**
     * Constructs a new {@code BasicStockService} instance
     * @return an object implementing the {@code StockService} interface
     */
    public static final StockService createStockService() {
        return new BasicStockService();
    }
}
