package com.github.rjbx.demo.stocks.utility;

import org.apache.http.annotation.Immutable;

/**
 * This class handles exceptions that occur when executing the getConnection method of {@code DatabaseUtils),
 * and extends {@code Throwable}.
 * @author Bob Basmaji
 */
@Immutable
public final class DatabaseConnectionException  extends Exception {

    /**
     * @param message describes the exception
     * @param cause if this exception is caused by another exception
     */
    public DatabaseConnectionException(String message, Throwable cause) { super(message, cause); }
}
