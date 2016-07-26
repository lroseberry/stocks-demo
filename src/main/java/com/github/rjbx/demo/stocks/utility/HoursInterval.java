package com.github.rjbx.demo.stocks.utility;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * This enum implements the {@code Interval} interface.
 * Elements enumerate units, returned by public constant fields of the {@code Calendar} class, and the desired amount of each unit.
 * These values are passed to the add() method of the {@code Calendar} class in order to increment time by the specified interval.
 * @author Bob Basmaji
 */
@Immutable
public enum HoursInterval implements Interval {
    /** An enumeration for one hour in a day */
    HOUR("HOUR")            { public final int amount() { return 1; } },

    /** An enumeration for half of one day in a year */
    HALF_DAY("HALF_DAY")    { public final int amount() { return 12; } },

    /** An enumeration for one day in a year */
    DAY("DAY")              { public final int amount() { return 24; } },

    /** An enumeration for one week in a year */
    WEEK("WEEK")            { public final int amount() { return 168; } },

    /** An enumeration for one week in a year */
    MONTH("MONTH")            { public final int amount() { return 672; } };

    // private fields of this class
    private final String symbol;

    /**
     * Constructs a new {@code BasicIntervalEnum} instance
     * @param symbol a {@code String} representation for an {@code enum} element
     */
    HoursInterval(String symbol) { this.symbol = symbol; }

    /**
     * @return the field of this instance representing an {@code enum} element
     */
    @Override
    public final String toString() { return this.symbol; }
}
