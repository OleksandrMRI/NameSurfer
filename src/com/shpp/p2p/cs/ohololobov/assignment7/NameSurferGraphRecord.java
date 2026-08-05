package com.shpp.p2p.cs.ohololobov.assignment7;

import java.awt.*;

/**
 * This record contains information about one nameSurferEntry
 * and color of graph of this nameSurferEntry. It is needed to
 * manage adding and deleing graphs at canvas
 */

public record NameSurferGraphRecord(Color color, NameSurferEntry nameSurferEntry) {
    @Override
    public String toString() {

        return " " + nameSurferEntry + ", " + color;
    }
}
