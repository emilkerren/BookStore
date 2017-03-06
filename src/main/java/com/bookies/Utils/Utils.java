package com.bookies.Utils;

public class Utils {
    public static String toCamelCase(String string) {
        StringBuffer sb = new StringBuffer(string);
        sb.replace(0, 1, string.substring(0, 1).toUpperCase());
        return sb.toString();

    }
}
