package com.github.rjbx.demo.stocks.utilities;

import org.apache.http.annotation.Immutable;

/**
 * This class handles exceptions that occur when executing the initializeDatabase method of {@code DatabaseUtils),
 * and extends {@code Throwable}.
 * @author Bob Basmaji
 */
@Immutable
public class DatabaseInitializationException extends Exception {
    
    /**
     * @param message describes the exception
     */
    public DatabaseInitializationException(String message) { super(message); }

    /**
     * @param message describes the exception
     * @param cause if this exception is caused by another exception
     */
    public DatabaseInitializationException(String message, Throwable cause) { super(message, cause); }
}
