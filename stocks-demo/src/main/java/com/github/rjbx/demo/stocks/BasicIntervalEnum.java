package com.github.rjbx.demo.stocks;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Calendar;

/**
 * This enum implements the {@code IntervalEnum} interface.
 * Elements enumerate units, returned by public constant fields of the {@code Calendar} class, and the desired amount of each unit.
 * These values are passed to the add() method of the {@code Calendar} class in order to increment time by the specified interval.
 * @author Bob Basmaji
 */
@Immutable
public enum BasicIntervalEnum implements IntervalEnum {
    /** An enumeration for one hour in a day */
    HOUR("HOUR")            { public final int unit() { return Calendar.HOUR_OF_DAY; } },

    /** An enumeration for half of one day in a year */
    HALF_DAY("HALF_DAY")    { public final int unit() { return (Calendar.HOUR_OF_DAY); }
                    @Override public final int amount() { return 12; }                 },

    /** An enumeration for one day in a year */
    DAY("DAY")              { public final int unit() { return Calendar.DAY_OF_YEAR; } },

    /** An enumeration for one week in a year */
    WEEK("WEEK")            { public final int unit() { return Calendar.WEEK_OF_YEAR; } },

    /** An enumeration for one month */
    MONTH("MONTH")          { public final int unit() { return Calendar.MONTH; } },

    /** An enumeration for half of one year */
    HALF_YEAR("HALF_YEAR")  { public final int unit() { return Calendar.MONTH; }
                    @Override public final int amount() { return 6; }          },

    /** An enumeration for one year */
    YEAR("YEAR")            { public final int unit() { return Calendar.YEAR; } };

    // private fields of this class
    private final String symbol;

    /**
     * Constructs a new {@code BasicIntervalEnum} instance
     * @param symbol a {@code String} representation for an {@code enum} element
     */
    BasicIntervalEnum(String symbol) { this.symbol = symbol; }

    /**
     * @return the field of this instance representing an {@code enum} element
     */
    @Override
    public final String toString() { return this.symbol; }

    /**
     * @return a value of {@code 1}, unless overridden in an element-specific implementation of this method
     */
    public int amount() { return 1; }
}
