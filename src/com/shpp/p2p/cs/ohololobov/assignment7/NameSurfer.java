package com.shpp.p2p.cs.ohololobov.assignment7;

/**
 * File: NameSurfer.java
 * ---------------------
 * The program generates a popularity graph for names given to children in American families between 1900 and 2010.
 * You can check a name's popularity by entering name or names in English (separated by a space) into the text field
 * and clicking the button "Graph" or pressing key "Enter". If the name is in the database,
 * a popularity graph will appear; otherwise, the program will display a message stating
 * that the name\s was\were not found. Click the button "Clear" to clear all graphs from the screen.
 * To remove the graph for a specific name\s, type the word "clear" and the needed name or names (separated by a space),
 * for example "clear alex" or "clear alex omar bil" into the text field and press key "Enter".
 * With button "Delete" will be deleted all names printing in text field,
 * use button "Delete" without command-word "clear". If the graph\s of checked name\s is\are at the canvas,
 * it\they will be removed; otherwise, the program will display a message stating
 * that graphs for certain names were not found.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {
    private NameSurferGraph graph;
    private static final String PRESS_ENTER = "Press Enter";
    /**
     * Label of button for drawing a popularity graph for name
     */
    private static final String GRAPH_BUTTON_LABEL = "Graph";
    /**
     * Label of button for drawing a popularity graph for name
     */
    private static final String CLEAR_BUTTON_LABEL = "Clear";
    /**
     * Label of button for drawing a popularity graph for name
     */
    private static final String DELETE_BUTTON_LABEL = "Delete";
    /**
     * Initial text in JTextField nameField, which can be seeing for user by starting program
     */
    private static final String TEXT_FIELD_USER_REQUEST_MESSAGE = "Print any English name and press Graph";
    /**
     * length of JTextField for tipping name
     */
    private static final int TEXT_FIELD_LENGTH = 30;
    /**
     * Text of JLabel links from JTextField nameField, explaining the purpose of the JTextField
     */
    private static final String NAME_LABEL_SIGNATURE = "Name: ";

    private static final String CONFIRM_DIALOG_MESSAGE = "Skip program instruction";
    /**
     * The text for information window, that describe user, what does the program do, in
     */
    private static final String PROGRAM_DESCRIPTION_MSG =
            """
                    Welcome in NameSurfer
                    The program generates a popularity graph
                    for names given to children
                    in American families between 1900 and 2010.
                    
                    
                    """;
    /**
     * The text for information window, that describe user,
     * what needs be doing, to receive popularity graph for names according to decade
     */
    private static final String GRAPH_GETTING_INSTRUCTION_MSG =
            """
                    You can check a name's popularity
                    by entering name or names in English (separated by a space)
                    into the text field and clicking the button "Graph".
                    If the name is in the database, a popularity graph will appear;
                    otherwise, the program will display a message stating
                    that the name was not found.
                    """;
    /**
     * The text for information window, that describe user,
     * what needs be doing, to delete all graphs from the canvas
     */
    private static final String All_GRAPHS_CLEANING_INSTRUCTION_MSG =
            """
                    Click the button "Clear"
                    to clear all graphs from the screen.
                    
                    
                    
                    
                    """;
    /**
     * The text for information window, that describe user,
     * what needs be doing, to delete all graphs from the canvas according to entered names
     */
    private static final String GRAPH_CLEANING_INSTRUCTION_MSG =
            """
                    To remove the graph for a specific name, type the word "сlear"
                    and the needed name or names (separated by a space)
                    for example "clear alex" or "clear alex omar bil"
                    into the text field and press Enter.
                    If the graph\\s of checked name\\s is\\are at the canvas,
                    it\\they will be removed""";
    /**
     * a flag placed before a name or a list of names in a text string to remove the corresponding graphs
     */
    private static final String CLEAR_NAME_FLAG = "clear";
    /**
     * An empty line replaces the keyword "clear" in the user's line to prepare it for name parsing.
     */
    private static final String REPLACING_STRING = "";
    /**
     * The text of message indicates, that the name is missing from the database
     */
    private static final String NO_SUCH_NAME_ERROR = "There is no name '%s' in the database";
    /**
     * Window title for the message indicating the name is missing from the database
     */
    private static final String NO_SUCH_NAME_ERROR_TITLE = "Name Error";
    /**
     * The text of message indicates, that graph the name is missing from the canvas
     */
    private static final String NO_SUCH_GRAPH_ERROR = "There is no graph for name\\s '%s' at the canvas";
    /**
     * Window title for the message indicating the name is missing from the canvas
     */
    private static final String NO_SUCH_GRAPH_ERROR_TITLE = "Graph Error";
    //JTextField fot tipping names to find corresponding date in database or at canvas for making or clearing graphs
    JTextField nameField;
    //JButton to draw popularity graph with corresponding name at the canvas
    JButton graphButton;
    //JButton to clear all graphs from canvas or specified graph with name from nameField
    JButton clearButton;
    //JButton to clear all graphs from canvas or specified graph with name from nameField
    JButton deleteButton;
    /**
     * NameSurferDataBase object, that saves names and popularity ranges of name in HashMap with entries
     */
    private NameSurferDataBase nameSurferDataBase;


    //initial block that calls information window before showing main window of program
    {
        if (JOptionPane.showConfirmDialog(this, CONFIRM_DIALOG_MESSAGE) == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, PROGRAM_DESCRIPTION_MSG);
            JOptionPane.showMessageDialog(this, GRAPH_GETTING_INSTRUCTION_MSG);
            JOptionPane.showMessageDialog(this, All_GRAPHS_CLEANING_INSTRUCTION_MSG);
            JOptionPane.showMessageDialog(this, GRAPH_CLEANING_INSTRUCTION_MSG);
        }
    }

    /* Method: init() */

    /**
     * This method has the responsibility for reading in the database
     * and initializing the interactors at the top of the window.
     */
    @Override
    public void init() {
        try {
            nameSurferDataBase = new NameSurferDataBase(NAMES_DATA_FILE);
        } catch (IOException e) {
            System.err.println("It was Exception from constructor NameSurferDataBase() received. " + e.getMessage());

            return;
        }
        JLabel name = new JLabel(NAME_LABEL_SIGNATURE);
        add(NORTH, name);
        nameField = new JTextField(TEXT_FIELD_USER_REQUEST_MESSAGE, TEXT_FIELD_LENGTH);
        add(NORTH, nameField);
        nameField.setActionCommand(PRESS_ENTER);
        nameField.addActionListener(this);
        graphButton = new JButton(GRAPH_BUTTON_LABEL);
        add(NORTH, graphButton);
        clearButton = new JButton(CLEAR_BUTTON_LABEL);
        add(NORTH, clearButton);
        deleteButton = new JButton(DELETE_BUTTON_LABEL);
        add(NORTH, deleteButton);
        graph = new NameSurferGraph();
        add(graph);

        this.addActionListeners();
    }

    /* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String userText = nameField.getText();
        if (userText != null) {
            String normaliseUserString = userText.trim().toLowerCase();
            boolean startsWithClear = normaliseUserString.startsWith(CLEAR_NAME_FLAG);

            if (e.getActionCommand().equals(PRESS_ENTER) && startsWithClear
                    || e.getSource().equals(deleteButton)) {

                if (startsWithClear) {
                    //clearing graphs, which names are at canvas and return line with names, which names are not at canvas
                    normaliseUserString = normaliseUserString.replaceFirst(CLEAR_NAME_FLAG, REPLACING_STRING);
                }
                String[] names = NameSurferUtils.parsStringToWords(normaliseUserString, USER_STRING_SEPARATOR_REGEX);
                String missingNames = graph.deleteGraphs(names);
                graph.update();
                //notifying user about missin graphs with specific names if those are available
                if (!missingNames.isEmpty()) {
                    JOptionPane.showMessageDialog(this, String.format(NO_SUCH_GRAPH_ERROR, missingNames),
                            NO_SUCH_GRAPH_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }

            } else if (e.getSource() == graphButton || e.getActionCommand().equals(PRESS_ENTER)) {
                String[] entryNames = NameSurferUtils.parsStringToWords(nameField.getText(), USER_STRING_SEPARATOR_REGEX);
                String missingNames = addEntriesToDrawing(entryNames);
                graph.update();
                // throwing error message, if StringBuilder with missed names is not empty
                if (!missingNames.isEmpty()) {
                    JOptionPane.showMessageDialog(this, String.format(NO_SUCH_NAME_ERROR, missingNames), NO_SUCH_NAME_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == clearButton) {
            //deleting all graphs from the canvas
            graph.clear();
            graph.update();
        }
    }

    /**
     * The method receives array with entry names from user string and adds valid entries to HashMap
     * with entries to graph drawing. Those names that are not in database return as a string missingNames
     *
     * @param entryNames array of names from user string
     * @return string with all missing in database names
     */
    private String addEntriesToDrawing(String[] entryNames) {
        StringBuilder missingEntryNames = new StringBuilder();
        //finding entry in database with name of entry from nameField
        NameSurferEntry entry;
        for (String name : entryNames) {
            if ((entry = nameSurferDataBase.findEntry(name)) != null) {
                graph.addEntry(entry);
            } else {
                NameSurferUtils.formStringBuilderWithFormat(name, missingEntryNames);
            }
        }
        return missingEntryNames.toString();
    }
}
