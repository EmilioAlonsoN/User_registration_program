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
 * Class use to autogenerate users and add it to the database.
 */
public class UserGenerator {

    private static String Name;
    private static String Surname;
    /**
     * Function use to choose the number of new user to be created, will calculate the time consume on the process
     * express on seconds.
     * Password will be created with the autoPasswordGenerator function form NewUser class.
     */

    public static void generateUsers(Connection conn) throws SQLException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, InvalidKeySpecException,
            InvalidKeyException {

        SecretKey key = CryptoTools.generateKey();
        CryptoTools.decryptFile();

        System.out.println("How many new users do you want to create? ");
        Scanner scanner = new Scanner(System.in);
        int amount = scanner.nextInt();
        double startTime = System.nanoTime();

        for (int i = 0; i < amount; i++) {
            String name = getName();
            String surname = getSurname();
            String email = getEmail(conn, name, surname);
            String username = getUsername(conn, surname);
            String pass = NewUser.passwordGenerator((char) 20);
            DatabaseClass.saveUser(conn, name, surname, email, username, pass);
            DataClass.saveData(name, surname, email, username, pass);
        }
        double endTime = System.nanoTime();

        double duration = ((endTime - startTime) / 1000000) / 1000;
        System.out.println("Duration on seconds: " + duration);
        CryptoTools.encryptFile(key);
        Main.mainMenu(conn);
    }

    /**
     * Function use to random choose a name.
     */
    private static String getName() {
        String[] name = {"James", "Puta", "Mary", "Robert", "Patricia", "John", "Jennifer", "Michael", "Linda",
                "William", "Elizabeth", "David", "Barbara", "Stefani", "Richard", "Susan", "Joseph", "Jessica", "Thomas",
                "Sarah", "Charles", "Karen", "Christopher", "Nancy", "Daniel", "Lisa", "Matthew", "Betty", "Anthony",
                "Margaret", "Mark", "Sandra", "Donald", "Ashley", "Steven", "Kimberly", "Paul", "Emily", "Andrew", "Donna",
                "Joshua", "Michelle", "Kenneth", "Dorothy", "Kevin", "Carol", "Brian", "Amanda", "George", "Melissa",
                "Edward", "Deborah", "Ronald", "Stephanie", "Timothy", "Rebecca", "Jason", "Sharon", "Jeffrey", "Laura",
                "Ryan", "Cynthia", "Jacob", "Kathleen", "Gary", "Amy", "Nicholas", "Shirley", "Eric", "Angela", "Jonathan",
                "Helen", "Stephen", "Anna", "Larry", "Brenda", "Justin", "Pamela", "Scott", "Nicole", "Brandon", "Emma",
                "Benjamin", "Samantha", "Samuel", "Katherine", "Gregory", "Christine", "Frank", "Debra", "Alexander",
                "Rachel", "Raymond ", "Catherine", "Patrick", "Carolyn", "Jack", "Janet", "Dennis", "Ruth", "Jerry",
                "Maria", "Tyler", "Heather", "Aaron", "Diane", "Jose", "Virginia", "Adam", "Julie", "Henry", "Joyce",
                "Nathan", "Victoria", "Douglas", "Olivia", "Zachary", "Kelly", "Peter", "Christina", "Kyle", "Lauren",
                "Walter ", "Joan", "Ethan", "Evelyn", "Emilio", "Jeremy ", "Judith", "Harold ", "Megan", "Keith", "Cheryl",
                "Christian", "Andrea", "Roger", "Hannah", "Noah", "Martha", "Gerald", "Jacqueline", "Carl", "Frances",
                "Terry", "Gloria", "Sean", "Ann", "Austin", "Teresa", "Arthur", "Kathryn", "Lawrence", "Sara", "Jesse",
                "Janice", "Dylan", "Jean", "Bryan", "Alice", "Joe", "Madison", "Jordan", "Doris", "Billy", "Abigail",
                "Bruce", "Julia", "Albert", "Judy", "Willie", "Grace", "Gabriel", "Denise", "Logan", "Amber", "Alan ",
                "Marilyn", "Juan", "Beverly", "Wayne", "Danielle", "Roy", "Theresa", "Ralph", "Sophia", "Randy", "Marie",
                "Eugene", "Diana", "Vincent", "Brittany", "Russell", "Natalie", "Elijah", "Isabella", "Louis", "Charlotte",
                "Bobby", " Rose", "Philip", "Alexis", "Johnny", "Kayla"};
        Random random = new Random();
        int randomName = random.nextInt(name.length);

        return name[randomName];
    }

    public static void setName(String name) {
        UserGenerator.Name = name;
    }

