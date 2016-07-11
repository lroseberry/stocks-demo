package com.github.rjbx.demo.stocks.utilities;

import org.apache.http.annotation.Immutable;

/**
 * This class handles exceptions that occur when executing the marshal method of {@code XMLUtils),
 * and extends {@code Throwable}.
 * @author Bob Basmaji
 */
@Immutable
public final class XMLMarshalException extends Exception {

    /**
     * @param message describes the exception
     */
    public XMLMarshalException(String message) { super(message); }

    /**
     * @param message describes the exception
     * @param cause if this exception is caused by another exception
     */
    public XMLMarshalException(String message, Throwable cause) { super(message, cause); }
}

