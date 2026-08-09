package com.shpp.p2p.cs.ohololobov.assignment7;

/**
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

public class NameSurferEntry implements NameSurferConstants {

    private final String[] entryArray;
    private final String name;
    private final int[] ranks =new int[NDECADES];

    /* Constructor: NameSurferEntry(line) */

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        entryArray = line.split(" ");

        this.name = entryArray[0];

        for (int i = 1; i < entryArray.length; i++) {
            this.ranks[i-1]=Integer.parseInt(entryArray[i]);
        }
    }

    /* Method: getName() */

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return name;
    }

    /* Method: getRank(decade) */

    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decadeIndex) {
        return ranks[decadeIndex];
    }

    /* Method: toString() */

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(entryArray[0]).append(" ");
        for (int decade = 0; decade < NDECADES; decade++) {
            String decadeRank = entryArray[decade + 1];
            if (decade > 0) {
                sb.append(" ").append(decadeRank);
            } else {
                sb.append("[").append(decadeRank);
            }
        }

        return sb.append("]").toString();
    }
}

