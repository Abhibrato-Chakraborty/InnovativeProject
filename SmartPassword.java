import java.security.SecureRandom;
import java.util.Scanner;

// Class to store user details
class UserDetails {

    private String name;
    private String pan;
    private String dob;

    // Constructor
    public UserDetails(String name, String pan, String dob) {

        this.name = name;
        this.pan = pan;
        this.dob = dob;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getPan() {
        return pan;
    }

    public String getDob() {
        return dob;
    }
}

// Class for generating password
class PasswordGenerator {

    // Symbols used in password
    private static final String SYMBOLS
            = "@#$%&*!";

    // Secure random object
    private static final SecureRandom random
            = new SecureRandom();

    // Password generation method
    public static String generatePassword(UserDetails user) {

        // First 2 letters from name
        String namePart
                = user.getName().substring(0, 2);

        // Last 2 characters from PAN
        String panPart
                = user.getPan().substring(
                        user.getPan().length() - 2);

        // Remove '/' from DOB
        String dobPart
                = user.getDob().replace("/", "");

        // Random special symbol
        char symbol
                = SYMBOLS.charAt(
                        random.nextInt(SYMBOLS.length()));

        // Random 2-digit number
        int randomNumber
                = random.nextInt(90) + 10;

        // Final password
        return namePart
                + symbol
                + dobPart
                + panPart
                + randomNumber;
    }
}

// Class for checking password strength
class PasswordStrengthChecker {

    public static String classifyPassword(String password) {

        int score = 0;

        // Length check
        if (password.length() >= 8) {
            score++;
        }

        // Lowercase letter check
        if (password.matches(".*[a-z].*")) {
            score++;
        }

        // Uppercase letter check
        if (password.matches(".*[A-Z].*")) {
            score++;
        }

        // Digit check
        if (password.matches(".*\\d.*")) {
            score++;
        }

        // Special character check
        if (password.matches(".*[^a-zA-Z0-9].*")) {
            score++;
        }

        // Strength classification
        if (score <= 2) {
            return "Weak"; 
        }else if (score <= 4) {
            return "Medium"; 
        }else {
            return "Strong";
        }
    }
}

// Main class
public class SmartPassword {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String choice;

        do {

            // Name input
            System.out.println("Enter Name:");
            String name = sc.nextLine();

            // PAN validation
            String pan;

            while (true) {

                System.out.println("Enter PAN Number (Example: ABCDE1234F):");
                pan = sc.nextLine().toUpperCase();

                // PAN regex validation
                if (pan.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
                    break;
                } else {
                    System.out.println(
                            "Invalid PAN Format!");
                    System.out.println(
                            "Valid Format Example: ABCDE1234F\n");
                }
            }

            // DOB validation
            String dob;

            while (true) {

                System.out.println(
                        "Enter DOB (DD/MM/YYYY):");

                dob = sc.nextLine();

                // DOB regex validation
                if (dob.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    break;
                } else {
                    System.out.println(
                            "Invalid DOB Format!");
                    System.out.println(
                            "Valid Format Example: 15/08/2005\n");
                }
            }

            // Create user object
            UserDetails user
                    = new UserDetails(name, pan, dob);

            // Generate password
            String password
                    = PasswordGenerator.generatePassword(user);

            // Check password strength
            String strength
                    = PasswordStrengthChecker
                            .classifyPassword(password);

            // Display output
            System.out.println(
                    "\nGenerated Password: "
                    + password);

            System.out.println(
                    "Password Strength: "
                    + strength);

            // Continue option
            System.out.println(
                    "\nDo you want another user? (Y/N)");

            choice = sc.nextLine();

        } while (choice.equalsIgnoreCase("Y"));

        sc.close();

        System.out.println("\nProgram Ended.");
    }
}