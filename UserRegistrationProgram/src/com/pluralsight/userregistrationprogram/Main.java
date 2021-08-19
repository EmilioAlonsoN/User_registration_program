package com.pluralsight.userregistrationprogram;


import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, SQLException {
        Connection conn = DatabaseClass.getConnection();
        mainMenu(conn);
    }
    public static void mainMenu(Connection conn) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, SQLException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("....................");
        System.out.println("Welcome to my App.");
        System.out.println("....................");
        System.out.println("From now on you will be part of the PinkSkin community.");
        System.out.println("What would you like to do?");
        System.out.println("....................");
        System.out.println("1 Make new Account\n2 Login\n3 User AutoGenerator\n4 Exit");
        System.out.println("....................");
        String option = scanner.next();
        while ((option != null || option.isEmpty())) {
            switch (option) {
                case "1" -> NewUser.newUser(conn);
                case "2" -> LoginMenu.loginMenu(conn);
                case "3" -> UserGenerator.generateUsers(conn);
                case "4" -> {
                    DataClass.deleteFile();
                    System.out.println("See you again PigSkin...............");
                    System.exit(0);
                }
            }
        }
        scanner.close();
    } // add loop to remove default call main
}