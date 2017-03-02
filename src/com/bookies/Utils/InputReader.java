package com.bookies.Utils;

import java.util.Scanner;

/**
 * Created by Emil on 2017-02-21.
 */
public class InputReader {
    public static String scan(Scanner scan) {
        String input;
        if (scan.hasNextLine()) {
            input = scan.nextLine();
        } else {
            input = "ERROR";
        }
        System.out.println("Scan: "+input);
        return input.toLowerCase();
    }

    public static int scanInt(Scanner scan) {
        int input;
        if (scan.hasNextInt()) {
            input = scan.nextInt();
            //otherwise next readLine will be newLine
            scan.nextLine();
        } else {
            input = -1;
        }
        System.out.println("Int scan: " + input);
        return input;
    }
}
