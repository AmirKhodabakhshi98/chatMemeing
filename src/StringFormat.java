import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class StringFormat {


    private static final int lineBreak = 84;

    private static StringBuilder spaceFormatting(LinkedList<String> input) {
        StringBuilder sb = new StringBuilder();

        for (String s : input) {
            sb.append(s);
            int diff = (lineBreak - (s.length() % lineBreak)) % lineBreak;
            if (diff != 0) {
                sb.append(" ".repeat(diff));
            }
        }
        Log.debugInput(sb, "spaceFormatting");
        return sb;
    }
    private static int[] inputToRobot(StringBuilder spaceFormattedInput){
        int[] inputMappedToRobot = new int[spaceFormattedInput.length()];
        for (int i=0; i<spaceFormattedInput.length(); i++) {
            inputMappedToRobot[i] = KeyEvent.getExtendedKeyCodeForChar(spaceFormattedInput.charAt(i));
        }
        Log.debugInput(inputMappedToRobot, "inputToRobot");
        return inputMappedToRobot;
    }



    public static int[] getInputToRobot(LinkedList<String> input) throws AWTException {
        int[] inputMappedToRobot = inputToRobot(spaceFormatting(input));
        Log.debugInput(inputMappedToRobot, "getInputToRobot");
        return inputMappedToRobot;
    }
    

}
