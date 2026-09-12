import java.util.Arrays;
import java.util.LinkedList;

public class Log {

    public static void debugInput(String input) {
        debugInput(input, null);
    }

    public static void debugInput(String input, String msg) {
        if (msg != null) {
            System.err.println(msg);
        } else {
            System.err.println();
        }
        System.err.println(input);
    }


    public static void debugInput(StringBuilder input) {
        debugInput(input, null);
    }

    public static void debugInput(StringBuilder input, String msg) {
        debugInput(input.toString(), msg);
    }

    public static void debugInput(int[] input) {
        debugInput(input, null);
    }

    public static void debugInput(int[] input, String msg) {
        debugInput(Arrays.toString(input), msg);
    }


    public static void debugInput(LinkedList<String> input) {
        debugInput(input, null);
    }

    public static void debugInput(LinkedList<String> input, String msg) {
        debugInput(input.toString(), msg);
    }


}
