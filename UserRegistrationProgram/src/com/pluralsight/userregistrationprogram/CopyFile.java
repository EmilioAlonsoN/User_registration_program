package com.pluralsight.userregistrationprogram;

import java.io.*;

/**
 * Non use class make copy of a file
 */
public class CopyFile {

    public static void CopyFile(File input, File output) throws FileNotFoundException {

        try {
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter(output, true);
            String s;

            while ((s = bufferedReader.readLine()) != null) {
                fileWriter.append("\n");
                fileWriter.write(s);
                fileWriter.flush();
            }
            bufferedReader.close();
            fileWriter.close();
            System.out.println("file copied");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
