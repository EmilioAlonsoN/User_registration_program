package com.pluralsight.userregistrationprogram;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;


/**
 * This is a Crypto class use for of all the task required to deal with encryption and decryption.
 */
public class CryptoTools {

    public CryptoTools() { }

    /**
     * Secret key generator.
     * Generate 256 bit key.
     */
    protected static SecretKey generateKey() throws NoSuchAlgorithmException {

        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);
        SecretKey secretKey = generator.generateKey();
        secretKey.getFormat();

        return secretKey;
    }

    /**
     * Encryption using Cipher method.
     */
    private static void encryptMode(SecretKey secretKey) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, IOException, NoSuchPaddingException, InvalidAlgorithmParameterException {

        FileInputStream nonEncryptedFile = new FileInputStream("C:\\Users\\valde\\IdeaProjects" +
                                                        "\\UserRegistrationProgram\\decrypted_file_accounts.txt");
        FileOutputStream encryptFile = new FileOutputStream("C:\\Users\\valde\\IdeaProjects" +
                                                        "\\UserRegistrationProgram\\encrypted_file_accounts.txt");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

        byte[] ivBytes = new byte[16];
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec, SecureRandom.getInstance("SHA1PRNG"));
        CipherInputStream cipherInputStream = new CipherInputStream(nonEncryptedFile, cipher);
        writeTheFileEncryption(cipherInputStream, encryptFile);

        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        FileWriter myWriter = new FileWriter("key.pub");
        BufferedWriter bufferedWriter = new BufferedWriter(myWriter);
        bufferedWriter.write(encodedKey);

        bufferedWriter.close();
        myWriter.close();
    }


    /**
     * Decryption using Cipher method.
     */
    private static void decryptMode() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
                                            IOException, NoSuchPaddingException, InvalidAlgorithmParameterException {

        FileInputStream encryptFile = new FileInputStream("C:\\Users\\valde\\IdeaProjects" +
                                                     "\\UserRegistrationProgram\\encrypted_file_accounts.txt");
        FileOutputStream nonEncryptedFile = new FileOutputStream("C:\\Users\\valde\\IdeaProjects" +
                                                     "\\UserRegistrationProgram\\decrypted_file_accounts.txt");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

        byte[] ivBytes = new byte[16];
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        FileReader keyFile = new FileReader("key.pub");
        BufferedReader bufferedReader = new BufferedReader(keyFile);
        byte[] decodedKey = Base64.getDecoder().decode(String.valueOf(bufferedReader.readLine()));
        SecretKey Key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        cipher.init(Cipher.DECRYPT_MODE, Key, ivParameterSpec, SecureRandom.getInstance("SHA1PRNG"));
        CipherOutputStream cipherOutputStream = new CipherOutputStream(nonEncryptedFile, cipher);
        writeTheFileEncryption(encryptFile, cipherOutputStream);
        keyFile.close();
        bufferedReader.close();
    }

    /**
     * Write the file with the chosen encryption algorithm.
     */
    private static void writeTheFileEncryption(InputStream input, OutputStream output) throws IOException {

        byte[] buffer = new byte[64];
        int numOfBytesRead;
        while ((numOfBytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, numOfBytesRead);
        }
        output.close();
        input.close();
    }

    /**
     * Function use to encrypt the file with the users data.
     */
    public static void encryptFile(SecretKey key) throws InvalidAlgorithmParameterException {

        try {
            encryptMode(key);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException |
                InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function use to decrypt the file with the users data.
     */
    public static void decryptFile() {

        try {
            decryptMode();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException |
                InvalidAlgorithmParameterException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}

