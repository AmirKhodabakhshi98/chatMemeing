import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Controller {

    private final int linebreak = 84;
    LinkedList<KeyCombo> input;
    View view;
    Robot robot;

    public Controller() throws AWTException {
        robot = new Robot();
        //robot.setAutoDelay(1000);       // ms between each keystrokee- change if too fast
        robot.setAutoWaitForIdle(true);
        input = new LinkedList<>();
        view = new View(this);
    }

    static void main(String[] args) throws AWTException {
        try {
            new Controller();
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "An error occurred while starting the application.\n"
                            + "Please restart the application and try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            System.exit(0);
        }
    }

    public void userSubmittedInput(LinkedList<String> userInput, LinkedList<KeyCombo> keyCombos, int delaySeconds) throws AWTException {
        LinkedList<KeyCombo> padded = StringFormat.padCombos(keyCombos);
        this.input = padded;
        writeToKeyboard(padded, delaySeconds);
    }

    private void writeToKeyboard(LinkedList<KeyCombo> combos, int delaySeconds) throws AWTException {
        try {
            Thread.sleep(delaySeconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        for (KeyCombo combo : combos) {
            combo.replay(robot);
        }
    }
}