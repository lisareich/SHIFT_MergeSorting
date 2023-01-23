package org.lisareich;

import java.util.ArrayList;
import java.util.List;

// Class for CMD args verification
public class CMDUtil {

    private static boolean sortTypeSpecified = true;
    public static String defineSortType(String[] args) {
        if ("-a".equals(args[0]) || "-d".equals(args[0])) {
            return args[0];
        } else if ("-a".equals(args[1]) || "-d".equals(args[1])) {
            return args[1];
        } else {
            sortTypeSpecified = false;
            return "-a";
        }
    }

    public static String defineDataType(String[] args) {
        if ("-i".equals(args[0]) || "-s".equals(args[0])) {
            return args[0];
        } else if ("-i".equals(args[1]) || "-s".equals(args[1])) {
            return args[1];
        } else {
            throw new RuntimeException("You need to pass the following data type to CMD: -i or -s");
        }
    }

    public static String defineResultFileName(String[] args) {
        if ("-i".equals(args[1]) || "-s".equals(args[1]) || "-a".equals(args[1]) || "-d".equals(args[1])) {
            return args[2];
        } else {
            return args[1];
        }
    }

    public static List<String> defineInputFilesNames(String[] args, String resultFileName) {

        if ((args.length <= 3 && sortTypeSpecified) || (args.length <= 2 && !sortTypeSpecified)) {
            throw new RuntimeException("Please specify at least one input file in CMD!");
        }

        List<String> inputFilesNames = new ArrayList<>();

        for (int i = 2; i < args.length; i++) {
            if (!resultFileName.equals(args[i])) {
                inputFilesNames.add(args[i]);
            }
        }

        return inputFilesNames;
    }
}
