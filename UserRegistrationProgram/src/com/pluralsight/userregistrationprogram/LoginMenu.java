package com.pluralsight.userregistrationprogram;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Class use to login in.
 */
public class LoginMenu {

    public LoginMenu() { }

    public static void loginMenu(Connection conn) throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, SQLException {

        SecretKey key = CryptoTools.generateKey();
        CryptoTools.decryptFile();

        Scanner scanner = new Scanner(System.in);
        String checkUsername = loginCheckUser();
        String checkEmail = String.valueOf(loginCheckEmail());
        String checkPassword = loginCheckPassword();

        boolean loginChecker = loginChecker(checkEmail, checkUsername, checkPassword);

        if (!loginChecker) {
            System.out.println("No match found for this combination of credentials.");
            System.out.println("Please try again.");
            loginMenu(conn);
        }
        else {
            System.out.println("Congratulations PinkSkin");
            System.out.println("Successfully logged in.");
            System.out.print("What would you like to do? ");
            String nothing = scanner.nextLine();
            System.out.println(nothing + "HA HA HA was a joke :D ");
            CryptoTools.encryptFile(key);
            scanner.close();
            //Main.deleteFile();
            Main.mainMenu(conn);
        }
    }

    /**
     * Function use to check in the file for user login parameters.
     */
    private static boolean loginChecker(String value, String value1, String value2) throws IOException {

        File file = new File("C:\\Users\\valde\\IdeaProjects" +
                                        "\\UserRegistrationProgram\\decrypted_file_accounts.txt");
        String[] words;
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String string;
        String input = "Email:" + value + "User:" + value1 + "Password:" + value2;

        while ((string = bufferedReader.readLine()) != null) {
            words = string.split(" ");
            for (String word : words) {
                if (!word.equals(input)) {
                    return true;
                }
            }
        }
        bufferedReader.close();
        fileReader.close();
        return false;
    }

    /**
     * Function use to check in the file for username credentials looking for the parameter introduced exist.
     */
    private static String loginCheckUser() throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your Username:");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        if (username.contains(" ")) {
            System.out.println("Username can not contain spaces.");
            return loginCheckUser();
        }
        else {
            boolean hasDuplicate = DataClass.checkForDuplicates("User:", username);

            if (!hasDuplicate) {
                loginCheckUser();
            }
            else {
                scanner.close();
                return username;
            }
        }
        return null;
    }

    /**
     * Function use to check in the file for user email looking for the parameter introduced exist.
     */
    private static String loginCheckEmail() throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your email:");
        System.out.println("Email: ");
        String email = scanner.nextLine();

        if (!DataClass.isValid(email)) {
            System.out.println("Invalid Email.");
            return loginCheckEmail();
        }
        else {
            boolean hasDuplicate = DataClass.checkForDuplicates("Email:", email);
            if (!hasDuplicate) {
                loginCheckEmail();
            }
            else {
                scanner.close();
                return email;
            }
        }
        return null;
    }

    /**
     * Function use to check in the file for user password looking for the parameter introduced exist.
     */
    private static String loginCheckPassword() throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please introduced your password:");
        System.out.println("Password: ");
        String pass = scanner.nextLine();

        if (pass.contains(" ")) {
            System.out.println("Password can not contain spaces.");
            return loginCheckPassword();
        }
        else {
            boolean hasDuplicate = DataClass.checkForDuplicates("Password:", pass);
            if (!hasDuplicate) {
                loginCheckPassword();
            }
            else {
                scanner.close();
                return pass;
            }
        }
        return null;
    }
}
