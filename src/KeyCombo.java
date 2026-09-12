import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public record KeyCombo(int keyCode, List<Integer> modifierKeyCodes) {

    // Replays this exact keystroke using Robot
    public void replay(Robot robot) {
        for (int mod : modifierKeyCodes) {
            robot.keyPress(mod);
        }

        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);

        for (int i = modifierKeyCodes.size() - 1; i >= 0; i--) {
            robot.keyRelease(modifierKeyCodes.get(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int mod : modifierKeyCodes) {
            sb.append(KeyEvent.getKeyText(mod)).append("+");
        }
        sb.append(KeyEvent.getKeyText(keyCode));
        return sb.toString();
    }
}