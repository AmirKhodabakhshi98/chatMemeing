import java.awt.*;
import java.util.LinkedList;

public class Controller {

    private final int linebreak = 84;
    LinkedList input;
    View view;
    Robot robot;

    public Controller() throws AWTException {
        robot = new Robot();
        input = new LinkedList();
        view = new View(this);
    }




    public void userSubmittedInput(LinkedList<String> input) throws AWTException {
        this.input = input;
        int[] arr = StringFormat.getInputToRobot(input);
        writeToKeyboard(arr);
    }



    private void writeToKeyboard(int[] input) throws AWTException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        for (int i : input) {
            robot.keyPress(i);
            robot.keyRelease(i);
        }
    }




    static void main() throws AWTException {
        Controller controller = new Controller();
    }


}
