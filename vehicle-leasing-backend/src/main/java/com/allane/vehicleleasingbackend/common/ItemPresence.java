package com.allane.vehicleleasingbackend.common;

/**
 * Enumeration denoting on which side of a two-way comparison an item is present.
 */
public enum ItemPresence {
    /**
     * Indicate that item is present on left-hand side of comparison.
     */
    LEFT,
    /**
     * Indicate that item is present on both sides of comparison.
     */
    BOTH,
    /**
     * Indicate that item is present on right-hand side of comparison.
     */
    RIGHT
}
