package utility;

public class FontColor {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static String renderBlue(String text){
        return ANSI_BLUE + text + ANSI_RESET;
    }
    public static String renderRed(String text){
        return ANSI_RED + text + ANSI_RESET;
    }

    public static String renderYellow(String text){
        return ANSI_YELLOW + text + ANSI_RESET;
    }
}
