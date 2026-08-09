
package com.shpp.p2p.cs.ohololobov.assignment7;

/**
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;

import java.awt.event.*;
import java.awt.*;
import java.util.HashMap;


public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {
    /**
     * number of horizontal lines in initial decade grid
     */
    private static final int NUM_HORIZONTAL_LINES = 2;
    /**
     * font art for all labels
     */
    private static final String FONT_NAME = "SansSerif-";
    /**
     * font size for all labels
     */
    private static final int FONT_SIZE = 14;
    /**
     * duration of decade in years
     */
    private static final int DECADE = 10;
    /**
     * ArrayList with instances of all NameSurferEntry for drawing graph
     */
    private static final HashMap<String, NameSurferGraphRecord> ENTRIES_GRAPHS = new HashMap<>();
    /**
     * Array with colors for drawing graph
     */
    private static final Color[] COLORS = {Color.BLUE, Color.RED, Color.MAGENTA, Color.BLACK};
    /**
     * shift offset of GRect according to point of popularity,
     * to draw GRect for better visualization of popularity point
     */
    private static final int POINT_TO_GRECT_DEVIATION = 1;
    /**
     * constant calculates size of GRect for better visualization of popularity point
     */
    private static final int POINT_GRECT_SIZE = POINT_TO_GRECT_DEVIATION * 2 + 1;
    /**
     * start color index at GRAPH_COLORS
     */
    private static int colorIndex = 0;

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
    }

    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        ENTRIES_GRAPHS.clear();
        colorIndex = 0;
    }

    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        repaintGrid();
        drawGraphs();
    }

    /**
     * Adds a new NameSurferEntry to the HashMap<> ENTRIES_GRAPHS of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        if (entry != null && !ENTRIES_GRAPHS.containsKey(entry.getName().toLowerCase())) {
            //receiving color for entry graph, that there is yet not use at canvas
            Color color = getColor();
            ENTRIES_GRAPHS.put(entry.getName().toLowerCase(), new NameSurferGraphRecord(color, entry));
        }
    }

    /**
     * The method delete name\s from user line from HashMap with entries to drawing
     * and return string with names that missing at canvas
     *
     * @return line with names, which are not represent at canvas
     */
    public String deleteGraphs(String[] names) {
        StringBuilder missingNames = new StringBuilder();

        for (String name : names) {
            if (ENTRIES_GRAPHS.containsKey(name)) {
                ENTRIES_GRAPHS.remove(name);
            } else {
                NameSurferUtils.formStringBuilderWithFormat(name, missingNames);
            }
        }
        if (ENTRIES_GRAPHS.isEmpty()) colorIndex = 0;
        if (!missingNames.isEmpty()) {
            return missingNames.toString();
        }

        return "";
    }

    /**
     * The method removes all graphic object from canvas and adds grid and signatures of decades
     */
    private void repaintGrid() {
        removeAll();
        drawDecadesGrid();
    }

    /**
     * The method draws all components of initial grid of decades:
     * grid with vertical and horizontal lines and decades signatures
     */
    private void drawDecadesGrid() {
        drawHorizontalLines();
        drawVerticalLines();
        drawDecadesSignatures();
    }

    /**
     * The method draws horizontal lines of decade grid at the canvas
     */
    private void drawHorizontalLines() {
        double lineOffsetY;

        for (int i = 0; i < NUM_HORIZONTAL_LINES; i++) {
            //calculating offset y of horizontal lines of decade grid
            lineOffsetY = i == 0 ? GRAPH_MARGIN_SIZE : getHeight() - GRAPH_MARGIN_SIZE;
            drawLine(0, lineOffsetY, getWidth(), lineOffsetY, Color.BLACK);
        }
    }

    /**
     * The method draws vertical lines of decade grid at the canvas
     */
    private void drawVerticalLines() {
        double lineOffsetX;
        for (int decadeIndex = 0; decadeIndex < NDECADES; decadeIndex++) {

            //calculating offset x of vertical lines of decade grid
            lineOffsetX = (double) getWidth() / NDECADES * decadeIndex;

            drawLine(lineOffsetX, 0, lineOffsetX, getHeight(), Color.BLACK);
        }
    }

    /**
     * The method draws the line at the canvas
     *
     * @param firstPointX  offset x first endpoint of line
     * @param firstPointY  offset x first endpoint of line
     * @param secondPointX offset x second endpoint of line
     * @param secondPointY offset x second endpoint of line
     * @param color        color of line
     */
    private void drawLine(double firstPointX, double firstPointY, double secondPointX, double secondPointY, Color color) {
        GLine line = new GLine(firstPointX, firstPointY, secondPointX, secondPointY);
        line.setColor(color);
        add(line);
    }

    /**
     * The method draws decades signatures from 1900 to 2010 at еру bottom of the canvas,
     * aligned with the vertical decade lines.
     */
    private void drawDecadesSignatures() {
        int decade = START_DECADE;
        String decadeAsString;

        for (int decadeIndex = 0; decadeIndex < NDECADES; decadeIndex++) {
            decadeAsString = decade + "";
            //calculating x offset of signature of each decade
            double decadeSignatureOffsetX = (double) getWidth() / NDECADES * decadeIndex;

            //drawing signature of decade
            drawSignature(decadeAsString, decadeSignatureOffsetX, getHeight(), Color.BLACK);

            //calculating next decade
            decade += DECADE;
        }
    }

    /**
     * The method draws GLabel with base point specified offsets x and y at canvas
     *
     * @param signatureName             text of GLabel
     * @param signatureBasePointOffsetX x coordinate of point to calculating baseline position of GLabel
     * @param signatureBasePointOffsetY y coordinate of point to calculating baseline position of GLabel
     */
    private void drawSignature(String signatureName,
                               double signatureBasePointOffsetX,
                               double signatureBasePointOffsetY,
                               Color color) {
        GLabel signature = new GLabel(signatureName);
        signature.setFont(FONT_NAME + FONT_SIZE);
        signature.setColor(color);

        add(signature, signatureBasePointOffsetX + signature.getDescent(), signatureBasePointOffsetY - signature.getDescent());
    }

    /**
     * The method generates new random color, checks if this color is already contained at ArayList with colors
     * or is it  BLACK or WHITE (grid and canvas colors),
     * and save in ArayList with colors only original color
     */
    private Color getColor() {
        return COLORS[colorIndex++ % COLORS.length];
    }

    /**
     * The method draws entity graphs at canvas aus Arraylist<NameSurferEntry>
     */
    private void drawGraphs() {
        for (NameSurferGraphRecord recordValue : ENTRIES_GRAPHS.values()) {
            drawGraph(recordValue.nameSurferEntry(), recordValue.color());
        }
    }

    /**
     * The method draws graph of one entry
     * First, it places the popularity level points,
     * then connects them with lines,
     * and finally creates labels at each level.
     *
     * @param nameSurferEntry object NameSurferEntry that must be represented as graph
     * @param color           color of graph
     */
    private void drawGraph(NameSurferEntry nameSurferEntry, Color color) {
        //creating array of all points of rank of name according to decade
        GPoint[] rankPoints = getRankPointsArray(nameSurferEntry);
        //drawing rankPoints of graph at canvas
        drawPoints(rankPoints, color);
        //drawing graph edge at canvas
        drawGraphLines(rankPoints, color);
        //drawing namesSignature of entry
        drawNames(nameSurferEntry, rankPoints, color);
    }

    /**
     * The method fills array of GPoints of ranks according to decade for one entry
     *
     * @param nameSurferEntry object of NameSurferEntry, whose graph needs to be drawn
     * @return GPoint[] of all points of ranks
     */
    private GPoint[] getRankPointsArray(NameSurferEntry nameSurferEntry) {
        GPoint[] points = new GPoint[NDECADES];
        double distanceX = (double) getWidth() / NDECADES;
        double offSetX = 0;

        for (int decadeIndex = 0; decadeIndex < NDECADES; decadeIndex++) {
            if (nameSurferEntry.getRank(decadeIndex) != 0) {
                double offSetY = (double) (getHeight() - GRAPH_MARGIN_SIZE * 2) / MAX_RANK * nameSurferEntry.getRank(decadeIndex) + GRAPH_MARGIN_SIZE;
                points[decadeIndex] = new GPoint(offSetX, offSetY);
            } else {
                points[decadeIndex] = new GPoint(offSetX, getHeight() - GRAPH_MARGIN_SIZE);
            }
            offSetX += distanceX;
        }

        return points;
    }

    /**
     * The method draws points to the canvas
     *
     * @param points array of GPoints according to decade ranks
     * @param color  color of graph of entry
     */
    private void drawPoints(GPoint[] points, Color color) {
        for (GPoint point : points) {
            drawPoint(point, color);
        }
    }

    /**
     * The method draws point at the canvas as a square,
     * with the intersection of its diagonals at a given point
     *
     * @param point GPoint of rank
     * @param color color of graph of entry
     */
    private void drawPoint(GPoint point, Color color) {
        GRect gRect = new GRect(point.getX() - POINT_TO_GRECT_DEVIATION, point.getY() - POINT_TO_GRECT_DEVIATION, POINT_GRECT_SIZE, POINT_GRECT_SIZE);
        gRect.setColor(color);
        gRect.setFilled(true);
        add(gRect);
    }

    /**
     * The method draws edges of graph of entry,
     * connecting the points representing the name's popularity rank in sequence
     *
     * @param points array of GPoints according to decade ranks
     * @param color  color of graph of entry
     */
    private void drawGraphLines(GPoint[] points, Color color) {
        int pointsLength = points.length;

        for (int i = 0; i < pointsLength; i++) {
            if (i < pointsLength - 1) {
                drawLine(points[i].getX(), points[i].getY(), points[i + 1].getX(), points[i + 1].getY(), color);
            }
        }
    }

    /**
     * The method draws signature of name of entry
     *
     * @param entry      object of NameSurferEntry, whose graph needs to be drawn
     * @param rankPoints array of GPoints according to decade ranks
     * @param color      color of graph of entry
     */
    private void drawNames(NameSurferEntry entry, GPoint[] rankPoints, Color color) {
        for (int decadeIndex = 0; decadeIndex < rankPoints.length; decadeIndex++) {
            String entrySignature = entry.getRank(decadeIndex) != 0
                    ? entry.getName() + " " + entry.getRank(decadeIndex)
                    : entry.getName() + " *";

            drawSignature(entrySignature, rankPoints[decadeIndex].getX(), rankPoints[decadeIndex].getY(), color);
        }
    }

    /* Implementation of the ComponentListener interface */

    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}


