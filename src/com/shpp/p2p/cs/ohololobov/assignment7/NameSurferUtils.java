package com.shpp.p2p.cs.ohololobov.assignment7;

/**
 * This class includes common service logic for many classes of program, that can not be for specified classes added
 */
public class NameSurferUtils {
    /**
     * The method appends str to StringBuilder with separator ", "
     *
     * @param str str that is missing in database
     * @param sb  StringBuilder to append missing str.
     */
    static void formStringBuilderWithFormat(String str, StringBuilder sb) {
        if (!sb.isEmpty()) {
            sb.append(", ").append(str.toUpperCase());
        } else {
            sb.append(str.toUpperCase());
        }
    }

    /**
     * The method pars name\s from user line
     */
    public static String[] parsStringToWords(String userText, String regexSeparator) {
        return userText.trim().toLowerCase().split(regexSeparator);
    }
}
