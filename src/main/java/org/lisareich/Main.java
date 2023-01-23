package org.lisareich;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // CMD Settings
        String sortType = CMDUtil.defineSortType(args);
        String dataType = CMDUtil.defineDataType(args);
        String resultFileName = CMDUtil.defineResultFileName(args);
        List<String> inputFilesNames = CMDUtil.defineInputFilesNames(args, resultFileName);

        List<File> inputFiles = new ArrayList<>();

        File resultFile = new File(Sorting.pathNameMain + resultFileName);

        // Converting String FileNames to Files, and adding to the List
        for (String name : inputFilesNames) {
            File file = new File(Sorting.pathNameMain + name);
            inputFiles.add(file);
        }

        // Sorting
        Sorting.mergeSort(inputFiles, resultFile, dataType, sortType);
    }
}