package org.lisareich;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

// Sorting method is implemented here
public class Sorting {

    public static String pathNameMain = System.getProperty("user.dir") + File.separator;

    public static void mergeSort(List<File> inputFiles, File resultFile, String dataType, String sortType) {

        if (inputFiles.size() == 1) {

            if (inputFiles.get(0).getName().startsWith("res")) {
                SortUtil.createFinalSortedFile(inputFiles.get(0), resultFile, sortType);
                SortUtil.deleteTmpFiles();
            } else {
                try {
                    File emptyFile = new File(pathNameMain + "emptyFile");
                    emptyFile.createNewFile();
                    inputFiles.add(emptyFile);
                    mergeSort(inputFiles, resultFile, dataType, sortType);
                } catch (IOException ex) {
                    System.out.println("File error!");
                }

            }

            System.out.println("Merged file has been created.");
            System.exit(0);

        } else {
            List<File> updatedInputFiles = new ArrayList<>();
            int listSize;
            boolean listSizeEven;

            if (inputFiles.size() % 2 == 0) {
                listSize = inputFiles.size();
                listSizeEven = true;
            } else {
                listSize = inputFiles.size() - 1;
                listSizeEven = false;
            }

            for (int i = 0; i < listSize; i = i + 2) {
                try {
                    FileReader fileReader1 = new FileReader(inputFiles.get(i));
                    FileReader fileReader2 = new FileReader(inputFiles.get(i + 1));
                    BufferedReader reader1 = new BufferedReader(fileReader1);
                    BufferedReader reader2 = new BufferedReader(fileReader2);
                    File outputTmpFile = new File(pathNameMain + "tmpFile" + i);
                    FileWriter writer = new FileWriter(outputTmpFile);

                    String line1 = reader1.readLine();
                    String line2 = reader2.readLine();
                    String previousLine1 = null;
                    String previousLine2 = null;

                    while (line1 != null || line2 != null) {

                        if (line2 == null) {
                            previousLine1 = line1;
                            writer.write(line1 + "\r\n");
                            line1 = SortUtil.readLineWithVerifications(reader1, inputFiles.get(i));
                        } else if (line1 == null) {
                            previousLine2 = line2;
                            writer.write(line2 + "\r\n");
                            line2 = SortUtil.readLineWithVerifications(reader2, inputFiles.get(i + 1));
                        }

                        else if (SortUtil.compare(dataType, line1, line2) <= 0) {
                            previousLine1 = line1;
                            writer.write(line1 + "\r\n");
                            line1 = SortUtil.readLineWithVerifications(reader1, inputFiles.get(i));
                        } else if (SortUtil.compare(dataType, line1, line2) > 0) {
                            previousLine2 = line2;
                            writer.write(line2 + "\r\n");
                            line2 = SortUtil.readLineWithVerifications(reader2, inputFiles.get(i + 1));
                        }

                        if (SortUtil.compare(dataType, previousLine1, line1) > 0
                                && (previousLine1 != null) && (line1 != null)) {
                            System.out.printf("The file %s was not read after '%s' line " +
                                            "because the sorting order was violated in the source file.\n",
                                    inputFiles.get(i).getName(),
                                    previousLine1);
                            line1 = null;
                        }

                        if (SortUtil.compare(dataType, previousLine2, line2) > 0
                                && (previousLine2 != null) && (line2 != null)) {
                            System.out.printf("The file %s was not read after '%s' line " +
                                            "because the sorting order was violated in the source file.\n",
                                    inputFiles.get(i + 1).getName(),
                                    previousLine2);
                            line2 = null;
                        }
                    }

                    reader1.close();
                    reader2.close();
                    writer.close();

                    Path source = Paths.get(pathNameMain + "tmpFile" + i);
                    Path target = Paths.get(pathNameMain + "res" + i);

                    Files.deleteIfExists(target);

                    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                    outputTmpFile = new File(target.toUri());

                    updatedInputFiles.add(outputTmpFile);

                } catch (IOException ex) {
                    System.out.println("File error!");
                }
            }

            if (!listSizeEven) {
                updatedInputFiles.add(inputFiles.get(inputFiles.size() - 1));
            }

            mergeSort(updatedInputFiles, resultFile, dataType, sortType);
        }
    }
}
