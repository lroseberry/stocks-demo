package com.github.rjbx.demo.stocks.utilities;

import org.apache.http.annotation.Immutable;

/**
 * This class handles exceptions of classes implementing the {@code StockService} interface,
 * and extends {@code Throwable}.
 * @author Bob Basmaji
 */
@Immutable
public final class StockServiceException extends Exception {

    /**
     * @param message describes the exception
     */
    public StockServiceException(String message) { super(message ); }

    /**
     * @param message describes the exception
     * @param cause if this exception is caused by another exception
     */
    public StockServiceException(String message, Throwable cause) { super(message, cause); }
}
