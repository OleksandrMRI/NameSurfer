package com.shpp.p2p.cs.ohololobov.assignment7;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NameSurferDataBase implements NameSurferConstants {

    /**
     * collection for entries with keys as entryNames and values as enty line
     */
    private static final HashMap<String, NameSurferEntry> ENTRIES = new HashMap<>();

    /**
     * throwing message, if file is not find
     */
    private static final String FILE_NOT_FIND_MESSAGE = "Can not find the file with path: ";

    /**
     * number of parts to split the line from file to find entry name
     */
    private static final int NUMBER_OF_PARTS = 2;

    /**
     * index of entry name in array after line splitting
     */
    private static final int NAME_INDEX = 0;
    /**
     * divider between name and ranks in line from file
     */
    public static final String DIVIDER = " ";

    /* Constructor: NameSurferDataBase(filename) */

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String path) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            String name;
            while ((line = br.readLine()) != null) {
                name = getName(line);
                ENTRIES.put(name.toLowerCase(Locale.ROOT), new NameSurferEntry(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FIND_MESSAGE + path + " " + e.getMessage());
            throw e;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String getName(String line) {
        return line.split(DIVIDER, NUMBER_OF_PARTS)[NAME_INDEX];
    }

    /* Method: findEntry(name) */

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        return ENTRIES.get(name);
    }
}

