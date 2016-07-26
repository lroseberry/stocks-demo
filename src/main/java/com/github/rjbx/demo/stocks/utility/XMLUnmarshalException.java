package com.github.rjbx.demo.stocks.utility;

/**
 * This class handles exceptions that occur when executing the unmarshal method of {@code XMLUtils),
 * and extends {@code Throwable}.
 * @author Bob Basmaji
 */
public final class XMLUnmarshalException extends Exception {

    /**
     * @param message describes the exception
     */
    public XMLUnmarshalException(String message) { super(message); }

    /**
     * @param message describes the exception
     * @param cause if this exception is caused by another exception
     */
    public XMLUnmarshalException(String message, Throwable cause) { super(message, cause); }
}

