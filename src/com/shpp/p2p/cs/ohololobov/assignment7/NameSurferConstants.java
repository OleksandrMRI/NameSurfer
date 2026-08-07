package com.shpp.p2p.cs.ohololobov.assignment7;

/*
 * File: NameSurferConstants.java
 * ------------------------------
 * This file declares several constants that are shared by the
 * different modules in the NameSurfer application.  Any class
 * that implements this interface can use these constants.
 */

public interface NameSurferConstants {

    /**
     * The width of the application window
     */
    int APPLICATION_WIDTH = 800;

    /**
     * The height of the application window
     */
    int APPLICATION_HEIGHT = 400;

    /**
     * The name of the file containing the data
     */
    String NAMES_DATA_FILE = "assets/names-data.txt";

    /**
     * The first decade in the database
     */
    int START_DECADE = 1900;

    /**
     * The number of decades
     */
    int NDECADES = 12;

    /**
     * The maximum rank in the database
     */
    int MAX_RANK = 1000;

    /**
     * The number of pixels to reserve at the top and bottom
     */
    int GRAPH_MARGIN_SIZE = 20;
    /**
     * separator as regular expression to parsing user lines
     */
    String USER_STRING_SEPARATOR_REGEX = "[\\d\\W]";
}