    /**
     * Function use to random choose a surname.
     */
    private static String getSurname() {
        String[] surname = {"Hoxha", "Prifti", "Koetsier", "Shehu", "Dervishi", "Bektashi", "Begu", "Gjoni or Gjonaj",
                "Leka", "Lekaj", "Alex", "Gjoni", "John", "Murati", "Murad", "Mehmeti", "Mehmed", "Hysi", "Gjika",
                "Marku", "Mark", "Kola", "Nicholas", "Hasani", "Hassan", "Kristi", "Kristo", "Luka", "Lucas", "Brahimi",
                "Abraham", "Sinani", "Thanasi", "Athanasius", "Halili", "Halil", "Abazi", "Abbas", "Dibra", "Laci",
                "Shkodra", "Prishtina", "Delvina", "Koroveshi", "Permeti", "Frasheri", "Gega", "Tosku", "Chami",
                "Kelmendi", "Shkreli", "Berisha", "Krasniqi", "Gashi", "Kuqi", "Bardhi", "Gruber", "Huber", "Bauer",
                "Wagner", "Müller", "Pichler", "Steiner", "Moser", "Mayer", "Hofer", "Leitner", "Berger", "Fuchs",
                "Eder", "Fischer", "Schmid", "Winkler", "Weber", "Schwarz", "Maier", "Schneider", "Reiter", "Mayr",
                "Schmidt", "Smith", "Wimmer", "Vintner", "Egger", "Ploughman", "Brunner", "Lang", "Baumgartner",
                "Forrester", "Auer", "Binder", "Cooper", "Lechner", "Wolf", "Wallner", "Aigner", "Ebner", "Koller",
                "Lehner", "Haas", "Schuster", "Heilig", "Hodžić", "Hadžić", "Čengić", "Delić", "Demirović", "Kovačević",
                "Tahirović", "Ferhatović", "Muratović", "Ibrahimović", "Hasanović", "Mehmedović", "Salihović", "Terzić",
                "Tailor", "Ademović", "Adilović", "Delemović", "Zukić", "Krličević", "Suljić", "Salomon", "Suliman",
                "Ahmetović", "Jensen", "Hansen", "Pedersen", "Andersen", "Christensen", "Larsen", "Sørensen",
                "Rasmussen", "Jørgensen", "Petersen", "Madsen", "Matthew-son", "Kristensen", "Olsen", "Olaf-son",
                "Thomsen", "Tom-son", "Christiansen", "Christian-son", "Poulsen", "Paul-son", "Johansen", "John-son",
                "Møller", "Miller", "Mortensen", "De Jong", "Jansen", "De Vries", "Van den Berg", "Van Dijk", "Van Dyk",
                "Bakker Baker", "Janssen", "Johnson", "Visser", "Fisher", "Smit", "Meijer", "Meyer", "De Boer", "Mulder",
                "De Groot", "Bos", "Vos", "Peters", "Hendriks", "Van Leeuwen", "Dekker", "Brouwer", "De Wit", "De La Fruta",
                "Dijkstra", "Smits", "De Graaf", "Van der Meer", "Alonso", "García", "Fernández", "González", "Rodríguez",
                "López", "Martínez", "Sánchez", "Pérez", "Martín", "Gómez", "Ruiz", "Hernández", "Jiménez", "Díaz",
                "Álvarez", "Moreno", "Muñoz", "Gutiérrez", "Romero", "Pilgrim", "Navarro", "Torres", "Towers Toponym",
                "Domínguez", "Gil", "Vázquez", "Serrano", "Ramos", "Blanco", "Sanz", "Castro", "Suárez", "Ortega",
                "Rubio", "Molina", "Delgado", "Skinny", "Ramírez", "Morales", "Ortiz", "Marín", "Iglesias"};
        Random random = new Random();
        int randomSurname = random.nextInt(surname.length);

        return surname[randomSurname];
    }

    public static void setSurname(String surname) {
        UserGenerator.Surname = surname;
    }

    /**
     * Function use to random generate a email base on the name and surname and adding some random year.
     */
    private static String getEmail(Connection conn, String name, String surname) throws SQLException {
        int min = 1910;
        int max = 2004;
        int year = randomNumberGenerator(min, max);

        String[] middleEmail = {"hotmail", "msn", "yahoo", "sbcglobal", "gmail", "aol", "outlook", "verizon", "att",
                "me", "icloud", "comcast", "optonline", "live", "mac", "postcode", "eucerin", "etos", "logitech",
                " asus", "aoc", "samsung", "sony", "google", "disney", "amazon", "nitro", "caca", "turd", "moron"};
        Random random = new Random();
        int randomMiddlePart = random.nextInt(middleEmail.length);

        String[] endEmail = {".com", ".es", ".nl", ".ca", ".net", ".fr", ".nz", ".it"};
        int randomEndPart = random.nextInt(endEmail.length);

        String emailGenerated = name.toLowerCase() + surname.toLowerCase() + year + "@" +
                                            middleEmail[randomMiddlePart] + endEmail[randomEndPart];
        String email = emailGenerated.replaceAll("\\s+", "");
        boolean hasDuplicates = DatabaseClass.checkForDuplicatesDBEmail(conn, email);
        if(hasDuplicates) {
            getEmail(conn, name, surname);
        }
        return email;
    }

    /**
     * Function use to random generate a year that will be added to the email.
     */
    private static int randomNumberGenerator(int min, int max) {
        double year = (Math.random()*((max-min)+1))+min;
        return (int) year;
    }

    /**
     * Function use to random generate a username base on the surname chosen and adding some randomness.
     */
    private static String getUsername(Connection conn, String surname) throws SQLException {

        String randomUsernamePart = NewUser.passwordGenerator((char) 10);
        String username = surname.toLowerCase() + randomUsernamePart;
        boolean hasDuplicates = DatabaseClass.checkForDuplicatesDBUser(conn, username);

        if(hasDuplicates) {
            getUsername(conn, surname);
        }
        return username;
    }

}
