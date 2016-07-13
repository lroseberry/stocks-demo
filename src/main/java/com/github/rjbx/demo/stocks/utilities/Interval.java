package com.github.rjbx.demo.stocks.utilities;

import org.apache.http.annotation.Immutable;

/**
 * This interface requires that implementing classes define methods for getting the unit of time and amount of units for an {@code enum}.
 * Enumerations for intervals of time may implement this interface.
 * Implementing classes may extend the basic implementation.
 * @see HoursInterval
 * @author Bob Basmaji
 */
@Immutable
public interface Interval {

    /**
     * @return the amount of the unit desired
     */
    int amount();
}
