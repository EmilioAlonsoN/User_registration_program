package com.pluralsight.userregistrationprogram;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;


/**
 * Class used to create a new user.
 */
public class NewUser {

    public static void newUser(Connection conn) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, SQLException {

        SecretKey key = CryptoTools.generateKey();
        CryptoTools.decryptFile();

        System.out.println("....................");
        System.out.println("Registration App");
        System.out.println("App test ");

        String name = nameRegs();
        String surname = surnameRegs();
        String email = String.valueOf(eMail());

        System.out.println("....................");
        System.out.println("NOTE: Your user name is unique so it cannot be changed");
        System.out.println("Choose your Username.");

        String username = userName();
        String pass = passwordOptions(conn);

        DataClass.saveData (name, surname, email, username, pass); //Save data into a file.
        DatabaseClass.saveUser(conn,name, surname, email, username,pass);

        System.out.println("....................");
        System.out.println("Your user name is:" + username);
        System.out.println("Your password is:" + pass);
        System.out.println("Successfully registered.");
        System.out.println("....................");

        CryptoTools.encryptFile(key);

        Main.mainMenu(conn);
    }

    /**
     * Function to input a name.
     */
    private static String nameRegs() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("....................");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        if (name.contains(" ") || name.isEmpty()) {
            System.out.println("Name can not contain spaces.");
            return nameRegs();
        }

        System.out.println("Is this you name? " + "\"" + name + "\"" + " " + "Press \" y \" to confirm else try again.");
        String confirmation = scanner.nextLine();

        if (!confirmation.equals("y")) {
            return nameRegs();
        }
        scanner.close();
        return name;
    }

    /**
     * Function to input surname.
     */
    private static String surnameRegs() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("....................");
        System.out.print("Surname: ");
        String surname = scanner.nextLine();
        if (surname.contains(" ") || surname.isEmpty()) {
            return surnameRegs();
        }

        System.out.println("Is this you name? " + "\"" + surname + "\"" + " " + "Press \" y \" to confirm else try again.");
        String confirmation = scanner.nextLine();

        if (!confirmation.equals("y")) {
            return surnameRegs();
        }
        scanner.close();
        return surname;
    }

    /**
     * Function for manual email input. Will check that it is a valid email, and compare it to the file and the data base
     * to avoid duplicate emails.
     */
    private static String eMail() throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("....................");
        System.out.print("Please enter your email:");
        String email = scanner.nextLine();

        if (!DataClass.isValid(email)) {
            System.out.println("Invalid Email.");
            eMail();
        }
        else {
            boolean hasDuplicate = DataClass.checkForDuplicates("Email:", email);
            if (hasDuplicate) {
                System.out.println("Email already exists.");
                eMail();
            }
            else
                scanner.close();
                return email;
        }
        scanner.close();
        return email;
    }

    /**
     * Function to input a username. Will compare it to the file and the database to avoid duplicate usernames.
     */
    private static String userName() throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("....................");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        if (username.contains(" ") || username.isEmpty()) {
            System.out.println("Username can not contain spaces.");
            return userName();
        }
        else if (!username.contains(" ")) {
            boolean hasDuplicate = DataClass.checkForDuplicates("User:", username);
            if (hasDuplicate) {
                System.out.println("....................");
                System.out.println("Already exists.");
                System.out.println("Please choose another one.");
                return userName();
            }
            else {
                System.out.println("Is this your Username?" + " " + "\"" + username + "\"" +
                        " " + "Press \" y \" to confirm else try again.");
                String confirmation = scanner.nextLine();
                if (!confirmation.equals("y")) {
                    scanner.close();
                    return userName();
                }
            }
        }
        scanner.close();
        return username;
    }

    /**
     * Function to choose a method to create a password "self chosen or autogenerated".
     */
    private static String passwordOptions(Connection conn) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("....................");
        System.out.print("Password Option:\n1 Autogenerate password \n2 Choose yourself \n3 Main menu \n ");
        System.out.println("....................");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> {
                return passwordGenerator((char) 20);
            }
            case "2" -> {
                return selfChoosePassword();
            }
            case "3" ->
                    Main.mainMenu(conn);
            default -> {
                return passwordOptions(conn);
            }
        }
        scanner.close();
        return null;
    }

    /**
     * Function used to obtain a self chosen password from the user.
     */
    private static String selfChoosePassword() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("....................");
        System.out.println("Please enter a password with at least 8 characters");

        System.out.println("Password: ");
        String password = scanner.nextLine();
        System.out.println(password);

        if (password.contains(" ") || password.isEmpty()) {
            System.out.println("Password can not contain spaces or be empty.");
            return selfChoosePassword();
        }
        else if (password.length() < 8) {
            System.out.println("Password is too short, please enter a password with at least 8 characters");
            System.out.println("Try again PinkSkin");
            return selfChoosePassword();
        }
        else {
            System.out.print("Confirm Password: ");
            String confirmationPassword = scanner.nextLine();
            boolean checkPassword = password.equals(confirmationPassword);

            if (!checkPassword) {
                System.out.println("....................");
                System.out.println("Your password do not match.");
                System.out.println("Try again PinkSkin");
                return selfChoosePassword();
            }
        }
        scanner.close();
        return password;
    }

    /**
     * Function to autogenerated a new password.
     */
     public static String passwordGenerator(char length) {

        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;

        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return String.valueOf(password);
    }
}
