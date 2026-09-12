import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.LinkedList;

public class StringFormat {

    private static final int lineBreak = 84;

    // Splits combos on Enter keystrokes, pads each line individualyto a multiple of lineBreak.
    public static LinkedList<KeyCombo> padCombos(LinkedList<KeyCombo> combos) {
        LinkedList<KeyCombo> result = new LinkedList<>();
        LinkedList<KeyCombo> currentLine = new LinkedList<>();

        for (KeyCombo combo : combos) {
            if (combo.keyCode() == KeyEvent.VK_ENTER) {
                result.addAll(padLine(currentLine));
                currentLine = new LinkedList<>();
            } else {
                currentLine.add(combo);
            }
        }


        result.addAll(padLine(currentLine));

        //Log.debugInput(result, "padCombos");
        return result;
    }

    private static LinkedList<KeyCombo> padLine(LinkedList<KeyCombo> line) {
        LinkedList<KeyCombo> padded = new LinkedList<>(line);

        int diff = (lineBreak - (padded.size() % lineBreak)) % lineBreak;
        for (int i = 0; i < diff; i++) {
            padded.add(new KeyCombo(KeyEvent.VK_SPACE, Collections.emptyList()));
        }

        return padded;
    }
}