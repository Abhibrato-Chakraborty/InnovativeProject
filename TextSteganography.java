import java.io.*;
import java.util.*;

public class TextSteganography {

    static final char ZERO = '\u200B'; // Binary 0
    static final char ONE = '\u200C';  // Binary 1

    // Convert message to binary
    static String textToBinary(String text) {
        StringBuilder binary = new StringBuilder();

        for (char ch : text.toCharArray()) {
            String bin = String.format("%8s",
                    Integer.toBinaryString(ch))
                    .replace(' ', '0');

            binary.append(bin);
        }

        return binary.toString();
    }

    // Convert binary to text
    static String binaryToText(String binary) {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < binary.length(); i += 8) {
            String byteStr = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteStr, 2);
            text.append((char) charCode);
        }

        return text.toString();
    }

    // Encode message
    static void encode(String coverFile,
                       String outputFile,
                       String secretMessage) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(coverFile));
            StringBuilder coverText = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                coverText.append(line).append("\n");
            }
            br.close();

            String binary = textToBinary(secretMessage);
            StringBuilder hidden = new StringBuilder();

            for (char bit : binary.toCharArray()) {
                if (bit == '0')
                    hidden.append(ZERO);
                else
                    hidden.append(ONE);
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
            bw.write(coverText.toString() + hidden.toString());
            bw.close();

            System.out.println("Message Hidden Successfully!");

        } catch (Exception e) {
            System.out.println("Encoding Error!");
        }
    }

    // Decode message
    static void decode(String encodedFile, int length) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(encodedFile));

            StringBuilder text = new StringBuilder();
            int ch;

            while ((ch = br.read()) != -1) {
                if ((char) ch == ZERO)
                    text.append('0');

                else if ((char) ch == ONE)
                    text.append('1');
            }

            br.close();

            String binary = text.substring(0, length * 8);
            String secret = binaryToText(binary);

            System.out.println("Hidden Message: " + secret);

        } catch (Exception e) {
            System.out.println("Decoding Error!");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== Text Steganography System =====");

        System.out.print("Enter Cover File Name: ");
        String cover = sc.nextLine();

        System.out.print("Enter Output File Name: ");
        String output = sc.nextLine();

        System.out.print("Enter Secret Message: ");
        String secret = sc.nextLine();

        encode(cover, output, secret);

        System.out.println("\nNow Decoding Message...");

        decode(output, secret.length());

        sc.close();
    }
}
