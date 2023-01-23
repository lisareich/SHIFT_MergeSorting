package org.lisareich;

import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

// Util class for sorting method
public class SortUtil {

    public static int compare(String dataType, String line1, String line2) {

        if (line1 == null && line2 != null) {
            return -1;
        } else if (line2 == null && line1 != null) {
            return 1;
        } else if (line1 == null && line2 == null) {
            return 0;
        }

        if ("-i".equals(dataType)) {
            int num1 = Integer.parseInt(line1);
            int num2 = Integer.parseInt(line2);

            if (num1 - num2 > 0) {
                return 1;
            } else if (num1 - num2 < 0) {
                return -1;
            } else {
                return 0;
            }
        } else if ("-s".equals(dataType)) {
            return line1.compareTo(line2);
        } else {
            throw new RuntimeException("Wrong data type!");
        }
    }

    public static void createFinalSortedFile(File file, File resultFile, String sortType) {
        if ("-d".equals(sortType)) {
            try {
                ReversedLinesFileReader fileReader = new ReversedLinesFileReader(file, Charset.defaultCharset());
                FileWriter writer = new FileWriter(resultFile);

                String line = fileReader.readLine();

                while (line != null) {
                    writer.write(line + "\r\n");
                    line = fileReader.readLine();
                }

                fileReader.close();
                writer.close();

            } catch (IOException ex) {
                System.out.println("File error!");
            }
        } else if ("-a".equals(sortType)) {
            file.renameTo(resultFile);
        }
    }

    public static void deleteTmpFiles() {
        File dirFile = new File(System.getProperty("user.dir"));

        try {
            for (File f : dirFile.listFiles()) {
                if (f.getName().startsWith("res")) {
                    f.delete();
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("The file directory is empty!");
        }
    }

    public static String readLineWithVerifications(BufferedReader reader, File file) {

        try {

            String line = reader.readLine();

            if ("".equals(line)) {
                return readLineWithVerifications(reader, file);
            } else if (line != null && line.contains(" ")) {
                System.out.printf("In the file %s line '%s' was excluded from sorting " +
                                "because it contains spaces\n",
                        file.getName(), line);
                return readLineWithVerifications(reader, file);
            } else {
                return line;
            }

        } catch (IOException ex) {
            System.out.println("File error!");
        }

        throw new RuntimeException();
    }
}
