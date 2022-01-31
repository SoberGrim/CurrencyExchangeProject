package org.test.currency.utils;

public class Color {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    private Color() {
    }

    public static String textBlue(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }

    public static String textYellow(String text) {
        return ANSI_YELLOW + text + ANSI_RESET;
    }

    public static String textCyan(String text) {
        return ANSI_CYAN + text + ANSI_RESET;
    }
}
