package com.github.rjbx.demo.stocks.utilities;

import org.apache.http.annotation.Immutable;

/**
 * This class handles exceptions of classes implementing the {@code PersonService} interface,
 * and extends {@code Throwable}.
 * @author Bob Basmaji
 */
@Immutable
public final class PersonServiceException extends Exception {

    /**
     * @param message describes the exception
     */
    public PersonServiceException(String message) {
        super(message);
    }

    /**
     * @param message describes the exception
     * @param cause   if this exception is caused by another exception
     */
    public PersonServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}