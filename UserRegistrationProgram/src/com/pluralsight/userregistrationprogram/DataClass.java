package com.pluralsight.userregistrationprogram;

import java.io.*;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Class use to manage all data related.
 */
public class DataClass {

    public DataClass() { }

    /**
     * Function to write new user data to the file and terminate the program.
     */
    public static void saveData(String name, String surname, String email, String username, String pass) {

        try {
            FileWriter myWriter = new FileWriter("decrypted_file_accounts.txt", true);


            BufferedWriter bw = new BufferedWriter(myWriter);
            bw.write(String.format("Name:%s Surname:%s Email:%s User:%s Password:%s",
                    name, surname, email, username, pass));
            bw.append("\n");
            bw.close();
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            System.out.println("Please try again PickSkin");
            e.printStackTrace();
        }
    }

    /**
     * Function to check for duplicates of the "Username and Email" column in the file-database.
     */
    public static boolean checkForDuplicates(String column, String value) throws IOException {

        File file = new File("C:\\Users\\valde\\IdeaProjects" +
                                        "\\UserRegistrationProgram\\decrypted_file_accounts.txt");
        String[] words;
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String input = column + value;

        while ((line = bufferedReader.readLine()) != null) {
            words = line.split(" ");
            for (String word : words) {
                if (word.equals(input)) {
                    fileReader.close();
                    bufferedReader.close();
                    return true;
                }
            }
        }
        fileReader.close();
        bufferedReader.close();
        return false;
    }

    /**
     * Function use to delete the unencrypted file after is encrypted.
     */
    public static void deleteFile() throws IOException {

        File file = new File("decrypted_file_accounts.txt");
        while(file.exists()) {
            try {
                Files.delete(Path.of("C:\\Users\\valde\\IdeaProjects" +
                                            "\\UserRegistrationProgram\\decrypted_file_accounts.txt"));
            } catch (FileSystemException var2) {
                System.out.println(); // TODO: print exception, or log it
            }
        }
    }

    /**
     * Function to check email syntax to avoid non valid emails using Regex.
     */
    public static boolean isValid(String email) {

        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    /**
     * Function created to text the function isValid "check syntax of email to get only valid ones".
     */
    public static void emailAddressesArray() {

        String[] testEmailAddresses = {"email@example.com", "firstname.lastname@example.com",
                "email@subdomain.example.com", "firstname+lastname@example.com", "email@123.123.123.123",
                "email@[123.123.123.123]", "email@example.com", "1234567890@example.com", "email@example-one.com",
                "_______@example.com", "email@example.name", "email@example.museum", "email@example.co.jp",
                "firstname-lastname@example.com", "#@%^%#$@#$@#.com", "@example.com", "Joe Smith <email@example.com>",
                "email.example.com", "email@example@example.com", ".email@example.com", "email.@example.com",
                "email..email@example.com", "あいうえお@example.com", "email@example.com (Joe Smith)",
                "email@example,email@-example.com", "email@example.web", "email@111.222.333.44444",
                "email@example..com", "Abc..123@example.com", "just”not”right@example.com"};

        for (int i = 0; i <= testEmailAddresses.length - 1; i++) {
            boolean send = isValid(testEmailAddresses[i]);
            System.out.println(send+ " " + testEmailAddresses[i] );
        }
    }
}
