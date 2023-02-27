package utility;

import java.util.Scanner;
import java.util.regex.Pattern;

import static utility.FontColor.renderRed;

public class Input implements AutoCloseable {

    private final Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    public String getString(String... prompt) {
        if( prompt.length > 0)
            System.out.format(prompt[0]);
        return scanner.nextLine();
    }

    public boolean yesNo(String prompt) {
        System.out.format(prompt);
        String response = getString();
        return "y".toLowerCase().equals(response) ||
                "yes".toLowerCase().equals(response) ||
                "sure".toLowerCase().equals(response) ||
                "ok".toLowerCase().equals(response) ||
                "yeah".toLowerCase().equals(response) ||
                "affirmative".toLowerCase().equals(response);
    }

    public int getInteger(int min, int max, String prompt) {
        System.out.format(prompt);
        String nextLine = scanner.nextLine();
        nextLine = nextLine.isEmpty() ? String.valueOf(min-1): nextLine;
        boolean nextLineHasInt = nextLineHasInt(nextLine);
        int userInput = !nextLineHasInt ? (min-1) : Integer.parseInt(nextLine);
        if (userInput < min || userInput > max) {
            System.out.format(renderRed("%n⚠ Invalid input. Please enter a number between %d and %d.%n%n"), min, max);
            return getInteger(min, max);
        }
        return userInput;
    }

    private boolean nextLineHasInt(String nextLine) {
        return Pattern.matches("\\d+", nextLine);
    }

    public int getInteger(int min, int max) {
        return getInteger(min, max, String.format("Enter a number between %d and %d: ", min, max));
    }

    @Override
    public void close() {
        scanner.close();
    }
}
